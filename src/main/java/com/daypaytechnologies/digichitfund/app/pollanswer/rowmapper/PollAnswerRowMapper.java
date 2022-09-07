package com.daypaytechnologies.digichitfund.app.pollanswer.rowmapper;

import com.daypaytechnologies.digichitfund.app.pollanswer.data.PollAnswerData;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PollAnswerRowMapper implements RowMapper<PollAnswerData> {

    private final String schema;

    public PollAnswerRowMapper() {
        final StringBuilder builder = new StringBuilder(200);
        builder.append("FROM poll_answer pa ");
        builder.append("INNER JOIN poll_question q ON q.id = pa.question_id ");
        builder.append("INNER JOIN poll_question_option a ON a.id = pa.option_id ");
        this.schema = builder.toString();
    }
    public String schema() {
        return this.schema;
    }

    public String tableSchema() {
        final StringBuilder builder = new StringBuilder(200);
        builder.append("pa.id as id, ");
        builder.append("pa.question_id as questionId, ");
        builder.append("pa.option_id as optionId, ");
        builder.append("q.question as question, ");
        builder.append("a.option_name as option ");
        builder.append(this.schema);
        return builder.toString();
    }
    @Override
    public PollAnswerData mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final long id = rs.getLong("id");
        final Long questionId = rs.getLong("questionId");
        final Long optionId = rs.getLong("optionId");
        final String question = rs.getString("question");
        final String option = rs.getString("option");
        return PollAnswerData.newInstance(id, questionId, optionId,question,option);
    }
}
