package com.lucent.querydsl_example.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucent.querydsl_example.domain.member.entity.Member;


public interface MemberRepository extends JpaRepository<Member, Long>, MemberQuerydslRepository {
}
