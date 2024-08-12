package com.lucent.querydsl_example.domain.team.repository;

import static com.lucent.querydsl_example.domain.team.entity.QTeam.*;

import java.util.List;

import com.lucent.querydsl_example.domain.team.dto.QTeamResponse;
import com.lucent.querydsl_example.domain.team.dto.TeamResponse;
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
}
