package com.art.api.scheduler.application.jobconfig;

import com.art.api.scheduler.domain.entity.KopisArtList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class ApiBatchConfig {

//    private final KopisArtListApiRepository listApiRepository;
//    private final KopisArtDetailApiRepository detailApiRepository;
//    private final KopisArtFacDetailApiRepository facDetailApiRepository;

    // Art List Job
//    @Bean
//    public Job listReaderJob(JobRepository jobRepository, Step listReaderStep){
//        return new JobBuilder("listReaderJob", jobRepository)
//                .start(listReaderStep)
//                .build();
//    }



//    // Step 1 (list)
//    @JobScope
//    @Bean
//    public Step listReaderStep(JobRepository jobRepository, ItemReader apiListReader, ItemProcessor apiListProcessor, ItemWriter apiListWriter) {
//        return new StepBuilder("listReaderStep", jobRepository)
//                .build();
//    }


}
