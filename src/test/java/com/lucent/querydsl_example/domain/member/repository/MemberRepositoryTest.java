package com.lucent.querydsl_example.domain.member.repository;

import static com.lucent.querydsl_example.domain.manager.entity.QManager.*;
import static com.lucent.querydsl_example.domain.member.entity.QMember.*;
import static com.lucent.querydsl_example.domain.team.entity.QTeam.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.lucent.querydsl_example.domain.config.QuerydslTestConfig;
import com.lucent.querydsl_example.domain.manager.entity.Manager;
import com.lucent.querydsl_example.domain.manager.repository.ManagerRepository;
import com.lucent.querydsl_example.domain.member.entity.Member;
import com.lucent.querydsl_example.domain.member.dto.TeamMemberCount;
import com.lucent.querydsl_example.domain.team.dto.TeamResponse;
import com.lucent.querydsl_example.domain.team.entity.Team;
import com.lucent.querydsl_example.domain.team.repository.TeamRepository;
import com.querydsl.core.Tuple;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(QuerydslTestConfig.class)
class MemberRepositoryTest {

	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private TeamRepository teamRepository;
	@Autowired
	private ManagerRepository managerRepository;

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

		Team team1 = Team.builder()
			.name("A1")
			.budget(70000000)
			.build();

		Team team2 = Team.builder()
			.name("A2")
			.budget(90000000)
			.build();

		Team team3 = Team.builder()
			.name("B1")
			.budget(100000000)
			.build();

		Team team4 = Team.builder()
			.name("B2")
			.budget(50000000)
			.build();

		teamRepository.save(team1);
		teamRepository.save(team2);
		teamRepository.save(team3);
		teamRepository.save(team4);

		member1.updateTeam(team1);
		member2.updateTeam(team1);
		member3.updateTeam(team2);
		member4.updateTeam(team3);
		member5.updateTeam(team4);

		Manager manager1 = Manager.builder()
			.team(team1)
			.name("매니저1")
			.salary(70000000)
			.build();

		Manager manager2 = Manager.builder()
			.team(team2)
			.name("매니저2")
			.salary(60000000)
			.build();

		Manager manager3 = Manager.builder()
			.team(team3)
			.name("매니저3")
			.salary(50000000)
			.build();

		Manager manager4 = Manager.builder()
			.team(team4)
			.name("매니저4")
			.salary(80000000)
			.build();

		managerRepository.save(manager1);
		managerRepository.save(manager2);
		managerRepository.save(manager3);
		managerRepository.save(manager4);

		entityManager.flush();
		entityManager.clear();

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
	@DisplayName("회원 데이터의 집함 함수를 수행할 때, 카운트가 5, 합계가 128, 평균이 25.6, 최대값이 30, 최소값이 23이다.")
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
	@DisplayName("회원의 나이를 내림차순으로 정렬하고, 나이가 같다면 이름으로 오름차순 정렬하며, 정렬된 결과가 '냥이', '주희', '수미', '동건', '몽미'와 일치한다")
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
	@DisplayName("멤버를 나이 내림차순, 이름 오름차순으로 정렬하고, 1번째부터 2개의 멤버를 페이징할 때, 결과가 '주희', '수미'와 일치한다")
	public void pagingMember() {
		// when
		List<Member> members = memberRepository.pagingMember();

		// then
		assertEquals(2, members.size());
		assertEquals(members.get(0).getName(), "주희");
		assertEquals(members.get(1).getName(), "수미");
	}

	@Test
	@DisplayName("회원 목록을 페이지 번호 1, 사이즈 2로 페이징할 때, 두 번째 페이지의 회원 목록이 '몽미'이고, 마지막 페이지에서는 카운트 쿼리를 실행한다")
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
	@DisplayName("회원 목록을 페이지 번호 1, 사이즈 2로 페이징할 때, 두 번째 페이지의 회원 목록이 '몽미'이고, 마지막 페이지에서는 카운트 쿼리를 실행하지 않는다")
	public void pagingMemberUsingPageUtil() {
		// given
		// 페이지 번호가 1이고, 사이즈가 2이므로 페이지 번호 0: 냥이, 주희 / 1: 수미, 동건 / 2 : 몽미
		PageRequest pageRequest = PageRequest.of(2, 2);

		// when
		Page<Member> result = memberRepository.pagingMemberUsingPageUtil(pageRequest);

		// then
		assertEquals(result.getContent().get(0).getName(), "몽미");
	}

	@Test
	@DisplayName("팀 이름이 A1인 팀에 조인하여 해당 팀의 회원을 조회할 때, 회원 수가 2명이고 첫 번째 회원의 이름은 '수미'이다")
	public void joinWhereTeamName(){
		// given
		String teamName = "A1";

		// when
		List<Member> result = memberRepository.joinWhereTeamName(teamName);

		// then
		assertEquals(result.size(), 2);
		assertEquals(result.get(0).getName(), "수미");
	}

	@Test
	@DisplayName("팀 이름이 A1인 팀에 대해 내부 조인하여 회원을 조회할 때, 회원 수가 2명이고 첫 번째 회원의 이름은 '수미'이다")
	public void innerJoinWhereTeamName(){
		// given
		String teamName = "A1";

		// when
		List<Member> result = memberRepository.innerJoinWhereTeamName(teamName);

		// then
		assertEquals(result.size(), 2);
		assertEquals(result.get(0).getName(), "수미");
	}

