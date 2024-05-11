package com.art.api.discover.domain.dto;


import com.art.api.common.domain.entity.GenreList;
import com.art.api.discover.domain.entity.ArtMovie;
import com.art.api.product.domain.entity.ArtList;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiscoveryDTO {

    private String artNm;

    private String artShowAge;

    private String posterImgUrl;

    private String clsCode;

    private String discoverTitl;

    private String discoverCont;

    private String mvUrl;

    private int totSize;

    private List<GenreList> genreList = new ArrayList<>();


    @QueryProjection
    public DiscoveryDTO(String artNm, String artShowAge, String discoverTitl, String discoverCont, String mvUrl) {
        this.artNm = artNm;
        this.artShowAge = artShowAge;
        this.discoverCont = discoverCont;
        this.discoverTitl = discoverTitl;
        this.mvUrl = mvUrl;
    }

    public static DiscoveryDTO convertEntityToDto(ArtList artList, ArtMovie movie) {
        return new DiscoveryDTO(artList.getArtNm(), artList.getArtShowAge(), movie.getDiscoverTitl(), movie.getDiscoverCont(), movie.getMvUrl());
    }


}
