package com.lucent.querydsl_example.domain.member.repository;

import static com.lucent.querydsl_example.domain.member.entity.QMember.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.lucent.querydsl_example.domain.config.QuerydslTestConfig;
import com.lucent.querydsl_example.domain.member.entity.Member;
import com.querydsl.core.Tuple;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(QuerydslTestConfig.class)
class MemberRepositoryTest {

	@Autowired
	private MemberRepository memberRepository;

	@BeforeEach
	void setUp() {
		Member member1 = Member.builder()
			.age(25)
			.name("수미")
			.salary(90000000)
			.build();

		Member member2 = Member.builder()
			.age(23)
			.name("동건")
			.salary(70000000)
			.build();

		Member member3 = Member.builder()
			.age(23)
			.name("몽미")
			.salary(70000000)
			.build();

		Member member4 = Member.builder()
			.age(27)
			.name("주희")
			.salary(85000000)
			.build();

		Member member5 = Member.builder()
			.age(30)
			.name("냥이")
			.salary(70000000)
			.build();

		memberRepository.save(member1);
		memberRepository.save(member2);
		memberRepository.save(member3);
		memberRepository.save(member4);
		memberRepository.save(member5);

		// Team team1 = Team.builder()
		// 	.name("A1")
		// 	.budget(70000000)
		// 	.build();
		//
		// Team team2 = Team.builder()
		// 	.name("A2")
		// 	.budget(90000000)
		// 	.build();
		//
		// Team team3 = Team.builder()
		// 	.name("B1")
		// 	.budget(100000000)
		// 	.build();
		//
		// Team team4 = Team.builder()
		// 	.name("B2")
		// 	.budget(50000000)
		// 	.build();
		//


	}

	@Test
	@DisplayName("이름이 수미인 회원을 찾을 때, 회원의 나이가 25이다.")
	public void findEqualNameMember() {
		// when
		Member result =  memberRepository.findEqualNameMember("수미");

		// then
		assertEquals(25, result.getAge());
	}


	@Test
	@DisplayName("이름이 수미가 아닌 회원을 찾을 때, 결과의 크기가 4이다")
	public void findNotEqualNameMember() {
		// when
		List<Member> result =  memberRepository.findNotEqualNameMember("수미");

		// then
		assertEquals(4, result.size());
	}

	@Test
	@DisplayName("이름이 null이 아닌 회원을 찾을 때, 결과의 크기가 5이다")
	public void findNotNullMember() {
		// when
		List<Member> result =  memberRepository.findNotNullNameMember();

		// then
		assertEquals(5, result.size());
	}

	@Test
	@DisplayName("나이가 20과 30인 회원을 찾을 때, 결과의 크기가 1이다")
	public void findAgeInMember() {
		// when
		List<Member> result =  memberRepository.findAgeInMember(20, 30);

		// then
		assertEquals(1, result.size());
	}

	@Test
	@DisplayName("나이가 20과 30이 아닌 회원을 찾을 때, 결과의 크기가 4이다")
	public void findAgeNotInMember() {
		// when
		List<Member> result =  memberRepository.findAgeNotInMember(20, 30);

		// then
		assertEquals(4, result.size());
	}

	@Test
	@DisplayName("나이가 20에서 30 사이인 회원을 찾을 때, 결과의 크기가 5이다")
	public void findAgeBetweenMember() {
		// when
		List<Member> result =  memberRepository.findAgeBetweenMember(20, 30);

		// then
		assertEquals(5, result.size());
	}

	@Test
	@DisplayName("이름이 '수'로 시작하는 회원을 찾을 때, 결과의 크기가 1이다")
	public void findLikeNameMember() {
		// when
		List<Member> result =  memberRepository.findLikeNameMember("수%");

		// then
		assertEquals(1, result.size());
	}

	@Test
	@DisplayName("이름에 '미'가 포함된 첫 번째 회원을 찾을 때, 이름이 '몽미'인지 확인한다")
	public void findContainsNameMember() {
		// when
		Member member =  memberRepository.findContainsNameFirstMember("미");

		// then
		assertEquals(member.getName(),"몽미");
	}

	@Test
	@DisplayName("이름이 '수'로 시작하는 회원을 찾을 때, 결과의 크기가 1이다")
	public void findStartsWithNameMember() {
		// when
		List<Member> result =  memberRepository.findStartsWithNameMember("수");

		// then
		assertEquals(1, result.size());
	}

