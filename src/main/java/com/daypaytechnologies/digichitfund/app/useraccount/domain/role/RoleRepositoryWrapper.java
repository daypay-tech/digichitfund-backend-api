package com.daypaytechnologies.digichitfund.app.useraccount.domain.role;

import com.daypaytechnologies.digichitfund.infrastructure.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleRepositoryWrapper {

    private final RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public Role findOneWithNotFoundDetection(final String roleCode) {
        return this.roleRepository.findByRoleCode(roleCode).orElseThrow(() -> new NotFoundException("Role  Not found " + roleCode));
    }

    @Transactional(readOnly = true)
    public Role findOneWithNotFoundDetection(final Long roleId) {
        return this.roleRepository.findById(roleId).orElseThrow(() -> new NotFoundException("Role  Not found " + roleId));
    }
}
