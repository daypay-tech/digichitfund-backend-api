package com.daypaytechnologies.digichitfund.app.proxyresponded.domain;

import com.daypaytechnologies.digichitfund.infrastructure.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProxyRespondedRepositoryWrapper {

    private final ProxyRespondedRepository proxyRespondedRepository;

    @Transactional(readOnly = true)
    public ProxyResponded findOneWithNotFoundDetection(final Long proxyRespondedId) {
        return this.proxyRespondedRepository.findById(proxyRespondedId).orElseThrow(() -> new NotFoundException("ProxyResponded  Not found " + proxyRespondedId));
    }
}
