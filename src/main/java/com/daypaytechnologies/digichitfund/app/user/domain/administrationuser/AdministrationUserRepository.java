package com.daypaytechnologies.digichitfund.app.user.domain.administrationuser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdministrationUserRepository extends JpaRepository<AdministrationUser, Long> {

    @Query("SELECT a FROM AdministrationUser a WHERE (a.account.email=:username OR a.account.mobile=:username) AND a.organization.id=:orgId")
    AdministrationUser findByEmailOrMobile(@Param("username") String username, @Param("orgId") Long orgId);

    @Query("SELECT a FROM AdministrationUser a WHERE a.organization.id=:orgId")
    AdministrationUser findByOrg(@Param("orgId") Long orgId);

    @Query("SELECT a FROM AdministrationUser a WHERE a.account.email=:email")
    AdministrationUser findByEmail(@Param("email") String email);

    @Query("SELECT a FROM AdministrationUser a WHERE (a.account.email=:username OR a.account.mobile=:username) AND a.organization.id=:orgId")
    AdministrationUser findByUsername(@Param("username") String username, @Param("orgId") Long orgId);

    @Query("SELECT a FROM AdministrationUser a WHERE a.account.email=:username AND a.organization.id IS NULL")
    AdministrationUser findByUsernameForSuperAdmin(@Param("username") String username);
}
