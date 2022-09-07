package com.daypaytechnologies.digichitfund.app.pollquestionoption.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PollQuestionOptionRepository extends JpaRepository<PollQuestionOption, Long> {

    @Query("select po from PollQuestionOption po where po.id =:id ")
    Optional<PollQuestionOption> findByPollQuestionOptionId(@Param("id") Long id);
}
