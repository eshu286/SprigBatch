package com.poc.spring.batch.SpringBatch.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

@Component
public class DBConnectivitySkipper implements SkipPolicy{

    private static final Logger log = LoggerFactory.getLogger(DBConnectivitySkipper.class);

    @Override
    public boolean shouldSkip(Throwable exception, int skipCount) throws SkipLimitExceededException {

        if (exception instanceof DuplicateKeyException ) {
            return true;
        } else  if (exception instanceof  NullPointerException ){
            return true;
        } else {
            return false;
        }
    }

}
