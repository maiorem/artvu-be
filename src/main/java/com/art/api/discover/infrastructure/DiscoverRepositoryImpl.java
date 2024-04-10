package com.art.api.discover.infrastructure;

import com.art.api.discover.domain.entity.ArtMovie;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.art.api.discover.domain.entity.QArtMovie.artMovie;

@RequiredArgsConstructor
public class DiscoverRepositoryImpl implements DiscoverRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    private Long discoverCount() {
        return jpaQueryFactory.
                select(artMovie.count())
                .from(artMovie)
                .fetchOne();
    }

    public Page<ArtMovie> retrieveDiscover(Pageable pageable){
        List<ArtMovie> result = jpaQueryFactory
                .selectFrom(artMovie)
                .fetch();
        Long count = discoverCount();
        return new PageImpl<>(result, pageable, count);
    }


}
