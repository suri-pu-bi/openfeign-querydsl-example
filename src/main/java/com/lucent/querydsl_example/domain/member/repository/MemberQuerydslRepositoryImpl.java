package com.lucent.querydsl_example.domain.member.repository;

import static com.lucent.querydsl_example.domain.manager.entity.QManager.*;
import static com.lucent.querydsl_example.domain.member.entity.QMember.*;
import static com.lucent.querydsl_example.domain.team.entity.QTeam.*;
import static com.querydsl.core.types.ExpressionUtils.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import com.lucent.querydsl_example.domain.member.dto.MemberResponse;
import com.lucent.querydsl_example.domain.member.dto.TeamMemberCount;
import com.lucent.querydsl_example.domain.member.entity.Member;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
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

	@Override
	public List<Member> fetchJoin(String teamName) {
		return queryFactory
			.selectFrom(member)
			.join(member.team, team).fetchJoin()
			.where(team.name.eq(teamName))
			.fetch();
	}

	@Override
	public List<TeamMemberCount> subQueryInSelect() {
		return queryFactory.select(
			Projections.fields(TeamMemberCount.class,
			team.name.as("teamName"),
				ExpressionUtils.as(
					JPAExpressions.select(count(member.id))
						.from(member)
						.where(member.team.eq(team)),
					"memberCount")
				))
			.from(team)
			.fetch();
	}

	@Override
	public Member subQueryWhere(Long teamId) {
		return queryFactory
			.selectFrom(member)
			.where(member.salary.eq(
				JPAExpressions
					.select(member.salary.max())
					.from(member)
					.where(member.team.id.eq(teamId))))
			.fetchFirst();
	}

	@Override
	public List<Member> subQueryUsingIn() {
		return queryFactory
			.selectFrom(member)
			.where(member.salary.in(
				JPAExpressions
					.select(member.salary)
					.from(member)
					.where(member.salary.gt(
						JPAExpressions
							.select(member.salary.avg().intValue())
							.from(member))
					)
				)
			).fetch();
	}

	@Override
	public List<String> basicCase() {
		return queryFactory
			.select(member.age
				.when(23).then("23")
				.when(25).then("25")
				.otherwise("기타"))
			.from(member)
			.fetch();
	}

	@Override
	public List<Integer> complexCase() {
		return queryFactory
			.select(new CaseBuilder()
				.when(member.age.between(20, 25)).then(member.age.add(1))
				.when(member.age.between(26, 30)).then(member.age.add(-1))
				.otherwise(member.age))
			.from(member)
			.fetch();
	}

	@Override
	public List<Tuple> addConstant() {
		return queryFactory
			.select(member.name, Expressions.constant("A"))
			.from(member)
			.fetch();
	}

	@Override
	public List<String> addString() {
		return queryFactory
			.select(member.name.concat("_").concat(member.age.stringValue()).prepend("HI_"))
			.from(member)
			.fetch();

	}

	@Override
	public List<Integer> distinctSalary(Long teamId) {
		return queryFactory
			.select(member.salary).distinct()
			.from(member)
			.join(member.team, team)
			.where(team.id.eq(teamId))
			.fetch();
	}

	@Override
	public List<Member> searchMemberUsingBuilder(String name, Integer age) {
		BooleanBuilder builder = new BooleanBuilder();

		if (StringUtils.hasText(name)) {
			builder.and(member.name.eq(name));
		}
		if (age != null) {
			builder.and(member.age.eq(age));
		}
		return queryFactory
			.selectFrom(member)
			.where(builder)
			.fetch();
	}

	@Override
	public List<Member> searchMemberUsingExpression(String name, Integer age) {
		return queryFactory
			.selectFrom(member)
			.where(allEq(name, age))
			.fetch();
	}

	@Override
	public Long updateBulk() {
		return queryFactory
			.update(member)
			.set(member.age, member.age.add(1))
			.where(member.age.goe(24))
			.execute();
	}

	@Override
	public Long deleteBulk() {
		return queryFactory
			.delete(member)
			.where(member.age.lt(25))
			.execute();
	}

	@Override
	public List<MemberResponse> notFetchJoinUsingLeftJoinWithOn() {
		return queryFactory
			.select(
				Projections.fields(MemberResponse.class,
					member.team.name.as("teamName"), member.name.as("memberName"), member.age.as("memberAge")))
			.from(member)
			.leftJoin(member.team, team)
			.on(team.name.eq("A1"))
			.fetch();
	}



	private BooleanExpression allEq(String name, Integer age) {
		return nameEq(name).and(ageEq(age));
	}

	private BooleanExpression nameEq(String name) {
		return name != null ? member.name.eq(name) : null;
	}

	private BooleanExpression ageEq(Integer age) {
		return age != null ? member.age.eq(age) : null;
	}
}
