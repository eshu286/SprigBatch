package com.poc.spring.batch.SpringBatch.configuration;

import com.poc.spring.batch.SpringBatch.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class DBLogProcessor implements ItemProcessor<Employee, Employee> {

    private static final Logger log = LoggerFactory.getLogger(DBLogProcessor.class);

    public Employee process(Employee employee) throws Exception {
        System.out.println("Processing record with data : " + employee.toString());
        throw new NullPointerException();
        //return employee;
    }
}
