package com.daypaytechnologies.digichitfund.app.scheme.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SchemeRepository extends JpaRepository<Scheme, Long> {
    @Query("select sc from Scheme sc where sc.schemeName =:schemeName ")
    Optional<Scheme> findBySchemeName (String schemeName);

    @Query("select sc from Scheme sc where sc.id =:id ")
    Optional<Scheme> findBySchemeId (@Param("id") Long id);
}
