package com.daypaytechnologies.digichitfund.app.user.domain.account;

import com.daypaytechnologies.digichitfund.infrastructure.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountRepositoryWrapper {

    private final AccountRepository accountRepository;

    @Transactional(readOnly = true)
    public Account findOneWithNotFoundDetection(final String email) {
        return this.accountRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Email  Not found " + email));
    }

    @Transactional(readOnly = true)
    public Account findOneWithNotFoundDetectionByMobile(final String mobile) {
        return this.accountRepository.findByMobile(mobile).orElseThrow(() -> new NotFoundException("Mobile  Not found " + mobile));
    }
}
