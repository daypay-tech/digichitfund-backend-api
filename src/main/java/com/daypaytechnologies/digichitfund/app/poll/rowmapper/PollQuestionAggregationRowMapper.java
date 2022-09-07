package com.daypaytechnologies.digichitfund.app.poll.rowmapper;

import com.daypaytechnologies.digichitfund.app.poll.data.PollQuestionAggregationData;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PollQuestionAggregationRowMapper implements RowMapper<PollQuestionAggregationData> {

    private final String schema;

    private final Long categoryId;

    private final Long pollId;

    private final Long userId;

    public PollQuestionAggregationRowMapper(Long categoryId, Long pollId, Long createdBy) {
        final StringBuilder builder = new StringBuilder(200);
        builder.append("FROM np_poll p ");
        builder.append("INNER JOIN category c on c.id = p.category_id ");
        builder.append("INNER JOIN administration_user u on u.id = p.created_by_user_id ");
        this.categoryId = categoryId;
        this.pollId = pollId;
        this.userId = createdBy;
        this.schema = builder.toString();
    }

    public String schema() {
        return this.schema;
    }

    public String tableSchema() {
        final StringBuilder builder = new StringBuilder(200);
        builder.append("p.id as id, ");
        StringBuilder countQry = new StringBuilder();
        countQry.append("SELECT count(*) from poll_question q ");
        countQry.append("INNER JOIN np_poll p ON p.id = q.poll_id ");
        countQry.append("INNER JOIN category c ON c.id = p.category_id ");
        countQry.append("INNER JOIN administration_user u on u.id = q.created_by_user_id ");
        countQry.append("WHERE p.is_deleted IS NULL AND u.id = "+this.userId+" ");
        if(pollId != null) {
            countQry.append("AND p.id = "+pollId);
        }
        if(categoryId != null) {
            countQry.append("AND c.id = "+categoryId);
        }
        builder.append(" ( "+countQry+" ) as questionCount, ");
        builder.append("c.category_name as categoryName, ");
        builder.append("c.id as categoryId, ");
        builder.append("p.poll_name as pollName, ");
        builder.append("u.first_name as firstName, ");
        builder.append("u.last_name as lastName ");
        builder.append(this.schema);
        return builder.toString();
    }

    @Override
    public PollQuestionAggregationData mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final long id = rs.getLong("id");
        final String pollName = rs.getString("pollName");
        final String categoryName = rs.getString("categoryName");
        final Long categoryId = rs.getLong("categoryId");
        final Long questionCount = rs.getLong("questionCount");
        final String firstName = rs.getString("firstName");
        final String lastName = rs.getString("lastName");
        return PollQuestionAggregationData.newInstance(id, pollName, categoryName, categoryId, questionCount, firstName, lastName);
    }
}
