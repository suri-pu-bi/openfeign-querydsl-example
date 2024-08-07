package com.lucent.querydsl_example.domain.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberQuerydslRepositoryImpl implements MemberQuerydslRepository {
	private final JPAQueryFactory queryFactory;
}
