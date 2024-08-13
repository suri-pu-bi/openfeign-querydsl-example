package com.lucent.querydsl_example.domain.team.repository;

import static com.lucent.querydsl_example.domain.member.entity.QMember.*;
import static com.lucent.querydsl_example.domain.team.entity.QTeam.*;

import java.util.List;

import com.lucent.querydsl_example.domain.team.dto.QTeamResponse;
import com.lucent.querydsl_example.domain.team.dto.TeamResponse;
import com.lucent.querydsl_example.domain.team.entity.Team;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TeamQuerydslRepositoryImpl implements TeamQuerydslRepository {
	private final JPAQueryFactory queryFactory;

	@Override
	public List<TeamResponse> projectionTeam() {
		return queryFactory.select(
				new QTeamResponse(
					team.name.as("teamName"), team.budget))
			.from(team)
			.fetch();
	}

	@Override
	public List<Team> fetchJoinWithWhere() {
		return queryFactory
			.selectFrom(team)
			.join(team.members, member).fetchJoin()
			.where(member.age.goe(23))
			.fetch();
	}
}
