package com.daypaytechnologies.digichitfund.app.poll.services;

import com.daypaytechnologies.digichitfund.infrastructure.Response;
import com.daypaytechnologies.digichitfund.infrastructure.exceptions.NavPulseApplicationException;
import com.daypaytechnologies.digichitfund.infrastructure.exceptions.PlatformDataIntegrityException;
import com.daypaytechnologies.digichitfund.app.category.domain.Category;
import com.daypaytechnologies.digichitfund.app.category.domain.CategoryRepositoryWrapper;
import com.daypaytechnologies.digichitfund.app.poll.data.PollDataValidator;
import com.daypaytechnologies.digichitfund.app.poll.domain.Poll;
import com.daypaytechnologies.digichitfund.app.poll.domain.PollRepository;
import com.daypaytechnologies.digichitfund.app.poll.domain.PollRepositoryWrapper;
import com.daypaytechnologies.digichitfund.app.poll.request.CreatePollRequest;
import com.daypaytechnologies.digichitfund.app.poll.request.UpdatePollRequest;
import com.daypaytechnologies.digichitfund.app.user.domain.administrationuser.AdministrationUser;
import com.daypaytechnologies.digichitfund.security.services.PlatformSecurityContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Slf4j
@RequiredArgsConstructor
public class PollWritePlatformServiceImpl implements PollWritePlatformService {

    private final PollDataValidator pollDataValidator;

    private final PollRepository pollRepository;

    private final PollRepositoryWrapper pollRepositoryWrapper;
    private final CategoryRepositoryWrapper categoryRepositoryWrapper;

    private final PlatformSecurityContext platformSecurityContext;

    @Override
    @Transactional
    public Response savePoll(CreatePollRequest createPollRequest) {
        try {
            final AdministrationUser userAccount = this.platformSecurityContext.validateAdministrationUser();
            this.pollDataValidator.validateCreatePoll(createPollRequest);
            final Category category = this.categoryRepositoryWrapper.findOneWithNotFoundDetection(createPollRequest.getCategoryId());
            final Poll poll = Poll.from(createPollRequest, category, userAccount);
            pollRepository.saveAndFlush(poll);
            return Response.of(poll.getId());
        } catch (DataIntegrityViolationException e) {
            throw new PlatformDataIntegrityException("error.duplicate.data", String.format("Poll %s already exist", createPollRequest.getPollName()));
        } catch (Exception e) {
            throw new NavPulseApplicationException(e.getMessage());
        }
    }


    @Override
    @Transactional
    public Response updatePoll(Long id, UpdatePollRequest request) {
        final Poll oldPoll = pollRepositoryWrapper.findOneWithNotFoundDetection(id);
        oldPoll.setPollName(request.getPollName());
        final AdministrationUser userAccount = this.platformSecurityContext.validateAdministrationUser();
        oldPoll.setUpdatedBy(userAccount);
        final Category category = this.categoryRepositoryWrapper.findOneWithNotFoundDetection(request.getCategoryId());
        oldPoll.setCategory(category);
        this.pollRepository.saveAndFlush(oldPoll);
        return Response.of(oldPoll.getId());
    }
    @Override
    public Poll delete(Long id) {
        final Poll poll = this.pollRepositoryWrapper.findOneWithNotFoundDetection(id);
        poll.setIsDeleted(true);
        poll.setDeletedOn(LocalDate.now());
        poll.setDeletedBy(1);
        this.pollRepository.saveAndFlush(poll);
        return poll;
    }
}
