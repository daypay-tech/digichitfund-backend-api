package com.daypaytechnologies.digichitfund.app.pollanswer.services;

import com.daypaytechnologies.digichitfund.infrastructure.Response;
import com.daypaytechnologies.digichitfund.infrastructure.ResponseList;
import com.daypaytechnologies.digichitfund.infrastructure.exceptions.MismatchQuestionAnswerException;
import com.daypaytechnologies.digichitfund.infrastructure.exceptions.NavPulseApplicationException;
import com.daypaytechnologies.digichitfund.app.pollanswer.data.PollAnswerDataValidator;
import com.daypaytechnologies.digichitfund.app.pollanswer.domain.PollAnswer;
import com.daypaytechnologies.digichitfund.app.pollanswer.domain.PollAnswerRepository;
import com.daypaytechnologies.digichitfund.app.pollanswer.request.CreatePollAnswerRequest;
import com.daypaytechnologies.digichitfund.app.pollquestion.domain.PollQuestion;
import com.daypaytechnologies.digichitfund.app.pollquestion.domain.PollQuestionRepositoryWrapper;
import com.daypaytechnologies.digichitfund.app.pollquestionoption.domain.PollQuestionOption;
import com.daypaytechnologies.digichitfund.app.pollquestionoption.domain.PollQuestionOptionRepositoryWrapper;
import com.daypaytechnologies.digichitfund.app.proxyresponded.domain.ProxyResponded;
import com.daypaytechnologies.digichitfund.app.proxyresponded.domain.ProxyRespondedRepositoryWrapper;
import com.daypaytechnologies.digichitfund.app.user.domain.member.Member;
import com.daypaytechnologies.digichitfund.security.services.PlatformSecurityContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PollAnswerWritePlatformServiceImpl implements PollAnswerWritePlatformService {
    private final PollAnswerRepository pollAnswerRepository;

    private final PollAnswerDataValidator pollAnswerDataValidator;

    private final PollQuestionRepositoryWrapper pollQuestionRepositoryWrapper;

    private final PollQuestionOptionRepositoryWrapper pollQuestionOptionRepositoryWrapper;

    private final ProxyRespondedRepositoryWrapper proxyRespondedRepositoryWrapper;

    private final PlatformSecurityContext platformSecurityContext;

    @Override
    @Transactional
    public Response saveAnswer(CreatePollAnswerRequest createPollAnswerRequest, Long proxyRespondedId) {
        try {
            final Member member = this.platformSecurityContext.validateMember();
            this.pollAnswerDataValidator.validateCreatePollAnswer(createPollAnswerRequest);
            ProxyResponded proxyResponded = null;
            if(proxyRespondedId != null) {
                proxyResponded = this.proxyRespondedRepositoryWrapper.findOneWithNotFoundDetection(proxyRespondedId);
            }
            final PollQuestion question = this.pollQuestionRepositoryWrapper.findOneWithNotFoundDetection(createPollAnswerRequest.getQuestionId());
            final PollQuestionOption option = this.pollQuestionOptionRepositoryWrapper.findOneWithNotFoundDetection(createPollAnswerRequest.getOptionId());
            final PollAnswer pollAnswer;
            if(proxyResponded == null) {
                this.validateAlreadyAnswered(question, member);
                pollAnswer = PollAnswer.from(question, option, member);
            } else {
                pollAnswer = PollAnswer.from(question, option);
            }
            pollAnswer.setCreatedAt(LocalDateTime.now());
            pollAnswer.setUpdatedAt(LocalDateTime.now());
            pollAnswer.setCreatedBy(member);
            pollAnswer.setUpdatedBy(member);
            pollAnswerRepository.saveAndFlush(pollAnswer);
            return Response.of(pollAnswer.getId());
        } catch (Exception e) {
            throw new NavPulseApplicationException(e.getMessage());
        }
    }

    public void validateAlreadyAnswered(final PollQuestion question, final Member member) {
        final Optional<PollAnswer> pollAnswerOptional = this.pollAnswerRepository.findByQuestionIdAndUserId(question.getId(), member.getId());
        if(pollAnswerOptional.isPresent()) {
            log.debug("Member {} Already answered this question {}", member.getEmail(), question.getQuestion());
            throw new NavPulseApplicationException(String.format("You have already answered this question %s", question.getQuestion()));
        }
    }

    @Override
    @Transactional
    public ResponseList saveAnswer(Long pollId, List<CreatePollAnswerRequest> requestList, Long proxyRespondedId) {
        try {
            final List<PollQuestion> pollQuestions = pollQuestionRepositoryWrapper.findAllQuestions(pollId);
            if(pollQuestions.size() != requestList.size()) {
                throw new MismatchQuestionAnswerException("Answer all the questions");
            }
            final Member member = platformSecurityContext.validateMember();
            final List<PollAnswer> pollAnswers = new ArrayList<>();
            final ResponseList responseList = new ResponseList();
            ProxyResponded proxyResponded = null;
            if(proxyRespondedId != null) {
                proxyResponded = this.proxyRespondedRepositoryWrapper.findOneWithNotFoundDetection(proxyRespondedId);
            }
            for(CreatePollAnswerRequest createPollAnswerRequest: requestList) {
                this.pollAnswerDataValidator.validateCreatePollAnswer(createPollAnswerRequest);
                final PollQuestion question = this.pollQuestionRepositoryWrapper.findOneWithNotFoundDetection(createPollAnswerRequest.getQuestionId());
                final PollAnswer pollAnswer;
                final PollQuestionOption option = this.pollQuestionOptionRepositoryWrapper.findOneWithNotFoundDetection(createPollAnswerRequest.getOptionId());
                if(proxyResponded == null) {
                    this.validateAlreadyAnswered(question, member);
                    pollAnswer = PollAnswer.from(question, option, member);
                } else {
                    pollAnswer = PollAnswer.from(question, option);
                }
                pollAnswer.setProxyResponded(proxyResponded);
                pollAnswer.setCreatedAt(LocalDateTime.now());
                pollAnswer.setUpdatedAt(LocalDateTime.now());
                pollAnswer.setCreatedBy(member);
                pollAnswer.setUpdatedBy(member);
                pollAnswers.add(pollAnswer);
            }
            if(!pollAnswers.isEmpty()) {
                pollAnswerRepository.saveAllAndFlush(pollAnswers);
            }
            return responseList;
        } catch (Exception e) {
            throw new NavPulseApplicationException(e.getMessage());
        }
    }
}
