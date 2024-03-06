package com.art.api.scheduler.application.jobconfig.list;

import com.art.api.scheduler.domain.entity.KopisArtList;
import com.art.api.scheduler.infrastructure.repository.KopisArtListApiRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(properties = {"job.name=listReaderJob"})
class ArtListItemWriterTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private KopisArtListApiRepository listApiRepository;

    @Test
    public void processor에서_writer로_list를_넘긴다(@Autowired Job job) throws Exception {
        this.jobLauncherTestUtils.setJob(job);

        listApiRepository.save(
                KopisArtList.builder()
                        .artId("PF121682")
                        .artNm("옥탑방 고양이")
                        .artStrDt("2010.04.06")
                        .artEndDt("2024.03.31")
                        .artFacNm("틴틴홀")
                        .artAreaNm("서울특별시")
                        .genreNm("연극")
                        .status("공연중")
                        .posterImgUrl("http://www.kopis.or.kr/upload/pfmPoster/PF_PF121682_210322_143051.gif")
                        .openrunYn("Y")
                        .regDt(LocalDateTime.now())
                        .build()
        );
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        Assertions.assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);

    }





}