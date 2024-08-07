package com.lucent.querydsl_example.domain.team.repository;

import com.querydsl.core.QueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TeamQuerydslRepositoryImpl {
	private final JPAQueryFactory queryFactory;
}
