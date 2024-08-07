package com.lucent.querydsl_example.domain.member.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.lucent.querydsl_example.domain.team.entity.Team;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "BIGINT(11)")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "team_id")
	private Team team;

	@Column(columnDefinition = "VARCHAR(20)", nullable = false)
	private String name;

	@Column(columnDefinition = "INT(10)", nullable = false)
	private int age;

	@Column(columnDefinition = "INT(10)", nullable = false)
	private int salary;

	@Builder
	public Member(String name, int age, int salary) {
		this.name = name;
		this.age = age;
		this.salary = salary;
	}

	public void updateTeam(Team team) {
		this.team = team;
		team.getMembers().add(this);
	}
}
