package com.art.api.scheduler.application.openApiRecords;

import com.art.api.scheduler.domain.entity.KopisArtList;
import lombok.Data;

import java.util.List;


@Data
public class KopisArtListResponse{

    private Dbs db;

    @Data
    public static class Dbs {

        private List<Db> db;

        @Data
        public static class Db {
            private String mt20id; //공연ID
            private String prfnm;//공연명
            private String prfpdfrom;//공연시작시간
            private String prfpdto;//공연종료시간
            private String fcltynm;//공연시설명
            private String poster;//포스터
            private String area;//지역
            private String genrenm;//장르
            private String prfstate;//공연상태
            private String openrun; //오픈런여부
        }

    }

}
