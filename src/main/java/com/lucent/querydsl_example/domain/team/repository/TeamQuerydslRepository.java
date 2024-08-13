package com.lucent.querydsl_example.domain.team.repository;

import java.util.List;


import com.lucent.querydsl_example.domain.team.dto.TeamResponse;
import com.lucent.querydsl_example.domain.team.entity.Team;

public interface TeamQuerydslRepository {
	// 프로젝션
	List<TeamResponse> projectionTeam();

	// fetchJoin with Where - Error
	List<Team> fetchJoinWithWhere();
}
