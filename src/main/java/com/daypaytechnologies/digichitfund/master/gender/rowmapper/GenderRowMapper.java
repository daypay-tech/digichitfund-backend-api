package com.daypaytechnologies.digichitfund.master.gender.rowmapper;

import com.daypaytechnologies.digichitfund.master.gender.data.GenderData;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenderRowMapper implements RowMapper<GenderData> {

    private final String schema;

    public GenderRowMapper() {
        final StringBuilder builder = new StringBuilder(200);
        builder.append("FROM gender_ref c ");
        this.schema = builder.toString();
    }
    public String schema() {
        return this.schema;
    }

    public String selectSchema() {
        final StringBuilder builder = new StringBuilder(200);
        builder.append("c.id as genderId, ");
        builder.append("c.gender as gender ");
        builder.append(this.schema);
        return builder.toString();
    }
    @Override
    public GenderData mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final long id = rs.getLong("genderId");
        final String gender = rs.getString("gender");
        return GenderData.newInstance(id, gender);
    }
}
