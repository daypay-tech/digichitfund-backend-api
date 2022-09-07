package com.daypaytechnologies.digichitfund.app.pollquestionoption.services;

import com.daypaytechnologies.digichitfund.infrastructure.Response;
import com.daypaytechnologies.digichitfund.app.pollquestionoption.request.UpdatePollQuestionOptionRequest;

public interface PollQuestionOptionWritePlatformService {

    Response updateOption(Long id, UpdatePollQuestionOptionRequest request);
}
