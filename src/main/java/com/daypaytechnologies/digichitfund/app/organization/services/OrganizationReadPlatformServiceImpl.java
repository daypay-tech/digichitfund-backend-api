package com.daypaytechnologies.digichitfund.app.organization.services;

import com.daypaytechnologies.digichitfund.app.organization.data.OrganizationData;
import com.daypaytechnologies.digichitfund.app.organization.domain.Organization;
import com.daypaytechnologies.digichitfund.app.organization.domain.OrganizationRepositoryWrapper;
import com.daypaytechnologies.digichitfund.app.organization.rowmapper.OrganizationRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrganizationReadPlatformServiceImpl implements OrganizationReadPlatformService {

    private final JdbcTemplate jdbcTemplate;

    private final OrganizationRepositoryWrapper organizationRepositoryWrapper;

    @Override
    public OrganizationData fetchById(Long orgId) {
        final Organization organization = this.organizationRepositoryWrapper.findOneWithNotFoundDetection(orgId);
        final OrganizationRowMapper organizationRowMapper = new OrganizationRowMapper();
        String qry = "SELECT " + organizationRowMapper.tableSchema();
        qry = qry + " WHERE o.id = ?";
        return this.jdbcTemplate.queryForObject(qry, organizationRowMapper, new Object[]{ organization.getId() });
    }

    @Override
    public List<OrganizationData> fetchAll() {
        final OrganizationRowMapper organizationRowMapper = new OrganizationRowMapper();
        final String qry = "SELECT " + organizationRowMapper.tableSchema();
        return this.jdbcTemplate.query(qry, organizationRowMapper);
    }
}
