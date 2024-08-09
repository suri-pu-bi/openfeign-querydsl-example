package com.lucent.querydsl_example.domain.manager.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.lucent.querydsl_example.domain.team.entity.Team;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Manager {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "BIGINT(11)")
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	private Team team;

	@Column(columnDefinition = "VARCHAR(20)", nullable = false)
	private String name;

	@Column(columnDefinition = "INT(10)", nullable = false)
	private int salary;

	@Builder
	public Manager(Team team, String name, int salary) {
		this.team = team;
		this.name = name;
		this.salary = salary;
	}
}
