package com.art.api.artcrew.domain.dto;

import com.art.api.artcrew.domain.entity.ArtActors;
import com.art.api.product.domain.entity.ArtDetail;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class PeopleDTO {

    private String prodCompNm;

    private String agencyNm;

    private String sponsorNm;

    private String organizerNm;

    private String artStaff;

    private String artActor;

    @QueryProjection
    public PeopleDTO(String prodCompNm, String agencyNm, String sponsorNm, String organizerNm, String artStaff, String artActor) {
        this.prodCompNm = prodCompNm;
        this.agencyNm = agencyNm;
        this.sponsorNm = sponsorNm;
        this.organizerNm = organizerNm;
        this.artStaff = artStaff;
        this.artActor = artActor;
    }

    public static PeopleDTO convertEntityToDto(ArtDetail artDetail) {
        return new PeopleDTO(artDetail.getProdCompNm(), artDetail.getAgencyNm(), artDetail.getSponsorNm(), artDetail.getOrganizerNm(), artDetail.getArtStaff(), artDetail.getArtActor());
    }
}
