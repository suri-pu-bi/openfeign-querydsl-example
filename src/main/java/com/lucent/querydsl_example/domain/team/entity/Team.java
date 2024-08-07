package com.lucent.querydsl_example.domain.team.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.lucent.querydsl_example.domain.member.entity.Member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "BIGINT(11)")
	private Long id;

	@OneToMany(mappedBy = "team")
	private List<Member> members;

	@Column(columnDefinition = "VARCHAR(20)", nullable = false)
	private String name;

	@Column(columnDefinition = "INT(10)", nullable = false)
	private int budget;

	@Builder
	public Team(String name, int budget) {
		this.name = name;
		this.budget = budget;
	}

}

