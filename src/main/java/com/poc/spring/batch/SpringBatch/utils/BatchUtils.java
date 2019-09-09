package com.poc.spring.batch.SpringBatch.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

@Component
public class BatchUtils {

    private static final Logger log = LoggerFactory.getLogger(BatchUtils.class);

    public boolean isExpectedException(Throwable throwableException) {

        if (throwableException instanceof DuplicateKeyException ) {
            return true;
        } else  if (throwableException instanceof  NumberFormatException){
            return true;
        } else {
            return false;
        }

    }
}


