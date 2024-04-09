package com.art.api.product.domain.dto;

import com.art.api.common.domain.entity.GenreList;
import com.art.api.product.domain.entity.ArtList;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArtListDTO {

    private String artId;
    private String artNm;
    private String copyText;
    private String area;
    private String posterUrl;
    private List<GenreList> genreList = new ArrayList<>();


    @QueryProjection
    public ArtListDTO(String artId, String artNm, String copyText ){
        this.artId = artId;
        this.artNm = artNm;
        this.copyText = copyText;
    }

    public static ArtListDTO convertEntityToDto(ArtList artList) {
        return new ArtListDTO(artList.getArtId(), artList.getArtNm(), artList.getCopyText());
    }


}