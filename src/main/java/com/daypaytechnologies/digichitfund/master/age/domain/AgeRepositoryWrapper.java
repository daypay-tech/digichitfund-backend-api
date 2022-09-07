package com.daypaytechnologies.digichitfund.master.age.domain;

import com.daypaytechnologies.digichitfund.infrastructure.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AgeRepositoryWrapper {

    private final AgeRepository ageRepository;

    @Transactional(readOnly = true)
    public Age findOneWithNotFoundDetection(final Long id) {
        return this.ageRepository.findById(id).orElseThrow(() -> new NotFoundException("Age Not found "+id));
    }

    public void saveAndFlush(final Age age){
        this.ageRepository.saveAndFlush(age);
    }

}
