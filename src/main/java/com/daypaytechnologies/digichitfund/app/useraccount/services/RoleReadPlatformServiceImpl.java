package com.daypaytechnologies.digichitfund.app.useraccount.services;


import com.daypaytechnologies.digichitfund.app.useraccount.data.RoleData;
import com.daypaytechnologies.digichitfund.app.useraccount.rowmapper.RoleRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoleReadPlatformServiceImpl implements RoleReadPlatformService {

    private final JdbcTemplate jdbcTemplate;


    @Override
    public List<RoleData> fetchAll() {
        log.debug("Start of fetchAll() Role{}", "Role Data");
        final RoleRowMapper roleRowMapper = new RoleRowMapper();
        final String qry = "SELECT " + roleRowMapper.selectSchema()+" WHERE r.is_user_defined = true";
        log.debug("End of fetchAll() Role {}", qry);
        return jdbcTemplate.query(qry, roleRowMapper);
    }
}
