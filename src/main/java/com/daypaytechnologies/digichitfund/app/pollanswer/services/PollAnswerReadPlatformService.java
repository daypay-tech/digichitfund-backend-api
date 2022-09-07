package com.daypaytechnologies.digichitfund.app.pollanswer.services;

import com.daypaytechnologies.digichitfund.app.pollanswer.data.PollAnswerData;

import java.util.List;

public interface PollAnswerReadPlatformService {

//    Page<PollAnswerData> fetchAll(int page, int pageSize);

    List<PollAnswerData> fetchAll();

    PollAnswerData fetchByPollAnswerId(Long pollAnswerId);

    String fetchAllMyAnsweredQuestionIdsAsStr(final Long pollId);
}
