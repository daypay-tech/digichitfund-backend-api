package com.daypaytechnologies.digichitfund.app.pollanswer.services;

import com.daypaytechnologies.digichitfund.infrastructure.Response;
import com.daypaytechnologies.digichitfund.infrastructure.ResponseList;
import com.daypaytechnologies.digichitfund.app.pollanswer.request.CreatePollAnswerRequest;

import java.util.List;

public interface PollAnswerWritePlatformService {

    Response saveAnswer(CreatePollAnswerRequest request, Long proxyRespondedId);

    ResponseList saveAnswer(Long pollId, List<CreatePollAnswerRequest> requestList, Long proxyRespondedId);
}
