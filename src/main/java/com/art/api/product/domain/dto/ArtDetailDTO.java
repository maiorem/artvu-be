package com.art.api.product.domain.dto;

import com.art.api.common.domain.entity.GenreList;
import com.art.api.product.domain.entity.ArtDetail;
import com.art.api.product.domain.entity.ArtImg;
import com.art.api.product.domain.entity.ArtList;
import com.art.api.product.domain.entity.ArtTime;
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
public class ArtDetailDTO {

    private String artId;

    private String artNm;

    private String artShowAge;

    private String status;

    private int orgPrice;

    private int minPrice;

    private String artRuntime;

    private String artStrDt;

    private String artEndDt;

    private int intermisMi;

    private String summary;

    private String contDetail;

    private ArtFacilityDto theater;

    private List<ArtImg> artImgList = new ArrayList<>();

    private List<GenreList> genreList = new ArrayList<>();

    @QueryProjection
    public ArtDetailDTO(String artId, String artNm, String artShowAge, String status, int orgPrice, int minPrice, String artStrDt, String artEndDt, String artRuntime, int intermisMi, String summary, String contDetail, ArtFacilityDto theater) {
        this.artId = artId;
        this.artNm = artNm;
        this.artShowAge = artShowAge;
        this.status = status;
        this.orgPrice = orgPrice;
        this.minPrice = minPrice;
        this.artStrDt = artStrDt;
        this.artEndDt = artEndDt;
        this.artRuntime = artRuntime;
        this.intermisMi = intermisMi;
        this.summary = summary;
        this.contDetail = contDetail;
        this.theater = theater;
    }

    public static ArtDetailDTO convertEntityToDto(ArtList artList, ArtDetail artDetail, ArtTime artTime){

        return new ArtDetailDTO(artList.getArtId(),
                                artList.getArtNm(),
                                artList.getArtShowAge(),
                                artList.getStatus(),
                                artList.getOrgPrice(),
                                artList.getMinPrice(),
                                artTime.getArtStrDt(),
                                artTime.getArtEndDt(),
                                artDetail.getArtRuntime(),
                                artDetail.getIntermisMi(),
                                artDetail.getSummary(),
                                artDetail.getContDetail(),
                                ArtFacilityDto.of(artList.getArtFacId()));
    }

}
