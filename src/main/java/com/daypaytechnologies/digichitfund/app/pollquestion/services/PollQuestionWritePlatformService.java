package com.daypaytechnologies.digichitfund.app.pollquestion.services;

import com.daypaytechnologies.digichitfund.infrastructure.Response;
import com.daypaytechnologies.digichitfund.app.pollquestion.request.CreatePollQuestionBulkRequest;
import com.daypaytechnologies.digichitfund.app.pollquestion.request.CreatePollQuestionRequest;
import com.daypaytechnologies.digichitfund.app.pollquestion.request.UpdateQuestionRequest;
import com.daypaytechnologies.digichitfund.app.pollquestion.request.UpdatePollQuestionRequest;

import java.util.List;

public interface PollQuestionWritePlatformService {

    Response save(CreatePollQuestionRequest request);

    Response saveBulk(CreatePollQuestionBulkRequest request);

    Response update(Long id, UpdatePollQuestionRequest request);

    void updateBulk(Long pollId, List<UpdateQuestionRequest> request);

    void deleteQuestion(Long id);

    void deleteOption(Long questionId, Long optionId);
}
