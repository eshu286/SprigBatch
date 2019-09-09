package com.poc.spring.batch.SpringBatch.tasklet;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class EventTasklet implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution contribution,
                                ChunkContext chunkContext) throws Exception {

        if(contribution.getExitStatus() == ExitStatus.COMPLETED) {
            //jobLauncher.run(job, new JobParameters());
            return RepeatStatus.CONTINUABLE;
        }
        return RepeatStatus.FINISHED;
    }
}