	@Test
	@DisplayName("팀 이름이 A1인 팀에 대해 왼쪽 조인하여 회원을 조회할 때, 첫 번째 결과의 회원 이름이 '수미'이고 팀 이름이 'A1'이며, 세 번째 결과의 팀이 null이다")
	public void leftJoinOnTeamName(){
		// given
		String teamName = "A1";

		// when
		List<Tuple> result = memberRepository.leftJoinOnTeamName(teamName);

		// then
		assertEquals(result.get(0).get(member).getName(), "수미");
		assertEquals(result.get(0).get(team).getName(), "A1");
		assertNull(result.get(2).get(team));
	}

	@Test
	@DisplayName("팀 이름이 A1인 팀에 대해 오른쪽 조인하여 회원을 조회할 때, 첫 번째 결과의 회원 이름이 '수미'이고 팀 이름이 'A1'이며, 세 번째 결과의 팀은 null이 아니고 회원은 null이다")
	public void rightJoinOnTeamName(){
		// given
		String teamName = "A1";

		// when
		List<Tuple> result = memberRepository.rightJoinOnTeamName(teamName);

		// then
		assertEquals(result.get(0).get(member).getName(), "수미");
		assertEquals(result.get(0).get(team).getName(), "A1");
		assertNotNull(result.get(2).get(team));
		assertNull(result.get(2).get(member));
	}

	@Test
	@DisplayName("세타 조인을 사용하여 회원 급여가 매니저 급여보다 낮은 경우를 조회할 때, 결과가 3개이며, 첫번째 회원의 이름은 동건이다.")

	public void thetaJoinWhereMangerName(){
		// when
		List<Member> result = memberRepository.thetaJoinWhereMemberSalaryMoreThanManagerSalary();

		// then
		assertEquals(result.size(), 3);
		assertEquals(result.get(0).getName(), "동건");
	}

	@Test
	@DisplayName("연관관계 없는 엔티티끼리 왼쪽 조인을 사용하여 회원 급여가 매니저 급여보다 낮은 경우를 조회할 때, 결과가 8개이며, 8번째 결과의 매니저는 null이고, 세번쨰 매니저의 이름이 '매니저1', 네번째 매니저의 이름이 '매니저4'이다.")
	public void lefJoinHaveNotRelationShipOnMemberSalaryMoreThanManagerSalary(){
		// when
		List<Tuple> result = memberRepository.lefJoinHaveNotRelationShipOnMemberSalaryMoreThanManagerSalary();

		// then
		assertEquals(result.size(), 8);
		assertNull(result.get(7).get(manager));
		assertEquals(result.get(2).get(manager).getName(), "매니저1");
		assertEquals(result.get(3).get(manager).getName(), "매니저4");
	}

	@Test
	@DisplayName("fetchJoin을 이용하여 팀 이름이 'A1'인 회원을 조회할 때, 회원의 팀이 프록시가 아닌 실제로 로드된 상태인지 검증한다")
	public void fetchJoin() {
		// given
		String teamName = "A1";
		// when
		List<Member> result = memberRepository.fetchJoin(teamName);
		// then
		assertTrue(entityManager.getEntityManagerFactory().getPersistenceUnitUtil().isLoaded(result.get(0).getTeam()));
	}

	@Test
	@DisplayName("멤버 카운트를 팀별로 조회할 때, 반환된 리스트의 크기가 4이고, 첫 번째 팀의 이름이 A1, 그 팀의 멤버 수가 2이며, 두 번째 팀의 이름이 A2이다")
	public void subQueryInSelect() {
		// when
		List<TeamMemberCount> result = memberRepository.subQueryInSelect();

		// then
		assertEquals(result.size(), 4);
		assertEquals(result.get(0).getTeamName(), "A1");
		assertEquals(result.get(0).getMemberCount(), 2);
		assertEquals(result.get(1).getTeamName(), "A2");
	}

	@Test
	@DisplayName("특정 팀 ID를 기반으로 최고 급여를 받는 멤버를 조회할 때, 해당 멤버의 이름이 수미이고, 그 멤버가 속한 팀의 이름이 A1이다")
	public void subQueryWhere() {
		// given
		Long teamId = 1L;

		// when
		Member member = memberRepository.subQueryWhere(1L);

		// then
		assertEquals(member.getName(), "수미");
		assertEquals(member.getTeam().getName(), "A1");
	}

	@Test
	@DisplayName("평균 급여보다 높은 급여를 가진 멤버들을 조회할 때, 결과 크기가 2이고, 첫번쨰 멤버는 수미이며, 두번째 멤버는 주희이다")
	public void subQueryWhereIn() {
		// when
		List<Member> members = memberRepository.subQueryUsingIn();

		// then
		assertEquals(members.size(), 2);
		assertEquals(members.get(0).getName(), "수미");
		assertEquals(members.get(1).getName(), "주희");
	}

	@Test
	public void projectionTeam() {
		// when
		List<TeamResponse> result = teamRepository.projectionTeam();

		// then
		assertEquals(result.get(0).getTeamName(), "A1");
		assertEquals(result.get(0).getBudget(), 70000000);

	}

	@Test
	@DisplayName("멤버의 나이가 23이면 '23', 25이면 '25', 그 외에는 '기타'로 결과를 반환할 때, 첫 번째 결과가 25이다")
	public void basicCase() {
		// when
		List<String> result = memberRepository.basicCase();

		// then
		assertEquals(Integer.parseInt(result.get(0)), 25);
	}

	@Test
	@DisplayName("멤버의 나이가 20에서 25세 사이이면 나이에 1을 더한 값, 26에서 30세 사이이면 나이에 -1을 뺀 값, 그 외에는 나이 그대로를 반환할 때, 첫 번째 결과가 26이다")

	public void complexCase() {
		// when
		List<Integer> result = memberRepository.complexCase();

		// then
		assertEquals(result.get(0), 26);
	}





}
