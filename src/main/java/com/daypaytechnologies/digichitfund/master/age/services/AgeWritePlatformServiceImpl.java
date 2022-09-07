package com.daypaytechnologies.digichitfund.master.age.services;

import com.daypaytechnologies.digichitfund.infrastructure.Response;
import com.daypaytechnologies.digichitfund.infrastructure.exceptions.PlatformDataIntegrityException;
import com.daypaytechnologies.digichitfund.master.age.domain.Age;
import com.daypaytechnologies.digichitfund.master.age.domain.AgeRepositoryWrapper;
import com.daypaytechnologies.digichitfund.master.age.request.CreateAgeRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class AgeWritePlatformServiceImpl implements AgeWritePlatformService {

    private final AgeRepositoryWrapper ageRepositoryWrapper;

    @Override
    @Transactional
    public Response saveAge(CreateAgeRequest request) {
        Age newAge = Age.createNewInstance(request);
        try{
            ageRepositoryWrapper.saveAndFlush(newAge);
            return new Response(newAge.getId());
        }catch (final JpaSystemException | DataIntegrityViolationException | TransactionSystemException e) {
            handleDataIntegrityIssues(request, e.getMostSpecificCause(), e);
            throw new PlatformDataIntegrityException("error.msg.member.unknown.data.integrity.issue",
                    "Unknown data integrity issue with resource.");
        } catch (final PersistenceException e) {
            Throwable throwable = ExceptionUtils.getRootCause(e.getCause());
            handleDataIntegrityIssues(request, throwable, e);
            throw new PlatformDataIntegrityException("error.msg.member.unknown.data.integrity.issue",
                    "Unknown data integrity issue with resource.");
        }
    }

    @Override
    @Transactional
    public Response updateAge(Long id, CreateAgeRequest request) {
        final Age oldAgeRange = this.ageRepositoryWrapper.findOneWithNotFoundDetection(id);
        try{
            oldAgeRange.updateAgeRange(request);
            oldAgeRange.setUpdatedAt(LocalDateTime.now());
            ageRepositoryWrapper.saveAndFlush(oldAgeRange);
            return new Response(oldAgeRange.getId());
        }catch (final JpaSystemException | DataIntegrityViolationException | TransactionSystemException e) {
            handleDataIntegrityIssues(request, e.getMostSpecificCause(), e);
            throw new PlatformDataIntegrityException("error.msg.member.unknown.data.integrity.issue",
                    "Unknown data integrity issue with resource.");
        } catch (final PersistenceException e) {
            Throwable throwable = ExceptionUtils.getRootCause(e.getCause());
            handleDataIntegrityIssues(request, throwable, e);
            throw new PlatformDataIntegrityException("error.msg.member.unknown.data.integrity.issue",
                    "Unknown data integrity issue with resource.");
        }
    }

    private void handleDataIntegrityIssues(final CreateAgeRequest request, final Throwable realCause, final Exception dve) {
        if (realCause.getMessage().contains("range")) {
            final String range = request.getRange();
            throw new PlatformDataIntegrityException("error.msg.duplicate.range",
                    "Range `" + range + "` already exists", "range", "range");
        }
        logAsErrorUnexpectedDataIntegrityException(dve);
        throw new PlatformDataIntegrityException("error.msg.member.unknown.data.integrity.issue",
                "Unknown data integrity issue with resource.");
    }

    private void logAsErrorUnexpectedDataIntegrityException(final Exception dve) {
        log.error("Error occured.", dve);
    }
}
