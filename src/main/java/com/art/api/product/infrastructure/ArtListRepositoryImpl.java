package com.art.api.product.infrastructure;


import com.art.api.product.domain.entity.ArtList;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.art.api.product.domain.entity.QArtList.artList;
import static com.art.api.common.domain.entity.QGenreList.genreList;
import static com.art.api.common.domain.entity.QArtArea.artArea;


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
        if(!StringUtils.isEmpty(search)) builder.and(artList.artNm.contains(search)).or(artList.copyText.contains(search));
        if(!StringUtils.isEmpty(genre)) builder.and(genreList.artGenreNm.eq(genre));
        return builder;
    }

    @Override
    public Page<ArtList> findSearchResult(Pageable pageable, String genre, String local, String search) {

        List<ArtList> result = jpaQueryFactory
                .selectFrom(artList)
                .where( isExistKeyword(genre, local, search) )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        Long count = artListCount();

        return new PageImpl<>(result, pageable, count);
    }
}
