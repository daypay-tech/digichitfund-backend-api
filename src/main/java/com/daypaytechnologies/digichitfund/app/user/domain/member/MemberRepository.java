package com.daypaytechnologies.digichitfund.app.user.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("SELECT m FROM Member m WHERE m.email=:username OR m.mobile=:username")
    Optional<Member> findByUserName(@Param("username") String username);
}
