package com.daypaytechnologies.digichitfund.app.poll.rowmapper;

import com.daypaytechnologies.digichitfund.app.poll.data.PollData;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PollRowMapper implements RowMapper<PollData> {

    private final String schema;

    public PollRowMapper() {
        final StringBuilder builder = new StringBuilder(200);
        builder.append("FROM np_poll p ");
        builder.append("INNER JOIN category c on c.id = p.category_id ");
        builder.append("INNER JOIN administration_user u on u.id = p.created_by_user_id ");
        this.schema = builder.toString();
    }

    public String schema() {
        return this.schema;
    }

    public String tableSchema() {
        final StringBuilder builder = new StringBuilder(200);
        builder.append("p.id as id, ");
        builder.append("c.category_name as categoryName, ");
        builder.append("c.id as categoryId, ");
        builder.append("p.poll_name as pollName, ");
        builder.append("u.first_name as firstName, ");
        builder.append("u.last_name as lastName ");
        builder.append(this.schema);
        return builder.toString();
    }

    @Override
    public PollData mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final long id = rs.getLong("id");
        final String pollName = rs.getString("pollName");
        final String categoryName = rs.getString("categoryName");
        final Long categoryId = rs.getLong("categoryId");
        final String firstName = rs.getString("firstName");
        final String lastName = rs.getString("lastName");
        return PollData.newInstance(id, pollName, categoryName, categoryId, firstName, lastName);
    }
}
