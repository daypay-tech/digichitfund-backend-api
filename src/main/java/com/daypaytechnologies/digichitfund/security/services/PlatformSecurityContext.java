package com.daypaytechnologies.digichitfund.security.services;

import com.daypaytechnologies.digichitfund.infrastructure.exceptions.AuthenticationException;
import com.daypaytechnologies.digichitfund.app.user.domain.administrationuser.AdministrationUser;
import com.daypaytechnologies.digichitfund.app.user.domain.administrationuser.AdministrationUserRepositoryWrapper;
import com.daypaytechnologies.digichitfund.app.user.domain.member.Member;
import com.daypaytechnologies.digichitfund.app.user.domain.member.MemberRepositoryWrapper;
import com.daypaytechnologies.digichitfund.security.AdministrationUserDetailsImpl;
import com.daypaytechnologies.digichitfund.security.MemberUserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlatformSecurityContext {

    private final MemberRepositoryWrapper memberRepositoryWrapper;

    private final AdministrationUserRepositoryWrapper administrationUserRepositoryWrapper;

    public Member validateMember() {
        if(!(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof MemberUserDetailsImpl)) {
            throw new AuthenticationException("User not logged in. please do login");
        }
        MemberUserDetailsImpl userDetails = (MemberUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final Member member =  this.memberRepositoryWrapper.findOneWithNotFoundDetection(userDetails.getId());
        return this.memberRepositoryWrapper.findOneWithNotFoundDetection(member.getId());
    }

    public AdministrationUser validateAdministrationUser() {
        if(!(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof AdministrationUserDetailsImpl)) {
            return null;
        }
        AdministrationUserDetailsImpl userDetails = (AdministrationUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return this.administrationUserRepositoryWrapper.findOneWithNotFoundDetection(userDetails.getId());
    }

    public AdministrationUser forceValidateAdministrationUser() {
        if(!(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof AdministrationUserDetailsImpl)) {
            throw new AuthenticationException("User not logged in. please do login");
        }
        AdministrationUserDetailsImpl userDetails = (AdministrationUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return this.administrationUserRepositoryWrapper.findOneWithNotFoundDetection(userDetails.getId());
    }
}
