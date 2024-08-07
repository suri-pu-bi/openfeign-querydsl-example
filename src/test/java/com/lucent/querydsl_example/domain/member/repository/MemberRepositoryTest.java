package com.lucent.querydsl_example.domain.member.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import com.lucent.querydsl_example.domain.config.QuerydslTestConfig;
import com.lucent.querydsl_example.domain.member.entity.Member;


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



}
