package com.daypaytechnologies.digichitfund.app.proxyresponded.services;

import com.daypaytechnologies.digichitfund.infrastructure.Response;
import com.daypaytechnologies.digichitfund.master.age.domain.Age;
import com.daypaytechnologies.digichitfund.master.age.domain.AgeRepositoryWrapper;
import com.daypaytechnologies.digichitfund.master.gender.domain.Gender;
import com.daypaytechnologies.digichitfund.master.gender.domain.GenderRepositoryWrapper;
import com.daypaytechnologies.digichitfund.app.proxyresponded.domain.ProxyResponded;
import com.daypaytechnologies.digichitfund.app.proxyresponded.domain.ProxyRespondedRepository;
import com.daypaytechnologies.digichitfund.app.proxyresponded.request.ProxyRespondedRequest;
import com.daypaytechnologies.digichitfund.app.user.domain.member.Member;
import com.daypaytechnologies.digichitfund.security.services.PlatformSecurityContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProxyRespondedWritePlatformServiceImpl implements ProxyRespondedWritePlatformService{

    private final PlatformSecurityContext platformSecurityContext;

    private final GenderRepositoryWrapper genderRepositoryWrapper;

    private final AgeRepositoryWrapper ageRepositoryWrapper;

    private final ProxyRespondedRepository proxyRespondedRepository;


    @Override
    @Transactional
    public Response saveProxyResponded(ProxyRespondedRequest request) {
        final Member member = this.platformSecurityContext.validateMember();
        final Gender gender = this.genderRepositoryWrapper.findOneWithNotFoundDetection(request.getGenderId());
        final Age age = this.ageRepositoryWrapper.findOneWithNotFoundDetection(request.getAgeId());
        final ProxyResponded newProxyResponded = ProxyResponded.from(member, age, gender);
        this.proxyRespondedRepository.saveAndFlush(newProxyResponded);
        return Response.of(newProxyResponded.getId());
    }
}
