package com.daypaytechnologies.digichitfund.app.pollquestion.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PollQuestionRepository extends JpaRepository<PollQuestion, Long>, PollQuestionRepositoryJpaSpecification, JpaSpecificationExecutor<PollQuestion> {

    @Query("select pq from PollQuestion pq where pq.id =:id ")
    Optional<PollQuestion> findByPollQuestionId (@Param("id") Long id);

    @Query("select pq from PollQuestion pq where pq.id =:id ")
    PollQuestion findByPollQuestionIdWithoutOptional(@Param("id") Long id);

    @Query("select pq from PollQuestion pq where pq.question =:question ")
    Optional<PollQuestion> findByQuestions (String question);

    @Query("SELECT COUNT(q) FROM PollQuestion q WHERE q.poll.id=:pollId")
    Long totalQuestionCount(@Param("pollId") Long pollId);
}
