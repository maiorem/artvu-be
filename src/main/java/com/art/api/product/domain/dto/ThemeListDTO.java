package com.art.api.product.domain.dto;


import com.art.api.product.domain.entity.Theme;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ThemeListDTO {

    private String themeNm;

    private int themeOrdNo;

    private List<ThemeDTO> contents;

    @QueryProjection
    public ThemeListDTO(int themeOrdNo, String themeNm) {
        this.themeOrdNo = themeOrdNo;
        this.themeNm = themeNm;
    }

    public static ThemeListDTO convertEntityToDto(Theme theme) {
        return new ThemeListDTO(theme.getThemeOrdNo(), theme.getThemeNm());
    }

}
