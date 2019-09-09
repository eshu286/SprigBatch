package com.poc.spring.batch.SpringBatch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class SpringBatchApplication implements CommandLineRunner {

	@Autowired
	public JobLauncher jobLauncher;

	@Autowired
	public Job job;

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {

        while (true) {
            jobLauncher.run(job, new JobParametersBuilder()
                    .addString("KPIEVENTJOB" + UUID.randomUUID().toString(), String.valueOf(System.currentTimeMillis()))
                    .toJobParameters());
        }
    }
}