	@Test
	@DisplayName("나이가 23 이상인 회원을 찾을 때, 결과의 크기가 5이다")
	public void findAgeGoeMember() {
		// when
		List<Member> result =  memberRepository.findAgeGoeMember(23);

		// then
		assertEquals(5, result.size());
	}

	@Test
	@DisplayName("나이가 23 초과인 회원을 찾을 때, 결과의 크기가 3이다")
	public void findAgeGtMember() {
		// when
		List<Member> result =  memberRepository.findAgeGtMember(23);

		// then
		assertEquals(3, result.size());
	}

	@Test
	@DisplayName("나이가 25 이하인 회원을 찾을 때, 결과의 크기가 3이다")
	public void findAgeLoeMember() {
		// when
		List<Member> result =  memberRepository.findAgeLoeMember(25);

		// then
		assertEquals(3, result.size());
	}

	@Test
	@DisplayName("나이가 25 미만인 회원을 찾을 때, 결과의 크기가 2이다")
	public void findAgeLtMember() {
		// when
		List<Member> result =  memberRepository.findAgeLtMember(25);

		// then
		assertEquals(2, result.size());
	}

	@Test
	@DisplayName("회원 데이터의 집함 함수를 수행할 때, 카운트, 합계, 평균, 최대값, 최소값이 예상된 값과 일치한다")
	public void aggregation() {
		// when
		Tuple result = memberRepository.aggregationMember();

		// then
		assertEquals(result.get(member.count()), 5);
		assertEquals(result.get(member.age.sum()), 128);
		assertEquals(result.get(member.age.avg()), 25.6);
		assertEquals(result.get(member.age.max()), 30);
		assertEquals(result.get(member.age.min()), 23);
	}

	@Test
	@DisplayName("회원의 나이를 내림차순으로 정렬하고, 나이가 같다면 이름으로 오름차순 정렬하며, 이름이 없는 회원은 맨 마지막에 올 때, 예상된 순서와 일치한다")
	public void sortMember() {
		// when
		List<Member> members =  memberRepository.sortMember();

		// then
		assertEquals(members.get(0).getName(), "냥이");
		assertEquals(members.get(1).getName(), "주희");
		assertEquals(members.get(2).getName(), "수미");
		assertEquals(members.get(3).getName(), "동건");
		assertEquals(members.get(4).getName(), "몽미");
	}

	@Test
	@DisplayName("멤버를 나이 내림차순, 이름 오름차순으로 정렬하고, 1번째부터 2개의 멤버를 페이징할 때, 예상된 순서와 일치한다")
	public void pagingMember() {
		// when
		List<Member> members = memberRepository.pagingMember();

		// then
		assertEquals(2, members.size());
		assertEquals(members.get(0).getName(), "주희");
		assertEquals(members.get(1).getName(), "수미");
	}

	@Test
	@DisplayName("회원 목록을 페이지 번호 1, 사이즈 2로 페이징할 때, 두 번째 페이지의 회원 목록이 예상과 일치하고, 마지막 페이지에서는 카운트 쿼리를 실행한다")
	public void pagingMemberUsingPageImpl() {
		// given
		// 페이지 번호가 1이고, 사이즈가 2이므로 페이지 번호 0: 냥이, 주희 / 1: 수미, 동건 / 2 : 몽미
		PageRequest pageRequest = PageRequest.of(2, 2);

		// when
		Page<Member> result = memberRepository.pagingMemberUsingPageImpl(pageRequest);

		// then
		assertEquals(result.getContent().get(0).getName(), "몽미");
	}

	@Test
	@DisplayName("회원 목록을 페이지 번호 1, 사이즈 2로 페이징할 때, 두 번째 페이지의 회원 목록이 예상과 일치하고, 마지막 페이지에서는 카운트 쿼리를 실행하지 않는다")
	public void pagingMemberUsingPageUtil() {
		// given
		// 페이지 번호가 1이고, 사이즈가 2이므로 페이지 번호 0: 냥이, 주희 / 1: 수미, 동건 / 2 : 몽미
		PageRequest pageRequest = PageRequest.of(2, 2);

		// when
		Page<Member> result = memberRepository.pagingMemberUsingPageUtil(pageRequest);

		// then
		assertEquals(result.getContent().get(0).getName(), "몽미");
	}




}
