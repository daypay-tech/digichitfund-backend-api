package com.daypaytechnologies.digichitfund.app.pollquestion.services;

import com.daypaytechnologies.digichitfund.infrastructure.pagination.Page;
import com.daypaytechnologies.digichitfund.app.pollquestion.data.PollQuestionData;
import com.daypaytechnologies.digichitfund.app.pollquestion.data.PollQuestionDetailsData;

import java.util.List;

public interface PollQuestionReadPlatformService {

    List<PollQuestionData> fetchAll(Long pollId);

    Page<PollQuestionData> fetchAll(Long categoryId, Long pollId, int page, int pageSize);

    PollQuestionData fetchByPollQuestionId(Long pollQuestionId);

    PollQuestionData fetchPreviousQuestion(Long pollId, Long questionId, Boolean answerForOtherProxyUser);

    PollQuestionData fetchNextQuestion(Long pollId, Long questionId, Boolean answerForOtherProxyUser);

    List<PollQuestionData> fetchAllNonAnsweredQuestions(Long pollId);

    Long totalQuestion(Long pollId);

    PollQuestionDetailsData fetchQuestionDetails(Long pollId);
}
