package com.poc.spring.batch.SpringBatch.listener;

import com.poc.spring.batch.SpringBatch.utils.BatchUtils;
import com.poc.spring.batch.SpringBatch.utils.ErrorFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobResultListener implements JobExecutionListener {

    @Autowired
    private BatchUtils batchUtils;

    @Autowired
    private ErrorFlag errorFlag;

    private static final Logger log = LoggerFactory.getLogger(JobResultListener.class);

    public void beforeJob(JobExecution jobExecution) {
        System.out.println("Called beforeJob().");
        log.info("*************************************Before Job Execution***************************************");
    }

    public void afterJob(JobExecution jobExecution) {
        System.out.println("Called afterJob()");
        log.info("************************************After Job Execution******************************************" );

        if( jobExecution.getStatus() ==  BatchStatus.COMPLETED ){
            log.info("************************************Job Executed Successfully******************************************" );
            //batchUtils.setNextChunkFlag(jobExecution);
            errorFlag.setBatchSuccessfull(true);
        }
        else if(jobExecution.getStatus() == BatchStatus.FAILED){
            log.info("************************************Job Execution Failed******************************************" );
        }
    }

}
