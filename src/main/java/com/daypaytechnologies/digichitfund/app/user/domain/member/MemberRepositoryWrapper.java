package com.daypaytechnologies.digichitfund.app.user.domain.member;

import com.daypaytechnologies.digichitfund.infrastructure.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberRepositoryWrapper {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public Member findOneWithNotFoundDetection(final Long memberId) {
        return this.memberRepository.findById(memberId).orElseThrow(() -> new NotFoundException("Member  Not found " + memberId));
    }

    @Transactional(readOnly = true)
    public Member findByUsername(final String userName) {
        return this.memberRepository.findByUserName(userName).orElseThrow(() -> new NotFoundException("Member  Not found " + userName));
    }
}
