package com.daypaytechnologies.digichitfund.app.poll.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PollRepository extends JpaRepository<Poll, Long> {

    @Query("select p from Poll p where p.pollName =:pollName ")
    Optional<Poll> findByPollName (String pollName);

    @Query("select p from Poll p where p.id =:id ")
    Optional<Poll> findByPollId (@Param("id") Long id);
}
