package com.daypaytechnologies.digichitfund.master.age.rowmapper;

import com.daypaytechnologies.digichitfund.master.age.data.AgeData;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AgeRowMapper implements RowMapper<AgeData>  {

    private final String schema;

    public AgeRowMapper() {
        final StringBuilder builder = new StringBuilder(200);
        builder.append(" FROM age_ref c");
        this.schema = builder.toString();
    }
    public String tableSchema() {
        return this.schema;
    }

    public String selectSchema() {
        final StringBuilder builder = new StringBuilder(200);
        builder.append("c.id as id, ");
        builder.append("c.range as range ");
        builder.append(this.schema);
        return builder.toString();
    }
    @Override
    public AgeData mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final Long id = rs.getLong("id");
        final String range = rs.getString("range");
        return AgeData.newInstance(id, range);
    }
}
