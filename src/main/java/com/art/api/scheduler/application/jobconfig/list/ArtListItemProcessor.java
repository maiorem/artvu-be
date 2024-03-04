package com.art.api.scheduler.application.jobconfig.list;

import com.art.api.scheduler.application.openApiRecords.KopisArtListResponse;
import com.art.api.scheduler.domain.entity.KopisArtList;
import org.springframework.batch.item.ItemProcessor;

import java.util.ArrayList;
import java.util.List;

public class ArtListItemProcessor implements ItemProcessor<KopisArtListResponse, List<KopisArtList>> {

    @Override
    public List<KopisArtList> process(KopisArtListResponse item) {
        List<KopisArtListResponse.Dbs.Db> dataList = new ArrayList<>();

        return dataList.stream().map(data -> KopisArtList.builder()
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
                .build()
        ).toList();
    }
}
