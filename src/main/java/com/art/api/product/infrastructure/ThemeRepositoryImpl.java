package com.art.api.product.infrastructure;

import com.art.api.product.domain.dto.ThemeDTO;
import com.art.api.product.domain.dto.ThemeListDTO;
import com.art.api.product.domain.entity.ClsCode;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import static com.art.api.product.domain.entity.QArtList.artList;
import static com.art.api.product.domain.entity.QTheme.theme;
import static com.art.api.product.domain.entity.QThemeHist.themeHist;
import static com.art.api.product.domain.entity.QArtImg.artImg;
import static com.art.api.product.domain.entity.QArtGenreMppg.artGenreMppg;
import static com.art.api.user.domain.entity.QSaveHist.saveHist;

@RequiredArgsConstructor
public class ThemeRepositoryImpl implements ThemeRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ThemeDTO> findMainTheme() {
        return jpaQueryFactory
                .select(Projections.fields(ThemeDTO.class,
                        artList.artId,
                        theme.themeOrdNo,
                        theme.themeNm,
                        artList.artNm,
                        artList.copyText,
                        artList.artCateNm,
                        artList.artShowAge,
                        artList.artSaleYn,
                        artImg.imgUrl.as("posterImgUrl"))
                )
                .from(theme)
                .join(themeHist)
                .on(theme.themeId.eq(themeHist.theme.themeId))
                .join(artList)
                .on(artList.artId.eq(themeHist.artList.artId))
                .leftJoin(artImg)
                .on(artList.artId.eq(artImg.artList.artId))
                .on(artImg.clsCode.eq(ClsCode.KOPIS))
                .orderBy(theme.themeOrdNo.asc())
                .fetch();

    }
}
