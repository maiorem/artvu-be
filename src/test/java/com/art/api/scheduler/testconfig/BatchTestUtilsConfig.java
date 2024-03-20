package com.art.api.scheduler.testconfig;

import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BatchTestUtilsConfig {

    @Bean
    JobLauncherTestUtils jobLauncherTestUtils(JobRepository jobRepository, JobLauncher jobLauncher) {
        JobLauncherTestUtils jobLauncherTestUtils = new JobLauncherTestUtils();
        jobLauncherTestUtils.setJobRepository(jobRepository);
        jobLauncherTestUtils.setJobLauncher(jobLauncher);
        return jobLauncherTestUtils;
    }
}
