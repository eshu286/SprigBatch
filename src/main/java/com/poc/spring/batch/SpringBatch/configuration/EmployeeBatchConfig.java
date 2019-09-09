package com.poc.spring.batch.SpringBatch.configuration;

import com.poc.spring.batch.SpringBatch.listener.ChunkListnerTest;
import com.poc.spring.batch.SpringBatch.listener.JobResultListener;
import com.poc.spring.batch.SpringBatch.listener.StepItemReadListener;
import com.poc.spring.batch.SpringBatch.listener.StepItemWriteListner;
import com.poc.spring.batch.SpringBatch.model.Employee;
import com.poc.spring.batch.SpringBatch.reader.EmployeeReader;
import com.poc.spring.batch.SpringBatch.utils.BatchUtils;
import com.poc.spring.batch.SpringBatch.utils.DBConnectivitySkipper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.net.ConnectException;

@Configuration
@EnableBatchProcessing
public class EmployeeBatchConfig implements BatchConfigurer
        {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private JobResultListener jobResultListener;

    @Autowired
    private BatchUtils batchUtils;

    @Autowired
    private DBConnectivitySkipper dbConnectivitySkipper;

    @Autowired
    private StepItemWriteListner stepItemWriteListner;

    @Autowired
    private ChunkListnerTest chunkListnerTest;

    @Value("classPath:/input/inputData.csv")
    private Resource inputResource;


    @Bean
    public Job readingListOfValuesJob() {
        return jobBuilderFactory
                .get("step1")
                .incrementer(new RunIdIncrementer())
                .start(step())
                .listener(jobResultListener)
                .build();
    }

    @Bean
    public Step step() {
        return stepBuilderFactory
                .get("step1")
                .<Employee, Employee>chunk(1)
                .reader(employeeItemReader())
                .listener(new StepItemReadListener())
                .processor(processor())
                .writer(writer())
                .faultTolerant()
                .skipPolicy(dbConnectivitySkipper)
                .retry(ConnectException.class)
                .listener(chunkListnerTest)
                .listener(stepItemWriteListner)
                .build();
    }

    @Bean
    public ItemReader<Employee> employeeItemReader() {
        return  new EmployeeReader();
    }

    @Bean
    public ItemProcessor<Employee, Employee> processor() {
        return new DBLogProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Employee> writer() {
        JdbcBatchItemWriter<Employee> itemWriter = new JdbcBatchItemWriter<Employee>();
        itemWriter.setDataSource(dataSource());
        itemWriter.setSql("INSERT INTO EMPLOYEE (ID, FIRSTNAME, LASTNAME) VALUES (:id, :firstName, :lastName)");
        itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Employee>());
        System.out.println("Inserting employee  into DB");
        return itemWriter;
    }

    @Bean
    public DataSource dataSource(){
        EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder();
        return embeddedDatabaseBuilder.addScript("classpath:org/springframework/batch/core/schema-drop-h2.sql")
                .addScript("classpath:org/springframework/batch/core/schema-h2.sql")
                .addScript("classpath:employee.sql")
                .setType(EmbeddedDatabaseType.H2)
                .build();
    }
  @Override
    public JobRepository getJobRepository() throws Exception {
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(dataSource());
        factory.setTransactionManager(getTransactionManager());
        factory.afterPropertiesSet();
        return  factory.getObject();
    }

    @Override
    public PlatformTransactionManager getTransactionManager() throws Exception {
        return new ResourcelessTransactionManager();
    }

    @Override
    public JobLauncher getJobLauncher() throws Exception {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(getJobRepository());
        jobLauncher.afterPropertiesSet();
        return jobLauncher;
    }

    @Override
    public JobExplorer getJobExplorer() throws Exception {
        return null;
    }

}
