package com.art.api.discover.infrastructure;

import com.art.api.discover.domain.dto.DiscoveryDTO;
import com.art.api.product.domain.entity.ClsCode;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.art.api.discover.domain.entity.QArtMovie.artMovie;
import static com.art.api.product.domain.entity.QArtList.artList;
import static com.art.api.product.domain.entity.QArtImg.artImg;

@RequiredArgsConstructor
public class DiscoverRepositoryImpl implements DiscoverRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;


    public Page<DiscoveryDTO> retrieveDiscover(Pageable pageable){


        List<DiscoveryDTO> list = jpaQueryFactory
                .select(Projections.fields(DiscoveryDTO.class,
                        artList.artId,
                        artList.artNm,
                        artList.artShowAge,
                        artImg.imgUrl.as("posterImgUrl"),
                        artMovie.discoverTitl,
                        artMovie.discoverCont,
                        artMovie.mvUrl)
                )
                .from(artMovie)
                .join(artList)
                .on(artMovie.artlist.artId.eq(artList.artId))
                .leftJoin(artImg)
                .on(artImg.artList.artId.eq(artList.artId))
                .on(artImg.clsCode.eq(ClsCode.KOPIS))
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(artMovie.count())
                .from(artMovie)
                .join(artList)
                .on(artMovie.artlist.artId.eq(artList.artId))
                .leftJoin(artImg)
                .on(artImg.artList.artId.eq(artList.artId))
                .on(artImg.clsCode.eq(ClsCode.KOPIS));

        return PageableExecutionUtils.getPage(list, pageable, countQuery::fetchOne);
    }


}
