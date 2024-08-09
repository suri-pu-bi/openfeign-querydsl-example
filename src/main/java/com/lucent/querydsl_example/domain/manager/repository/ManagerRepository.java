package com.lucent.querydsl_example.domain.manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucent.querydsl_example.domain.manager.entity.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
}
