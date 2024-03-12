package com.art.api.scheduler.application.jobconfig;

import com.art.api.scheduler.application.jobconfig.list.ArtListItemProcessor;
import com.art.api.scheduler.application.jobconfig.list.ArtListItemReader;
import com.art.api.scheduler.application.jobconfig.list.ArtListItemWriter;
import com.art.api.scheduler.application.apiResponse.KopisArtListResponse;
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
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Job listReaderJob(JobRepository jobRepository, Step listStep){

        return new JobBuilder("listReaderJob", jobRepository)
                .start(listStep)
                .incrementer(new RunIdIncrementer())
                .build();
    }

    // Step 1 (list)
    @Bean
    @JobScope
    public Step listStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("listStep", jobRepository)
                .<KopisArtListResponse, List<KopisArtList>>chunk(100, transactionManager)
                .reader(listItemReader())
                .processor(listItemProcessor())
                .writer(listItemWriter())
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    @StepScope
    public ArtListItemReader listItemReader() {
        return new ArtListItemReader();
    }

    @Bean
    @StepScope
    public ItemProcessor<KopisArtListResponse, List<KopisArtList>> listItemProcessor() {
        return new ArtListItemProcessor();
    }

    @Bean
    @StepScope
    public ArtListItemWriter<KopisArtList> listItemWriter() {
        JpaItemWriter<KopisArtList> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManager);
        return new ArtListItemWriter<>(writer);
    }

}
