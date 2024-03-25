package com.art.api.product.infrastructure;


import com.art.api.common.domain.entity.GenreList;
import com.art.api.product.domain.dto.ArtListDTO;
import com.art.api.product.domain.entity.ArtList;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.art.api.product.domain.entity.QArtList.artList;
import static com.art.api.product.domain.entity.QArtDetail.artDetail;
import static com.art.api.product.domain.entity.QArtFacDetail.artFacDetail;
import static com.art.api.product.domain.entity.QArtImg.artImg;
import static com.art.api.product.domain.entity.QArtGenreMppg.artGenreMppg;
import static com.art.api.common.domain.entity.QGenreList.genreList;
import static com.art.api.common.domain.entity.QArtArea.artArea;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

@RequiredArgsConstructor
public class ArtListRepositoryImpl implements ArtListRepositoryCustum {

    private final JPAQueryFactory jpaQueryFactory;

    private Long artListCount() {
        return jpaQueryFactory.
                select(artList.count())
                .from(artList)
                .fetchOne();
    }

    private BooleanBuilder isExistKeyword(String genre, String local, String search) {
        BooleanBuilder builder = new BooleanBuilder();
        if(!StringUtils.isEmpty(local)) builder.and(artArea.areaNm.eq(local));
        if(!StringUtils.isEmpty(search)) builder.and(artList.artNm.contains(search));
        if(!StringUtils.isEmpty(genre)) builder.and(genreList.artGenreNm.eq(genre));
        return builder;
    }

    @Override
    public Page<ArtListDTO> findSearchResult(Pageable pageable, String genre, String local, String search) {

        List<ArtListDTO> result = jpaQueryFactory
                .select(Projections.fields(ArtListDTO.class,
                        artList.artNm,
                        artList.copyText,
                        artArea.areaNm,
                        genreList.artGenreNm))
                .from(artList, genreList, artArea)
                .leftJoin(artList.artGenreMppgs, artGenreMppg)
                .leftJoin(artGenreMppg.genreList, genreList)
                .leftJoin(artList.areaCode, artArea)
                .fetchJoin()
                .where( isExistKeyword(genre, local, search) )
                .distinct()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        Long count = artListCount();

        return new PageImpl<>(result, pageable, count);
    }
}
