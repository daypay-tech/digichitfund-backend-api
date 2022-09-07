package com.daypaytechnologies.digichitfund.app.pollquestionoption.domain;

import com.daypaytechnologies.digichitfund.infrastructure.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@RequiredArgsConstructor
public class PollQuestionOptionRepositoryWrapper {

    private final PollQuestionOptionRepository pollQuestionOptionRepository;

    @Transactional(readOnly = true)
    public PollQuestionOption findOneWithNotFoundDetection(final Long optionId) {
        return this.pollQuestionOptionRepository.findByPollQuestionOptionId(optionId).orElseThrow(() -> new NotFoundException("PollQuestionOption  Not found " + optionId));
    }
}
