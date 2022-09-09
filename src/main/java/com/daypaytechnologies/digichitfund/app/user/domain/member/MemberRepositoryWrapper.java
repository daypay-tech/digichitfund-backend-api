package com.daypaytechnologies.digichitfund.app.user.domain.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberRepositoryWrapper {

    private final MemberRepository memberRepository;


}
