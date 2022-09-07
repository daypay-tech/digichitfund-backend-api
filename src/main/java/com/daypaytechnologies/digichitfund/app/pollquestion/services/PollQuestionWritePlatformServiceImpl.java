package com.daypaytechnologies.digichitfund.app.pollquestion.services;

import com.daypaytechnologies.digichitfund.infrastructure.Response;
import com.daypaytechnologies.digichitfund.infrastructure.exceptions.NavPulseApplicationException;
import com.daypaytechnologies.digichitfund.infrastructure.exceptions.PlatformApiDataValidationException;
import com.daypaytechnologies.digichitfund.app.poll.domain.Poll;
import com.daypaytechnologies.digichitfund.app.poll.domain.PollRepositoryWrapper;
import com.daypaytechnologies.digichitfund.app.pollquestion.data.PollQuestionDataValidator;
import com.daypaytechnologies.digichitfund.app.pollquestion.domain.PollQuestion;
import com.daypaytechnologies.digichitfund.app.pollquestion.domain.PollQuestionRepository;
import com.daypaytechnologies.digichitfund.app.pollquestion.domain.PollQuestionRepositoryWrapper;
import com.daypaytechnologies.digichitfund.app.pollquestion.request.CreatePollQuestionBulkRequest;
import com.daypaytechnologies.digichitfund.app.pollquestion.request.CreatePollQuestionRequest;
import com.daypaytechnologies.digichitfund.app.pollquestion.request.UpdateQuestionOptionRequest;
import com.daypaytechnologies.digichitfund.app.pollquestion.request.UpdateQuestionRequest;
import com.daypaytechnologies.digichitfund.app.pollquestion.request.UpdatePollQuestionRequest;
import com.daypaytechnologies.digichitfund.app.pollquestionoption.domain.PollQuestionOption;
import com.daypaytechnologies.digichitfund.app.pollquestionoption.domain.PollQuestionOptionRepository;
import com.daypaytechnologies.digichitfund.app.pollquestionoption.domain.PollQuestionOptionRepositoryWrapper;
import com.daypaytechnologies.digichitfund.app.user.domain.administrationuser.AdministrationUser;
import com.daypaytechnologies.digichitfund.security.services.PlatformSecurityContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PollQuestionWritePlatformServiceImpl implements PollQuestionWritePlatformService {

    private final PollQuestionRepository pollQuestionRepository;

    private final PollQuestionRepositoryWrapper pollQuestionRepositoryWrapper;

    private final PollQuestionDataValidator pollQuestionDataValidator;

    private final PollRepositoryWrapper pollRepositoryWrapper;

    private final PlatformSecurityContext platformSecurityContext;

    private final PollQuestionOptionRepositoryWrapper pollQuestionOptionRepositoryWrapper;

    private final PollQuestionOptionRepository pollQuestionOptionRepository;

    @Override
    @Transactional
    public Response save(CreatePollQuestionRequest createPollQuestionRequest) {
        try {
            final AdministrationUser administrationUser = this.platformSecurityContext.validateAdministrationUser();
            this.pollQuestionDataValidator.validateCreatePollQuestion(createPollQuestionRequest);
            final Poll poll = this.pollRepositoryWrapper.findOneWithNotFoundDetection(createPollQuestionRequest.getPollId());
            final PollQuestion pollQuestion = PollQuestion.from(createPollQuestionRequest.getQuestion(), poll, administrationUser);
            pollQuestionRepository.save(pollQuestion);
            return Response.of(poll.getId());
        } catch (PlatformApiDataValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new NavPulseApplicationException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Response saveBulk(CreatePollQuestionBulkRequest createPollQuestionRequest) {
        try {
            final AdministrationUser administrationUser = this.platformSecurityContext.validateAdministrationUser();
            this.pollQuestionDataValidator.validateCreateBulkPollQuestion(createPollQuestionRequest);
            final Poll poll = this.pollRepositoryWrapper.findOneWithNotFoundDetection(createPollQuestionRequest.getPollId());
            final List<PollQuestion> pollQuestions = PollQuestion.from(createPollQuestionRequest.getQuestions(), poll, administrationUser);
            pollQuestionRepository.saveAll(pollQuestions);
            return Response.of(poll.getId());
        } catch (PlatformApiDataValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new NavPulseApplicationException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Response update(Long id, UpdatePollQuestionRequest request) {
        final AdministrationUser administrationUser = this.platformSecurityContext.validateAdministrationUser();
        final PollQuestion oldPollQuestion = pollQuestionRepositoryWrapper.findOneWithNotFoundDetection(id);
        oldPollQuestion.setQuestion(request.getQuestion());
        oldPollQuestion.setUpdatedBy(administrationUser);
        oldPollQuestion.setUpdatedAt(LocalDateTime.now());
        this.pollQuestionRepository.saveAndFlush(oldPollQuestion);
        return Response.of(oldPollQuestion.getId());
    }

    @Override
    @Transactional
    public void updateBulk(Long pollId, List<UpdateQuestionRequest> request) {
        final AdministrationUser administrationUser = this.platformSecurityContext.forceValidateAdministrationUser();
        final List<PollQuestion> questions = new ArrayList<>();
        final Poll poll = this.pollRepositoryWrapper.findOneWithNotFoundDetection(pollId);
        for(UpdateQuestionRequest updateQuestionRequest : request) {
            final PollQuestion pollQuestion;
            if(updateQuestionRequest.getId() == null) {
                pollQuestion = PollQuestion.from(updateQuestionRequest, poll, administrationUser);
            } else {
                pollQuestion = this.pollQuestionRepositoryWrapper.findOneWithNotFoundDetection(updateQuestionRequest.getId());
                pollQuestion.setUpdatedAt(LocalDateTime.now());
                pollQuestion.setUpdatedBy(administrationUser);
                if(pollId.longValue() != pollQuestion.getPoll().getId().longValue()) {
                    pollQuestion.setPoll(poll);
                }
                if(!pollQuestion.getQuestion().equals(updateQuestionRequest.getQuestion())) {
                    pollQuestion.setQuestion(updateQuestionRequest.getQuestion());
                }
                final List<PollQuestionOption> options = new ArrayList<>();
                for(UpdateQuestionOptionRequest optionRequest: updateQuestionRequest.getOptions()) {
                    PollQuestionOption option;
                    if(optionRequest.getId() == null) {
                        option = PollQuestionOption.from(optionRequest);
                    } else {
                        option = this.pollQuestionOptionRepositoryWrapper.findOneWithNotFoundDetection(optionRequest.getId());
                        if(!option.getOptionName().equals(optionRequest.getOption())) {
                            option.setOptionName(optionRequest.getOption());
                        }
                    }
                    options.add(option);
                }
                pollQuestion.setOptions(options);
            }
            questions.add(pollQuestion);
        }
        this.pollQuestionRepository.saveAllAndFlush(questions);
    }

    @Override
    @Transactional
    public void deleteQuestion(Long id) {
        final PollQuestion pollQuestion = this.pollQuestionRepositoryWrapper.findOneWithNotFoundDetection(id);
        pollQuestion.setOptions(new ArrayList<>());
        this.pollQuestionRepository.delete(pollQuestion);
    }

    @Override
    @Transactional
    public void deleteOption(Long questionId, Long optionId) {
        final PollQuestion pollQuestion = this.pollQuestionRepositoryWrapper.findOneWithNotFoundDetection(questionId);
        pollQuestion.getOptions().removeIf(pollQuestionOption -> pollQuestionOption.getId().longValue() == optionId.longValue());
        this.pollQuestionRepository.saveAndFlush(pollQuestion);
    }
}
