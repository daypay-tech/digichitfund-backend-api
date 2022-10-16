package com.daypaytechnologies.digichitfund.app.member.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberJpaRepositoryWrapper {

    private final MemberJpaRepository memberJpaRepository;
}
