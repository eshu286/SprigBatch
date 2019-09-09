package com.poc.spring.batch.SpringBatch.listener;

import com.poc.spring.batch.SpringBatch.model.Employee;
import com.poc.spring.batch.SpringBatch.utils.BatchUtils;
import com.poc.spring.batch.SpringBatch.utils.ErrorFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StepItemWriteListner implements ItemWriteListener<Employee> {

    private static final Logger log = LoggerFactory.getLogger(StepItemWriteListner.class);

    @Autowired
    private ErrorFlag errorFlag;

    @Autowired
    private BatchUtils batchUtils;

    @Override
    public void beforeWrite(List list) {
        log.info("****************************Event before Writing**************************");
    }

    @Override
    public void afterWrite(List list) {
        log.info("********************************Event after Writing******************************");
    }

    @Override
    public void onWriteError(Exception e, List list) {
       // list.forEach(employee -> log.info("Employee: {}",list.toString()));
        e.printStackTrace();
        log.info("Failed rRecord : "+ list.get(0).toString());
    }
}
