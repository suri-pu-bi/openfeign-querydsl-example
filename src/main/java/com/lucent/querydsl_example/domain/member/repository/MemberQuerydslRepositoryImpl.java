package com.lucent.querydsl_example.domain.member.repository;

import static com.lucent.querydsl_example.domain.manager.entity.QManager.*;
import static com.lucent.querydsl_example.domain.member.entity.QMember.*;
import static com.lucent.querydsl_example.domain.team.entity.QTeam.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.lucent.querydsl_example.domain.member.entity.Member;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
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

	@Override
	public Tuple aggregationMember() {
		return queryFactory.select(
			member.count(),
			member.age.sum(),
			member.age.avg(),
			member.age.max(),
			member.age.min()
		)
			.from(member)
			.fetchOne();
	}

	@Override
	public List<Member> sortMember() {
		return queryFactory
			.selectFrom(member)
			.orderBy(member.age.desc(), // 내림차순
				member.name.asc().nullsLast()) // 나이가 같다면, 오름차순
			.fetch();
	}

	@Override
	public List<Member> pagingMember() {
		return queryFactory
			.selectFrom(member)
			.orderBy(member.age.desc(),
				member.name.asc())
			.offset(1) // 시작 지점
			.limit(2) // 개수 제한
			.fetch();
	}

	@Override
	public Page<Member> pagingMemberUsingPageImpl(Pageable pageable) {
		List<Member> result = queryFactory
			.selectFrom(member)
			.orderBy(member.age.desc(),
				member.name.asc())
			.offset(pageable.getOffset()) // 시작 지점
			.limit(pageable.getPageSize()) // 개수 제한
			.fetch();

		int totalCount = queryFactory
			.select(member.count())
			.from(member)
			.orderBy(
				member.age.desc(),
				member.name.asc()
			).fetch().size();

		return new PageImpl<>(result, pageable, totalCount);
	}

	@Override
	public Page<Member> pagingMemberUsingPageUtil(Pageable pageable) {
		List<Member> result = queryFactory
			.selectFrom(member)
			.orderBy(member.age.desc(),
				member.name.asc())
			.offset(pageable.getOffset()) // 시작 지점
			.limit(pageable.getPageSize()) // 개수 제한
			.fetch();

		JPAQuery<Long> countQuery = queryFactory
			.select(member.count())
			.from(member)
			.orderBy(
				member.age.desc(),
				member.name.asc()
			);

		return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchOne);
	}

	@Override
	public List<Member> joinWhereTeamName(String teamName) {
		return queryFactory
			.selectFrom(member)
			.join(member.team, team)
			.where(team.name.eq(teamName))
			.fetch();
	}

	@Override
	public List<Member> innerJoinWhereTeamName(String teamName) {
		return queryFactory
			.selectFrom(member)
			.innerJoin(member.team, team)
			.where(team.name.eq(teamName))
			.fetch();
	}

	@Override
	public List<Tuple> leftJoinOnTeamName(String teamName) {
		return queryFactory
			.select(member, team)
			.from(member)
			.leftJoin(member.team, team)
			.on(team.name.eq(teamName))
			.fetch();
	}

	@Override
	public List<Tuple> rightJoinOnTeamName(String teamName) {
		return queryFactory
			.select(member, team)
			.from(member)
			.rightJoin(member.team, team)
			.on(team.name.eq(teamName))
			.fetch();
	}

	@Override
	public List<Member> thetaJoinWhereMemberSalaryMoreThanManagerSalary() {
		return queryFactory
			.select(member)
			.from(member, manager)
			.where(member.salary.loe(manager.salary))
			.groupBy(member.id)
			.fetch();
	}

	@Override
	public List<Tuple> lefJoinHaveNotRelationShipOnMemberSalaryMoreThanManagerSalary() {
		return queryFactory
			.select(member, manager)
			.from(member)
			.leftJoin(manager)
			.on(member.salary.loe(manager.salary))
			.orderBy(manager.name.asc().nullsLast())
			.fetch();

	}
}
