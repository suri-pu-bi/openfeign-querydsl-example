package com.lucent.querydsl_example.domain.member.repository;

import static com.lucent.querydsl_example.domain.member.entity.QMember.*;

import java.util.List;

import com.lucent.querydsl_example.domain.member.entity.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberQuerydslRepositoryImpl implements MemberQuerydslRepository {
	private final JPAQueryFactory queryFactory;

	@Override
	public Member findEqualNameMember(String name) {
		return queryFactory.selectFrom(member)
			.where(member.name.eq((name)))
			.fetchOne();
	}

	@Override
	public List<Member> findNotEqualNameMember(String name) {
		return queryFactory.selectFrom(member)
			.where(member.name.ne(name))
			.fetch();
	}


	@Override
	public List<Member> findNotNullNameMember() {
		return queryFactory.selectFrom(member)
			.where(member.name.isNotNull())
			.fetch();
	}

	@Override
	public List<Member> findAgeInMember(int minAge, int maxAge) {
		return queryFactory.selectFrom(member)
			.where(member.age.in(minAge, maxAge))
			.fetch();
	}

	@Override
	public List<Member> findAgeNotInMember(int minAge, int maxAge) {
		return queryFactory.selectFrom(member)
			.where(member.age.notIn(minAge, maxAge))
			.fetch();
	}

	@Override
	public List<Member> findAgeBetweenMember(int minAge, int maxAge) {
		return queryFactory.selectFrom(member)
			.where(member.age.between(minAge, maxAge))
			.fetch();
	}

	@Override
	public List<Member> findLikeNameMember(String name) {
		return queryFactory.selectFrom(member)
			.where(member.name.like(name))
			.fetch();
	}

	@Override
	public List<Member> findStartsWithNameMember(String name) {
		return queryFactory.selectFrom(member)
			.where(member.name.startsWith(name))
			.fetch();
	}

	@Override
	public Member findContainsNameFirstMember(String name) {
		return queryFactory.selectFrom(member)
			.where(member.name.contains(name))
			.orderBy(member.age.asc())
			.fetchFirst();
	}

	@Override
	public List<Member> findAgeGoeMember(int age) {
		return queryFactory.selectFrom(member)
			.where(member.age.goe(age))
			.fetch();
	}

	@Override
	public List<Member> findAgeGtMember(int age) {
		return queryFactory.selectFrom(member)
			.where(member.age.gt(age))
			.fetch();
	}

	@Override
	public List<Member> findAgeLoeMember(int age) {
		return queryFactory.selectFrom(member)
			.where(member.age.loe(age))
			.fetch();
	}

	@Override
	public List<Member> findAgeLtMember(int age) {
		return queryFactory.selectFrom(member)
			.where(member.age.lt(age))
			.fetch();
	}
}
