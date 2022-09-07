package com.daypaytechnologies.digichitfund.app.pollquestion.rowmapper;

import com.daypaytechnologies.digichitfund.app.pollquestion.data.PollQuestionData;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PollQuestionRowMapper implements RowMapper<PollQuestionData> {

    private final String schema;

    public PollQuestionRowMapper() {
        final StringBuilder builder = new StringBuilder(200);
        builder.append("FROM poll_question pq ");
        builder.append("INNER JOIN administration_user u ON u.id = pq.updated_by_user_id ");
        builder.append("INNER JOIN np_poll p ON p.id = pq.poll_id ");
        builder.append("INNER JOIN category c ON c.id = p.category_id ");
        this.schema = builder.toString();
    }
    public String schema() {
        return this.schema;
    }
    public String tableSchema() {
        final StringBuilder builder = new StringBuilder(200);
        builder.append("pq.id as id, ");
        builder.append("p.id as pollId, ");
        builder.append("pq.question as question, ");
        builder.append("c.category_name as categoryName, ");
        builder.append("c.id as categoryId, ");
        builder.append("p.poll_name as pollName ");
        builder.append(this.schema);
        return builder.toString();
    }
    @Override
    public PollQuestionData mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final long id = rs.getLong("id");
        final Long pollId = rs.getLong("pollId");
        final String question = rs.getString("question");
        final String pollName = rs.getString("pollName");
        final String categoryName = rs.getString("categoryName");
        final Long categoryId = rs.getLong("categoryId");
        return PollQuestionData.newInstance(id, question, pollName, pollId, categoryName, categoryId);
    }
}
