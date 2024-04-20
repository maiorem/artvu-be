package com.art.api.user.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserInfoRequest {

    @Schema(description = "닉네임")
    private String nickNm;

    @Schema(description = "남(MALE), 여(FEMALE)")
    private String gender;

    @Schema(description = "양식 : 20240420")
    private String birthDate;

    @Schema(description = "직업")
    private String jobNm;

    @Schema(description = "내외국인 구분 (내국인:NATIVE, 외국인:FOREIGNER)")
    private String nationalType;

    @Schema(description = "전화번호")
    private String tellNo;

}
