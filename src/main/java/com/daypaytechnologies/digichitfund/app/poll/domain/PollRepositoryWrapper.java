package com.daypaytechnologies.digichitfund.app.poll.domain;

import com.daypaytechnologies.digichitfund.infrastructure.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PollRepositoryWrapper {

    private final PollRepository pollRepository;

    @Transactional(readOnly = true)
    public Poll findOneWithNotFoundDetection(final String pollName) {
        return this.pollRepository.findByPollName(pollName).orElseThrow(() -> new NotFoundException("Poll  Not found " + pollName));
    }

    @Transactional(readOnly = true)
    public Poll findOneWithNotFoundDetection(final Long pollId) {
        return this.pollRepository.findByPollId(pollId).orElseThrow(() -> new NotFoundException("Poll  Not found " + pollId));
    }
}
