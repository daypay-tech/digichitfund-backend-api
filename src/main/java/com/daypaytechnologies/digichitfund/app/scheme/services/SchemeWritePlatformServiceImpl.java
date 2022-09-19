package com.daypaytechnologies.digichitfund.app.scheme.services;

import com.daypaytechnologies.digichitfund.app.scheme.data.SchemeDataValidator;
import com.daypaytechnologies.digichitfund.app.scheme.domain.Scheme;
import com.daypaytechnologies.digichitfund.app.scheme.domain.SchemeRepository;
import com.daypaytechnologies.digichitfund.app.scheme.domain.SchemeRepositoryWrapper;
import com.daypaytechnologies.digichitfund.app.scheme.request.CreateSchemeRequest;
import com.daypaytechnologies.digichitfund.app.scheme.request.UpdateSchemeRequest;
import com.daypaytechnologies.digichitfund.infrastructure.Response;
import com.daypaytechnologies.digichitfund.infrastructure.exceptions.PlatformDataIntegrityException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Slf4j
@RequiredArgsConstructor
public class SchemeWritePlatformServiceImpl implements SchemeWritePlatformService {

    private final SchemeRepository schemeRepository;

    private final SchemeRepositoryWrapper schemeRepositoryWrapper;

    private final SchemeDataValidator schemeDataValidator;

    @Override
    @Transactional
    public Response save(CreateSchemeRequest createSchemeRequest) {
        try {
            this.schemeDataValidator.validateCreateScheme(createSchemeRequest);
            final Scheme scheme = Scheme.from(createSchemeRequest, createSchemeRequest.getCalendar());
            schemeRepository.saveAndFlush(scheme);
            return Response.of(scheme.getId());
        } catch (DataIntegrityViolationException e) {
            throw new PlatformDataIntegrityException("error.duplicate.data", String.format("Scheme %s already exist", createSchemeRequest.getSchemeName()));
        } catch (Exception e) {
            throw new ApplicationContextException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Response update(Long id, UpdateSchemeRequest request) {
        final Scheme oldScheme = schemeRepositoryWrapper.findOneWithNotFoundDetection(id);
        oldScheme.setSchemeName(request.getSchemeName());
        oldScheme.setTotalAmount(request.getTotalAmount());
        oldScheme.setTotalMembers(request.getTotalMembers());
        oldScheme.setCalendar(request.getCalendar());
        oldScheme.setStartDate(request.getStartDate());
        this.schemeRepository.saveAndFlush(oldScheme);
        return Response.of(oldScheme.getId());
    }

    @Override
    @Transactional
    public Scheme delete(Long id) {
        final Scheme scheme = this.schemeRepositoryWrapper.findOneWithNotFoundDetection(id);
        scheme.setIsDeleted(true);
        scheme.setDeletedOn(LocalDate.now());
        scheme.setDeletedBy(1);
        this.schemeRepository.saveAndFlush(scheme);
        return scheme;


    }
}
