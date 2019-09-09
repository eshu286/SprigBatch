package com.poc.spring.batch.SpringBatch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class NoWorkFoundStepExecutionListener extends StepExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(NoWorkFoundStepExecutionListener.class);

    @Override
    public void beforeStep(StepExecution stepExecution) {
        ExecutionContext context = stepExecution.getExecutionContext();
        String batchStatus = null;
        if(!context.isEmpty()){
            batchStatus = context.getString("IS_BATCH_JOB_SUCCESS");
        }

        if(!StringUtils.isEmpty(batchStatus) && !Boolean.parseBoolean(batchStatus)){
            stepExecution.isTerminateOnly();
            stepExecution.setExitStatus(new ExitStatus("STOPPED", "Job should not be run right now."));
        }
    }

    public ExitStatus afterStep(StepExecution stepExecution) {
        ExecutionContext context = stepExecution.getExecutionContext();
        log.info("*#*#*#*#*#**#*#*#*#*#*#**#*#*#*# After Step Execution *#*#*#*#**#*#*#*#**#**#*#*#*#*#*#*#**#*#*#*#*");
        if (stepExecution.getReadCount() == stepExecution.getWriteCount() + stepExecution.getSkipCount()) {
            System.out.println("##################################################Completed##################################################");
            context.put("IS_BATCH_JOB_SUCCESS", "false");
            stepExecution.setExitStatus(ExitStatus.COMPLETED);
            return ExitStatus.COMPLETED;
        }
        System.out.println("################################################## Failed ##################################################");
        context.put("IS_BATCH_JOB_SUCCESS", "false");
        return ExitStatus.FAILED;
    }

}