package com.lucent.querydsl_example.domain.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberResponse {
	private String teamName;
	private String memberName;
	private int memberAge;

	@Builder
	public MemberResponse(String teamName, String memberName, int memberAge) {
		this.teamName = teamName;
		this.memberName = memberName;
		this.memberAge = memberAge;
	}
}
