package com.daypaytechnologies.digichitfund.app.scheme.domain;

import com.daypaytechnologies.digichitfund.infrastructure.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SchemeRepositoryWrapper {
    private final SchemeRepository schemeRepository;
    @Transactional(readOnly = true)
    public Scheme findOneWithNotFoundDetection(final String schemeName) {
        return this.schemeRepository.findBySchemeName(schemeName).orElseThrow(() -> new NotFoundException("Scheme  Not found " + schemeName));
    }

    @Transactional(readOnly = true)
    public Scheme findOneWithNotFoundDetection(final Long schemeId) {
        return this.schemeRepository.findBySchemeId(schemeId).orElseThrow(() -> new NotFoundException("Scheme  Not found " + schemeId));
    }
}
