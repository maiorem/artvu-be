package com.art.api.user.domain.model;

import com.art.api.user.domain.entity.Gender;
import lombok.*;

@Getter
@Setter
@Builder
public class MypageResponse {

    private String userId;
    private String userName;
    private String nickNm;
    private String profileImgUrl;
    private String email;
    private Gender gender;

    public MypageResponse(String userId, String userName, String nickNm, String profileImgUrl, String email, Gender gender) {
        this.userId = userId;
        this.userName = userName;
        this.nickNm = nickNm;
        this.profileImgUrl = profileImgUrl;
        this.email = email;
        this.gender = gender;
    }

    public static MypageResponse of(String userId, String userName, String nickNm, String profileImgUrl, String email, Gender gender) {
        return new MypageResponse(userId, userName, nickNm, profileImgUrl, email, gender);
    }

}
