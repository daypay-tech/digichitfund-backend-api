package com.daypaytechnologies.digichitfund.app.useraccount.domain.account;

import com.daypaytechnologies.digichitfund.infrastructure.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserAccountRepositoryWrapper {

    private final UserAccountRepository userAccountRepository;

    @Transactional(readOnly = true)
    public UserAccount findOneWithNotFoundDetection(final String email) {
        return this.userAccountRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Email  Not found " + email));
    }

    @Transactional(readOnly = true)
    public UserAccount findOneWithNotFoundDetection(final Long accountId) {
        return this.userAccountRepository.findById(accountId).orElseThrow(() -> new NotFoundException("Account Not found " + accountId));
    }

    @Transactional(readOnly = true)
    public UserAccount findOneWithNotFoundDetectionByMobile(final String mobile) {
        return this.userAccountRepository.findByMobile(mobile).orElseThrow(() -> new NotFoundException("Mobile  Not found " + mobile));
    }

    @Transactional(readOnly = true)
    public UserAccount findOneWithNotFoundDetection(final String email, final String mobile) {
        return this.userAccountRepository.findByEmailOrMobile(email, mobile).orElse(null);
    }
}
