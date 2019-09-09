package com.poc.spring.batch.SpringBatch.listener;

import com.poc.spring.batch.SpringBatch.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.listener.ItemListenerSupport;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JobItemListener extends ItemListenerSupport<Employee, Employee> {

    private static final Logger log = LoggerFactory.getLogger(JobItemListener.class);

    @Override
    public void afterRead(Employee employee) {

        log.info("Event after Reading" + employee.toString());
    }

    @Override
    public void onReadError(Exception e) {
        log.info("Event OnReading data");
    }

    @Override
    public void afterWrite(List list) {
        log.info("********************************Event after Writing******************************");
    }

    @Override
    public void onWriteError(Exception e, List list) {
        log.info("********************************Exception while writing Data into DB***********************************");
        log.info("Failed rRecord : "+ list.get(0).toString());
    }

}
