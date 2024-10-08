package com.lucent.querydsl_example.domain.member.repository;

import java.awt.*;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.lucent.querydsl_example.domain.member.dto.MemberResponse;
import com.lucent.querydsl_example.domain.member.dto.TeamMemberCount;
import com.lucent.querydsl_example.domain.member.entity.Member;
import com.lucent.querydsl_example.domain.team.entity.Team;
import com.querydsl.core.Tuple;

public interface MemberQuerydslRepository {
	// 동일 여부
	Member findEqualNameMember(String name);
	List<Member> findNotEqualNameMember(String name);
	List<Member> findNotNullNameMember();

	// 포함 여부
	List<Member> findAgeInMember(int minAge, int maxAge);
	List<Member> findAgeNotInMember(int minAge, int maxAge);
	List<Member> findAgeBetweenMember(int minAge, int maxAge);

	// 문자열
	List<Member> findLikeNameMember(String name);
	List<Member> findStartsWithNameMember(String name);
	Member findContainsNameFirstMember(String name);

	// 수 비교
	List<Member> findAgeGoeMember(int age);
	List<Member> findAgeGtMember(int age);
	List<Member> findAgeLoeMember(int age);
	List<Member> findAgeLtMember(int age);

	// 집합
	Tuple aggregationMember();

	// 정렬
	List<Member> sortMember();

	// 페이징
	List<Member> pagingMember();
	Page<Member> pagingMemberUsingPageImpl(Pageable pageable);
	Page<Member> pagingMemberUsingPageUtil(Pageable pageable);

	// 조인
	List<Member> joinWhereTeamName(String teamName);
	List<Member> innerJoinWhereTeamName(String teamName);
	List<Tuple> leftJoinOnTeamName(String teamName);
	List<Tuple> rightJoinOnTeamName(String teamName);
	List<Member> thetaJoinWhereMemberSalaryMoreThanManagerSalary();
	List<Tuple> lefJoinHaveNotRelationShipOnMemberSalaryMoreThanManagerSalary();
	List<Member> fetchJoin(String teamName);

	// 서브쿼리
	List<TeamMemberCount> subQueryInSelect();
	Member subQueryWhere(Long teamId);
	List<Member> subQueryUsingIn();

	// case 문
	List<String> basicCase();
	List<Integer> complexCase();

	// 상수, 문자 더하기
	List<Tuple> addConstant();
	List<String> addString();

	// distinct
	List<Integer> distinctSalary(Long teamId);

	// 동적 쿼리
	List<Member> searchMemberUsingBuilder(String name, Integer age);
	List<Member> searchMemberUsingExpression(String name, Integer age);

	// 벌크 연산
	Long updateBulk();
	Long deleteBulk();

	// fetchJoin with On
	List<MemberResponse> notFetchJoinUsingLeftJoinWithOn();

}
