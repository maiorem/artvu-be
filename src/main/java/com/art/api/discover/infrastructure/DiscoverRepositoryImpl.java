package com.art.api.discover.infrastructure;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class DiscoverRepositoryImpl implements DiscoverRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;




}
