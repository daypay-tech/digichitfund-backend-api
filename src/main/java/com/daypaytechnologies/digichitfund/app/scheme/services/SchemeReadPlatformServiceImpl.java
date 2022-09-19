package com.daypaytechnologies.digichitfund.app.scheme.services;

import com.daypaytechnologies.digichitfund.app.data.EnumOptionData;
import com.daypaytechnologies.digichitfund.app.enumoptions.CalendarTypeEnum;
import com.daypaytechnologies.digichitfund.app.scheme.data.SchemeData;
import com.daypaytechnologies.digichitfund.app.scheme.data.SchemeTemplateData;
import com.daypaytechnologies.digichitfund.app.scheme.domain.Scheme;
import com.daypaytechnologies.digichitfund.app.scheme.domain.SchemeRepositoryWrapper;
import com.daypaytechnologies.digichitfund.app.scheme.rowmapper.SchemeRowMapper;
import com.daypaytechnologies.digichitfund.infrastructure.pagination.Page;
import com.daypaytechnologies.digichitfund.infrastructure.pagination.PaginationHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SchemeReadPlatformServiceImpl implements SchemeReadPlatformService {
    private final JdbcTemplate jdbcTemplate;

    private final SchemeRepositoryWrapper schemeRepositoryWrapper;

    private final PaginationHelper<SchemeData> paginationHelper = new PaginationHelper<>();

    @Override
    public Page<SchemeData> fetchAll(int page, int pageSize) {
        final SchemeRowMapper schemeRowMapper = new SchemeRowMapper();
        String qry = "SELECT " + schemeRowMapper.tableSchema() +" WHERE sc.is_deleted is null offset ? limit ? ";
        String countQry = "SELECT count(sc) " + schemeRowMapper.schema()+ "WHERE sc.is_deleted is null";
        return this.paginationHelper.fetchPage(this.jdbcTemplate, countQry, qry, schemeRowMapper, page, pageSize);
    }

    @Override
    public SchemeTemplateData fetchTemplateData() {
        final List<EnumOptionData> calendarList = CalendarTypeEnum.getAllCalendarTypes();
        return SchemeTemplateData.newInstance(calendarList);
    }

    @Override
    public List<SchemeData> fetchAll() {
        final SchemeRowMapper schemeRowMapper = new SchemeRowMapper();
        String qry = "SELECT "+schemeRowMapper.tableSchema();
        return this.jdbcTemplate.query(qry, schemeRowMapper);
    }
    @Override
    public SchemeData fetchBySchemeId(Long schemeId) {
        final Scheme scheme = this.schemeRepositoryWrapper.findOneWithNotFoundDetection(schemeId);
        final SchemeRowMapper schemeRowMapper = new SchemeRowMapper();
        String qry = "SELECT "+schemeRowMapper.tableSchema();
        qry = qry + " WHERE sc.id = ?";
        return this.jdbcTemplate.queryForObject(qry, schemeRowMapper, new Object[] { scheme.getId() });
    }


}
