package com.lucent.querydsl_example.domain.team.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
class TeamRepositoryTest {

	@Autowired
	private TeamRepository teamRepository;

	@BeforeEach
	void setUp() {
	}
}
