package com.daypaytechnologies.digichitfund.app.pollquestionoption.services;

import com.daypaytechnologies.digichitfund.infrastructure.Response;
import com.daypaytechnologies.digichitfund.app.pollquestionoption.data.PollQuestionOptionDataValidator;
import com.daypaytechnologies.digichitfund.app.pollquestion.domain.*;
import com.daypaytechnologies.digichitfund.app.pollquestionoption.request.UpdatePollQuestionOptionRequest;
import com.daypaytechnologies.digichitfund.app.pollquestionoption.domain.PollQuestionOptionRepository;
import com.daypaytechnologies.digichitfund.app.pollquestionoption.domain.PollQuestionOptionRepositoryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PollQuestionOptionWritePlatformServiceImpl implements PollQuestionOptionWritePlatformService {

    private final PollQuestionOptionRepository pollQuestionOptionRepository;

    private final PollQuestionOptionRepositoryWrapper pollQuestionOptionRepositoryWrapper;

    private final PollQuestionOptionDataValidator pollQuestionOptionDataValidator;

    private final PollQuestionRepositoryWrapper pollQuestionRepositoryWrapper;

    @Override
    public Response updateOption(Long id, UpdatePollQuestionOptionRequest request) {
        return null;
    }
}
