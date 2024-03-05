package com.art.api.scheduler.application.jobconfig;

import com.art.api.scheduler.application.jobconfig.list.ArtListItemProcessor;
import com.art.api.scheduler.application.jobconfig.list.ArtListItemReader;
import com.art.api.scheduler.application.jobconfig.list.ArtListItemWriter;
import com.art.api.scheduler.application.openApiRecords.KopisArtListResponse;
import com.art.api.scheduler.domain.entity.KopisArtList;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ApiBatchConfig {
    private final EntityManagerFactory entityManager;

    @Bean
    public Job listReaderJob(JobRepository jobRepository, Step listReaderStep){

        return new JobBuilder("listReaderJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(listReaderStep)
                .build();
    }

    // Step 1 (list)
    @Bean
    @JobScope
    public Step listReaderStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("listReaderStep", jobRepository)
                .<KopisArtListResponse, List<KopisArtList>>chunk(2, transactionManager)
                .reader(listReader())
                .processor(listItemProcessor())
                .writer(listArtListItemWriter())
                .build();
    }

    @Bean
    @StepScope
    public ArtListItemReader listReader() {
        return new ArtListItemReader();
    }

    @Bean
    @StepScope
    public ArtListItemProcessor listItemProcessor() {
        return new ArtListItemProcessor();
    }

    @Bean
    @StepScope
    public ArtListItemWriter<KopisArtList> listArtListItemWriter() {
        JpaItemWriter<KopisArtList> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManager);
        return new ArtListItemWriter<>(writer);
    }


}
