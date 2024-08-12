package com.lucent.querydsl_example.domain.team.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamResponse {
	private String teamName;
	private int budget;

	@QueryProjection
	public TeamResponse(String teamName, int budget) {
		this.teamName = teamName;
		this.budget = budget;
	}
}
