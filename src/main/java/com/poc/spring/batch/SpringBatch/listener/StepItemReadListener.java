package com.poc.spring.batch.SpringBatch.listener;

import com.poc.spring.batch.SpringBatch.model.Employee;
import com.poc.spring.batch.SpringBatch.utils.BatchUtils;
import com.poc.spring.batch.SpringBatch.utils.ErrorFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StepItemReadListener implements ItemReadListener<Employee> {

    private static final Logger log = LoggerFactory.getLogger(StepItemReadListener.class);

    @Autowired
    private ErrorFlag errorFlag;

    @Autowired
    private BatchUtils batchUtils;

    @Override
    public void beforeRead() {
        log.info("Event before Reading");
    }

    @Override
    public void afterRead(Employee employee) {
        log.info("Event after Reading" + employee.toString());
    }

    @Override
    public void onReadError(Exception e) {

        log.info("Event OnReading data" + e);
    }
}
