package com.art.api.facility.domain.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SeatDTO {

    String seatImgUrl;

    public SeatDTO(String seatImgUrl) {
        this.seatImgUrl = seatImgUrl;
    }


}
