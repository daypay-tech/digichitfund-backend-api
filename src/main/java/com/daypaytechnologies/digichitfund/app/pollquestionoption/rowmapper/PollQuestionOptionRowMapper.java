package com.daypaytechnologies.digichitfund.app.pollquestionoption.rowmapper;

import com.daypaytechnologies.digichitfund.app.pollquestionoption.data.PollQuestionOptionData;
import com.daypaytechnologies.digichitfund.app.pollquestionoption.domain.PollQuestionOptionRepositoryWrapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PollQuestionOptionRowMapper implements RowMapper<PollQuestionOptionData> {

    private final String schema;

    private final PollQuestionOptionRepositoryWrapper pollQuestionOptionRepositoryWrapper;

    public PollQuestionOptionRowMapper(final PollQuestionOptionRepositoryWrapper pollQuestionOptionRepositoryWrapper) {
        this.pollQuestionOptionRepositoryWrapper = pollQuestionOptionRepositoryWrapper;
        final StringBuilder builder = new StringBuilder(200);
        builder.append("FROM poll_question_option po ");
        this.schema = builder.toString();
    }
    public String schema() {
        return this.schema;
    }
    public String tableSchema() {
        final StringBuilder builder = new StringBuilder(200);
        builder.append("po.id as id, ");
        builder.append("po.poll_question_id as pollQuestionId, ");
        builder.append("po.option_name as option ");
        builder.append(this.schema);
        return builder.toString();
    }
    @Override
    public PollQuestionOptionData mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final long id = rs.getLong("id");
        final String option = rs.getString("option");
        return PollQuestionOptionData.newInstance(id, option);
    }
}
