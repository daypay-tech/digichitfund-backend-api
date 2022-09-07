package com.daypaytechnologies.digichitfund.master.gender.domain;

import com.daypaytechnologies.digichitfund.infrastructure.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GenderRepositoryWrapper {

    private final GenderRepository genderRepository;

    @Transactional(readOnly = true)
    public Gender findOneWithNotFoundDetection(final Long id) {
        return this.genderRepository.findById(id).orElseThrow(() -> new NotFoundException("Gender Not found "+id));
    }

    public void saveAndFlush(final Gender gender){
        this.genderRepository.saveAndFlush(gender);
    }


}
