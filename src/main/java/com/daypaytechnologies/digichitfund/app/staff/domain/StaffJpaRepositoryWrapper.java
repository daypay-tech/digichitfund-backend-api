package com.daypaytechnologies.digichitfund.app.staff.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class StaffJpaRepositoryWrapper {

    private final StaffJpaRepository staffJpaRepository;
}
