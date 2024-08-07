package com.lucent.querydsl_example.domain.team.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucent.querydsl_example.domain.team.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long>, TeamQuerydslRepository {
}
