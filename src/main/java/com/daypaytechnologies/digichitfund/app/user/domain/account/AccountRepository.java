package com.daypaytechnologies.digichitfund.app.user.domain.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("select a from Account a where a.email =:email ")
    Optional<Account> findByEmail (String email);

    @Query("select a from Account a where a.mobile =:mobile ")
    Optional<Account> findByMobile (String mobile);
}
