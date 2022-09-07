package com.daypaytechnologies.digichitfund.master.gender.services;

import com.daypaytechnologies.digichitfund.master.gender.data.GenderData;
import com.daypaytechnologies.digichitfund.master.gender.rowmapper.GenderRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenderReadPlatformServiceImpl implements GenderReadPlatformService {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<GenderData> fetchAll() {
        final GenderRowMapper genderRowMapper = new GenderRowMapper();
        final String qry = "SELECT " + genderRowMapper.selectSchema();
        return jdbcTemplate.query(qry, genderRowMapper);
    }
}
