package com.daypaytechnologies.digichitfund.app.useraccount.domain.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("SELECT r from Role r where r.code=:roleCode")
    Optional<Role> findByRoleCode(@Param("roleCode") String roleCode);
}
