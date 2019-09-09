package com.poc.spring.batch.SpringBatch.utils;

import org.springframework.retry.RetryContext;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
public class StepRetry extends SimpleRetryPolicy {

    private int maxAttempts = 3;

    /**
     * Create a EventRetryPolicy with the default number of retry
     * attempts, retrying all configured exceptions.
     */
    public StepRetry() {
        setMaxAttempts(maxAttempts);
    }

    /**
     * Provides information on Exceptions that are eligible for Retry,
     * returns false if that exception is over the retry limit.
     */
    public boolean canRetry(RetryContext context) {

        Throwable exception = context.getLastThrowable();

        if (exception instanceof NullPointerException
                && context.getRetryCount() <= super.getMaxAttempts()) {
            return true;
        } else if (exception instanceof SQLException
                && context.getRetryCount() <= super.getMaxAttempts()) {
            return true;
        } else {
            return false;
        }
    }
}
