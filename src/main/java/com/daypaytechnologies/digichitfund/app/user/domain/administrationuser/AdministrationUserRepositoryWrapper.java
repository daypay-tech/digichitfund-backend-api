package com.daypaytechnologies.digichitfund.app.user.domain.administrationuser;

import com.daypaytechnologies.digichitfund.infrastructure.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdministrationUserRepositoryWrapper {

    private final AdministrationUserRepository administrationUserRepository;

    @Transactional(readOnly = true)
    public AdministrationUser findOneWithNotFoundDetection(final Long administrationUserId) {
        return this.administrationUserRepository.findById(administrationUserId).orElseThrow(() -> new NotFoundException("Member  Not found " + administrationUserId));
    }
}
