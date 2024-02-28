package com.art.api.scheduler.application.openApiRecords;

import com.art.api.scheduler.domain.entity.KopisArtList;

public record KopisArtListResponse(
        String mt20id, //공연ID
        String prfnm,//공연명
        String prfpdfrom,//공연시작시간
        String prfpdto,//공연종료시간
        String fcltynm,//공연시설명
        String poster,//포스터
        String area,//지역
        String genrenm,//장르
        String prfstate,//공연상태
        String openrun //오픈런여부
) {

    public KopisArtList toEntity() {
        return KopisArtList.builder()
                .artId(mt20id)
                .artNm(prfnm)
                .artStrDt(prfpdfrom)
                .artEndDt(prfpdto)
                .artFacNm(fcltynm)
                .posterImgUrl(poster)
                .artAreaNm(area)
                .genreNm(genrenm)
                .status(prfstate)
                .openrunYn(openrun)
                .build();
    }


}
