package com.art.api.scheduler.application.openApiRecords;

import com.art.api.scheduler.domain.entity.KopisArtList;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class KopisArtListResponse{

    private Dbs dbs;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Dbs {

        private List<Db> db;

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
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
