package com.poc.spring.batch.SpringBatch.utils;

import org.springframework.stereotype.Component;

@Component
public class ErrorFlag {

    public boolean isBatchSuccessfull() {
        return isBatchSuccessfull;
    }

    public void setBatchSuccessfull(boolean batchSuccessfull) {
        isBatchSuccessfull = batchSuccessfull;
    }

    private boolean isBatchSuccessfull;


}
