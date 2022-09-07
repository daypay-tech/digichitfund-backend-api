package com.daypaytechnologies.digichitfund.app.user.services;

import com.daypaytechnologies.digichitfund.infrastructure.pagination.Page;
import com.daypaytechnologies.digichitfund.infrastructure.pagination.PaginationHelper;
import com.daypaytechnologies.digichitfund.app.user.data.MemberData;
import com.daypaytechnologies.digichitfund.app.user.data.UserData;
import com.daypaytechnologies.digichitfund.app.user.rowmapper.MemberRowMapper;
import com.daypaytechnologies.digichitfund.app.user.rowmapper.UserRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserReadPlatformServiceImpl implements UserReadPlatformService {

    private final JdbcTemplate jdbcTemplate;
    private final PaginationHelper<MemberData> paginationHelper = new PaginationHelper<>();

    @Override
    public Page<MemberData> fetchAll(int page, int pageSize) {
        log.debug("Start of fetchAll page {} pageSize {}", page, pageSize);
        final MemberRowMapper memberRowMapper = new MemberRowMapper();
        String qry = "SELECT " +memberRowMapper.tableSchema() +" WHERE m.is_deleted is null offset ? limit ? ";
        String countQry = "SELECT count(m) " + memberRowMapper.schema()+ "WHERE m.is_deleted is null";
        return this.paginationHelper.fetchPage(this.jdbcTemplate, countQry, qry, memberRowMapper, page, pageSize);
    }

    @Override
    public Page<UserData> fetchAdministrationUsers(int page, int pageSize) {
        log.debug("Start of fetchAdministrationUser page {} pageSize {}", page, pageSize);
        final UserRowMapper administrationUserRowMapper = new UserRowMapper();
        String qry = "SELECT " +administrationUserRowMapper.tableSchema()+" offset ? limit ?";
        String countQry = "SELECT count(u) " + administrationUserRowMapper.schema();
        final PaginationHelper<UserData> paginationHelper = new PaginationHelper<>();
        return paginationHelper.fetchPage(this.jdbcTemplate, countQry, qry, administrationUserRowMapper, page, pageSize);
    }

}
