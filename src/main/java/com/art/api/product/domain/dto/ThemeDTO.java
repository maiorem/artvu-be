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
    private String artCateNm;
    private String artShowAge;
    private String artSaleYn;
    private boolean isSaved;

    private String posterImgUrl;

    @QueryProjection
    public ThemeDTO(String artId, int themeOrdNo, String themeNm, String artNm, String copyText, String artCateNm, String artShowAge, String artSaleYn) {
        this.artId = artId;
        this.themeOrdNo = themeOrdNo;
        this.themeNm = themeNm;
        this.artNm = artNm;
        this.copyText = copyText;
        this.artCateNm = artCateNm;
        this.artShowAge = artShowAge;
        this.artSaleYn = artSaleYn;
    }

    public static ThemeDTO convertEntityToDto(ArtList artList, Theme theme) {
        return new ThemeDTO(
                artList.getArtId(),
                theme.getThemeOrdNo(),
                theme.getThemeNm(),
                artList.getArtNm(),
                artList.getCopyText(),
                artList.getArtCateNm(),
                artList.getArtShowAge(),
                artList.getArtSaleYn());
    }

}
