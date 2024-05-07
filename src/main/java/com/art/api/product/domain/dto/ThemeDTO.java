package com.art.api.product.domain.dto;

import com.art.api.product.domain.entity.ArtList;
import com.art.api.product.domain.entity.Theme;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ThemeDTO {

    private String artId;

    private int themeOrdNo;

    private String themeNm;

    private String artNm;

    private String copyText;

    private String posterImgUrl;

    @QueryProjection
    public ThemeDTO(String artId, int themeOrdNo, String themeNm, String artNm, String copyText) {
        this.artId = artId;
        this.themeOrdNo = themeOrdNo;
        this.themeNm = themeNm;
        this.artNm = artNm;
        this.copyText = copyText;
    }

    public static ThemeDTO convertEntityToDto(ArtList artList, Theme theme) {
        return new ThemeDTO(artList.getArtId(), theme.getThemeOrdNo(), theme.getThemeNm(), artList.getArtNm(), artList.getCopyText());
    }

}
