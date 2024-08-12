package com.lucent.querydsl_example.domain.member.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TeamMemberCount {
	private String teamName;
	private Long memberCount;

	public TeamMemberCount(String teamName, Long memberCount) {
		this.teamName = teamName;
		this.memberCount = memberCount;
	}
}
