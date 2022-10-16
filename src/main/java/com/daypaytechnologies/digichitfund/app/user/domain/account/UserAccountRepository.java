package com.daypaytechnologies.digichitfund.app.user.domain.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    @Query("select a from UserAccount a where a.email =:email")
    Optional<UserAccount> findByEmail(String email);

    @Query("select a from UserAccount a where a.mobile =:mobile")
    Optional<UserAccount> findByMobile(String mobile);

    @Query("select a from UserAccount a where a.email =:email OR a.mobile =:mobile")
    Optional<UserAccount> findByEmailOrMobile(@Param("email") String email, @Param("mobile") String mobile);

    @Query("select a from UserAccount a where a.email =:userName OR a.mobile =:userName ")
    UserAccount findByUsername(@Param("userName") String userName);
}
