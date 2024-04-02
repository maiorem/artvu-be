package com.art.api.discover.domain.dto;


import com.art.api.common.domain.entity.GenreList;
import com.art.api.discover.domain.entity.ArtMovie;
import com.art.api.product.domain.entity.ArtDetail;
import com.art.api.product.domain.entity.ArtList;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class DiscoveryDTO {

    private String artNm;

    private String artShowAge;

    private String discoverTitl;

    private String discoverCont;

    private String mvUrl;

    private List<GenreList> genreList = new ArrayList<>();

    public DiscoveryDTO() {
    }

    @QueryProjection
    public DiscoveryDTO(String artNm, String artShowAge, String discoverTitl, String discoverCont, String mvUrl) {
        this.artNm = artNm;
        this.artShowAge = artShowAge;
        this.discoverCont = discoverCont;
        this.discoverTitl = discoverTitl;
        this.mvUrl = mvUrl;
    }

    public static DiscoveryDTO convertEntityToDto(ArtList artList, ArtDetail art, ArtMovie movie) {
        return new DiscoveryDTO(artList.getArtNm(), art.getArtShowAge(), movie.getDiscoverTitl(), movie.getDiscoverCont(), movie.getMvUrl());
    }


}
