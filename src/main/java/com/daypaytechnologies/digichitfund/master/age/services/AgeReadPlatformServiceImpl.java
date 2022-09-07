package com.daypaytechnologies.digichitfund.master.age.services;

import com.daypaytechnologies.digichitfund.master.age.data.AgeData;
import com.daypaytechnologies.digichitfund.master.age.rowmapper.AgeRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AgeReadPlatformServiceImpl implements AgeReadPlatformService {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<AgeData> fetchAll() {
        final AgeRowMapper ageRowMapper = new AgeRowMapper();
        final String qry = "SELECT " + ageRowMapper.selectSchema();
        System.out.println(qry);
        return jdbcTemplate.query(qry, ageRowMapper);
    }

    @Override
    public AgeData fetchById(Long id) {
        final AgeRowMapper ageRowMapper = new AgeRowMapper();
        String qry1 = "SELECT " + ageRowMapper.selectSchema();
        qry1 = qry1 + " WHERE c.id = ?";
        return this.jdbcTemplate.queryForObject(qry1, ageRowMapper, new Object[] {id});
    }
}
