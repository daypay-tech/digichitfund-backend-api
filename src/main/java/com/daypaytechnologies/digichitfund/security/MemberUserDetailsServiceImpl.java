package com.daypaytechnologies.digichitfund.security;

import com.daypaytechnologies.digichitfund.app.user.domain.member.Member;
import com.daypaytechnologies.digichitfund.app.user.domain.member.MemberRepositoryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("memberUserDetailsService")
@Slf4j
@RequiredArgsConstructor
public class MemberUserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepositoryWrapper memberRepositoryWrapper;

    @Transactional
    @Override
    public MemberUserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        Member user = memberRepositoryWrapper.findByUsername(username);
        return MemberUserDetailsImpl.build(user);
    }
}
