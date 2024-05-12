package com.art.api.product.infrastructure;


import com.art.api.common.domain.entity.GenreList;
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
import static com.art.api.product.domain.entity.QArtGenreMppg.artGenreMppg;


@RequiredArgsConstructor
public class ArtListRepositoryImpl implements ArtListRepositoryCustum {

    private final JPAQueryFactory jpaQueryFactory;

    private Long artListCount() {
        return jpaQueryFactory.
                select(artList.count())
                .from(artList)
                .fetchOne();
    }

    private BooleanBuilder isExistKeyword(List<String> genre, String local, String search) {
        BooleanBuilder builder = new BooleanBuilder();
        if(!StringUtils.isEmpty(local)) builder.and(artList.areaCode.areaNm.eq(local));
        if(!StringUtils.isEmpty(search)) builder.and(
                artList.artNm.contains(search).or(artList.copyText.contains(search))
        );
        if(genre != null && !genre.isEmpty()) {
            builder.and(artGenreMppg.genreList.artGenreNm.in(genre));
        }
        return builder;
    }

    @Override
    public Page<ArtList> findSearchResult(Pageable pageable, List<String> genre, String local, String search) {

        List<ArtList> result = jpaQueryFactory
                .selectFrom(artList)
                .leftJoin(artList.artGenreMppgs, artGenreMppg)
                .on(artList.artId.eq(artGenreMppg.artList.artId))
                .where( isExistKeyword(genre, local, search) )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = jpaQueryFactory.
                select(artList.count())
                .from(artList)
                .leftJoin(artList.artGenreMppgs, artGenreMppg)
                .on(artList.artId.eq(artGenreMppg.artList.artId))
                .where( isExistKeyword(genre, local, search) )
                .fetchOne();

        return new PageImpl<>(result, pageable, count);
    }

    @Override
    public List<ArtList> findSuggestList(List<GenreList> genreList) {
        return jpaQueryFactory
                .selectFrom(artList)
                .leftJoin(artList.artGenreMppgs, artGenreMppg)
                .on(artList.artId.eq(artGenreMppg.artList.artId))
                .where(artGenreMppg.genreList.in(genreList))
                .limit(6)
                .fetch();
    }

}
