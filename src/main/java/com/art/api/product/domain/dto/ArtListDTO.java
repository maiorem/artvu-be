package com.art.api.product.domain.dto;

import com.art.api.common.domain.entity.GenreList;
import com.art.api.product.domain.entity.ArtList;
import com.querydsl.core.annotations.QueryProjection;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class ArtListDTO {

    private String artId;
    private String artNm;
    private String area;
    private String posterUrl;
    private List<GenreList> genreList = new ArrayList<>();

    @QueryProjection
    public ArtListDTO(ArtList artList, List<GenreList> genres) {
        this.artId = artList.getArtId();
        this.artNm = artList.getArtNm();
        this.area = artList.getAreaCode().getAreaNm();
        this.posterUrl = artList.getArtImgList().stream().filter(
                img -> img.getClsCode().equals("1")
        ).findFirst().get().getImgUrl();

        genres.forEach(genre -> this.genreList.add(
                GenreList.builder()
                        .artGenreId(genre.getArtGenreId())
                        .artGenreNm(genre.getArtGenreNm())
                        .build()
        ));

    }


}
