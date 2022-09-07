package com.daypaytechnologies.digichitfund.master.gender.services;

import com.daypaytechnologies.digichitfund.infrastructure.Response;
import com.daypaytechnologies.digichitfund.infrastructure.exceptions.PlatformDataIntegrityException;
import com.daypaytechnologies.digichitfund.master.gender.domain.Gender;
import com.daypaytechnologies.digichitfund.master.gender.domain.GenderRepositoryWrapper;
import com.daypaytechnologies.digichitfund.master.gender.request.SaveGenderRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import javax.persistence.PersistenceException;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenderWritePlatformServiceImpl implements GenderWritePlatformService {

    private final GenderRepositoryWrapper genderRepositoryWrapper;
    @Override
    public Response saveGender(SaveGenderRequest request) {
        Gender newGender = Gender.createNewInstance(request);
        try{
            genderRepositoryWrapper.saveAndFlush(newGender);
            return new Response(newGender.getId());
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
    private void handleDataIntegrityIssues(final SaveGenderRequest request, final Throwable realCause, final Exception dve) {
        if (realCause.getMessage().contains("gender")) {
            final String gender = request.getGender();
            throw new PlatformDataIntegrityException("error.msg.duplicate.gender",
                    "Gender `" + gender + "` already exists", "gender", "gender");
        }
        logAsErrorUnexpectedDataIntegrityException(dve);
        throw new PlatformDataIntegrityException("error.msg.member.unknown.data.integrity.issue",
                "Unknown data integrity issue with resource.");
    }

    private void logAsErrorUnexpectedDataIntegrityException(final Exception dve) {
        log.error("Error occured.", dve);
    }
}
