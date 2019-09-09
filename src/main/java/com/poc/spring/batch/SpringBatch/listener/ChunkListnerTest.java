package com.poc.spring.batch.SpringBatch.listener;

import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.stereotype.Component;

@Component
public class ChunkListnerTest implements ChunkListener {


    @Override
    public void beforeChunk(ChunkContext context) {

    }

    @Override
    public void afterChunk(ChunkContext context) {
    }

    @Override
    public void afterChunkError(ChunkContext context) {
        context.getStepContext().getStepExecution().getExitStatus();
    }
}
