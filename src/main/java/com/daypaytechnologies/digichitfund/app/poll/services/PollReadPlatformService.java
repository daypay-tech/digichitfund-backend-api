package com.daypaytechnologies.digichitfund.app.poll.services;

import com.daypaytechnologies.digichitfund.infrastructure.pagination.Page;
import com.daypaytechnologies.digichitfund.app.poll.data.PollData;
import com.daypaytechnologies.digichitfund.app.poll.data.PollQuestionAggregationData;

import java.util.List;

public interface PollReadPlatformService {

    Page<PollData> fetchAll(Long categoryId, int page, int pageSize);

    List<PollData> fetchAll(Long categoryId);

    PollData fetchByPollId(Long pollId);

    Page<PollQuestionAggregationData> fetchAllPollQuestionAggregation(Long categoryId, Long pollId, int page, int pageSize);
}
