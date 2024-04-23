package com.art.api.product.domain.dto;

import com.art.api.facility.domain.entity.ArtFacDetail;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ArtFacilityDto {

    private String artFacId;

    private String cityNm;

    private String artFacNm;

    public ArtFacilityDto(String artFacId, String cityNm, String artFacNm) {
        this.artFacId = artFacId;
        this.cityNm = cityNm;
        this.artFacNm = artFacNm;
    }

    public static ArtFacilityDto of(ArtFacDetail artFacDetail){
        return new ArtFacilityDto(artFacDetail.getArtFacId(), artFacDetail.getCityNm(), artFacDetail.getArtFacNm());
    }
}
