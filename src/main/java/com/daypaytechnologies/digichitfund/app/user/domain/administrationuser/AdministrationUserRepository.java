package com.daypaytechnologies.digichitfund.app.user.domain.administrationuser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdministrationUserRepository extends JpaRepository<AdministrationUser, Long> {
    @Query("SELECT a FROM AdministrationUser a WHERE a.account.email=:email")
    AdministrationUser findByEmail(@Param("email") String email);




}
