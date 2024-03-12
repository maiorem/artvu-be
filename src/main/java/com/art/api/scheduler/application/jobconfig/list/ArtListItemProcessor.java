package com.art.api.scheduler.application.jobconfig.list;

import com.art.api.scheduler.application.apiResponse.KopisArtListResponse;
import com.art.api.scheduler.domain.entity.KopisArtList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
public class ArtListItemProcessor implements ItemProcessor<KopisArtListResponse, List<KopisArtList>> {

    @Override
    public List<KopisArtList> process(KopisArtListResponse item) {

        log.info("art LIST PROCESSOR ============================================== ");

        LocalDateTime regdt = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

        return item.getDbs().getDb().stream().map(data -> KopisArtList.builder()
                .artId(data.getMt20id())
                .artNm(data.getPrfnm())
                .artStrDt(data.getPrfpdfrom())
                .artEndDt(data.getPrfpdto())
                .artFacNm(data.getFcltynm())
                .posterImgUrl(data.getPoster())
                .artAreaNm(data.getArea())
                .genreNm(data.getGenrenm())
                .status(data.getPrfstate())
                .openrunYn(data.getOpenrun())
                .regDt(regdt)
                .build()
        ).toList();
    }
}
