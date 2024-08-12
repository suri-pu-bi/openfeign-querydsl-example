package com.lucent.querydsl_example.domain.team.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class TeamResponse {
	private String teamName;
	private int budget;

	@Builder
	public TeamResponse(String teamName, int budget) {
		this.teamName = teamName;
		this.budget = budget;
	}
}
