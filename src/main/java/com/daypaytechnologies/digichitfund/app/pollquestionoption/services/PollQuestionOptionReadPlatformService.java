package com.daypaytechnologies.digichitfund.app.pollquestionoption.services;

import com.daypaytechnologies.digichitfund.infrastructure.pagination.Page;
import com.daypaytechnologies.digichitfund.app.pollquestionoption.data.PollQuestionOptionData;

import java.util.List;

public interface PollQuestionOptionReadPlatformService {

    List<PollQuestionOptionData> fetchAll(Long pollQuestionId);

    Page<PollQuestionOptionData> fetchAll(int pollQuestionId, int page, int pageSize);

    PollQuestionOptionData fetchByPollQuestionOptionId(Long pollQuestionOptionId);
}
