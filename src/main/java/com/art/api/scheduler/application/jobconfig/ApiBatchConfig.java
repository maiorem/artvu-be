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
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@Slf4j
@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class ApiBatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final EntityManagerFactory entityManager;

    @Bean
    public Job listReaderJob(){
        return new JobBuilder("listReaderJob", jobRepository)
                .start(listReaderStep())
                .build();
    }

    // Step 1 (list)
    @Bean
    public Step listReaderStep() {
        return new StepBuilder("listReaderStep", jobRepository)
                .<KopisArtListResponse, List<KopisArtList>>chunk(2, transactionManager)
                .reader(listReader())
                .processor(listItemProcessor())
                .writer(listArtListItemWriter())
                .build();
    }

    @Bean
    public ArtListItemReader listReader() {
        return new ArtListItemReader();
    }

    @Bean
    public ArtListItemProcessor listItemProcessor() {
        return new ArtListItemProcessor();
    }

    @Bean
    public ArtListItemWriter<KopisArtList> listArtListItemWriter() {
        JpaItemWriter<KopisArtList> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManager);
        return new ArtListItemWriter<>(writer);
    }


}
