package com.daypaytechnologies.digichitfund.app.pollquestion.domain;

import com.daypaytechnologies.digichitfund.infrastructure.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PollQuestionRepositoryWrapper {

    private final PollQuestionRepository pollQuestionRepository;
    @Transactional(readOnly = true)
    public PollQuestion findOneWithNotFoundDetection(final String question) {
        return this.pollQuestionRepository.findByQuestions(question).orElseThrow(() -> new NotFoundException("PollQuestion  Not found " + question));
    }

    @Transactional(readOnly = true)
    public PollQuestion findOneWithNotFoundDetection(final Long pollQuestionId) {
        return this.pollQuestionRepository.findByPollQuestionId(pollQuestionId).orElseThrow(() -> new NotFoundException("PollQuestion  Not found " + pollQuestionId));
    }

    @Transactional(readOnly = true)
    public List<PollQuestion> findAllQuestions(final Long categoryId, final Long pollId) {
        return this.pollQuestionRepository.findAllPollQuestion(categoryId, pollId);
    }

    @Transactional(readOnly = true)
    public List<PollQuestion> findAllQuestions(final Long pollId) {
        return this.pollQuestionRepository.findAllPollQuestion(null, pollId);
    }
}
