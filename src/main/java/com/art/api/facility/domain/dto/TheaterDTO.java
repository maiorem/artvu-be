package com.art.api.facility.domain.dto;

import com.art.api.facility.domain.entity.ArtFacDetail;
import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TheaterDTO {

    private String artFacId;

    private String artFacNm;

    private String cityNm;

    private String artFacAddr;

    private Float latNo;

    private Float lonNo;

    public static TheaterDTO convertEntityToDto(ArtFacDetail artFacDetail) {
        return new TheaterDTO(
                artFacDetail.getArtFacId(),
                artFacDetail.getArtFacNm(),
                artFacDetail.getCityNm(),
                artFacDetail.getArtFacAddr(),
                artFacDetail.getLatNo(),
                artFacDetail.getLonNo()
        );
    }
}
