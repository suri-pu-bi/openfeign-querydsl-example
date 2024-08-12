package com.lucent.querydsl_example.domain.team.repository;

import java.util.List;

import com.lucent.querydsl_example.domain.team.dto.TeamResponse;

public interface TeamQuerydslRepository {
	// 프로젝션
	List<TeamResponse> projectionTeam();
}
