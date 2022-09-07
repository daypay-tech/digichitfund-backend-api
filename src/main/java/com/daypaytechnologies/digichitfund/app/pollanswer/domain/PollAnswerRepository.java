package com.daypaytechnologies.digichitfund.app.pollanswer.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PollAnswerRepository extends JpaRepository<PollAnswer, Long> {

    @Query("select pa from PollAnswer pa where pa.id =:id ")
    Optional<PollAnswer> findByPollAnswerId(@Param("id") Long id);

    @Query("select pa from PollAnswer pa where pa.question.id =:id AND pa.answeredBy.id=:memberId")
    Optional<PollAnswer> findByQuestionIdAndUserId(@Param("id") Long id, @Param("memberId") Long memberId);

    @Query("select pa from PollAnswer pa where pa.question.poll.id=:pollId AND pa.answeredBy.id=:memberId GROUP BY pa.id, pa.question.id")
    List<PollAnswer> findAllMyAnsweredQuestion(@Param("pollId") Long pollId, @Param("memberId") Long memberId);
}
