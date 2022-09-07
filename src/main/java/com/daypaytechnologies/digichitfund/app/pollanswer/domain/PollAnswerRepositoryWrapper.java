package com.daypaytechnologies.digichitfund.app.pollanswer.domain;

import com.daypaytechnologies.digichitfund.infrastructure.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PollAnswerRepositoryWrapper {

    private final PollAnswerRepository pollAnswerRepository;

    @Transactional(readOnly = true)
    public PollAnswer findOneWithNotFoundDetection(final Long pollAnswerId) {
        return this.pollAnswerRepository.findById(pollAnswerId).orElseThrow(() -> new NotFoundException("PollAnswer  Not found " + pollAnswerId));
    }
}
