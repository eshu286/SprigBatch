package com.poc.spring.batch.SpringBatch.listener;

import com.poc.spring.batch.SpringBatch.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.SkipListener;
import org.springframework.stereotype.Component;

@Component
public class EmployeeSkipListener implements SkipListener<Employee, Employee> {

    private static final Logger log = LoggerFactory.getLogger(EmployeeSkipListener.class);

    @Override
    public void onSkipInRead(Throwable t) {
        log.info("Skip after Reading" + t.getStackTrace());
    }

    @Override
    public void onSkipInWrite(Employee item, Throwable t) {

        log.info("Skip Writing: {}, with Stacktrace: {}", item.toString(), t.getStackTrace());
    }

    @Override
    public void onSkipInProcess(Employee item, Throwable t) {

        log.info("Skip Process" + item.toString());
    }

}
