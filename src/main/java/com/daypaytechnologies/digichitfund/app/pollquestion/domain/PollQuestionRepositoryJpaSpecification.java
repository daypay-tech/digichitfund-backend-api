package com.daypaytechnologies.digichitfund.app.pollquestion.domain;

import java.util.List;

public interface PollQuestionRepositoryJpaSpecification {

    List<PollQuestion> findAllPollQuestion(Long categoryId, Long pollId);
}
