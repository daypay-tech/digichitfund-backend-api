package com.daypaytechnologies.digichitfund.app.chart.rowmapper;

import com.daypaytechnologies.digichitfund.app.chart.data.QuestionOptionChartData;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QuestionOptionChartRowMapper implements RowMapper<QuestionOptionChartData>  {

    private final String schema;

    public QuestionOptionChartRowMapper(Long questionId) {
        final StringBuilder sb = new StringBuilder();
        sb.append("SELECT count(m.optionId) as count,");
        sb.append(" o.option_name as option, ");
        sb.append(" o.id as optionId ");
        sb.append(" FROM poll_question_option o  ");
        sb.append(" LEFT JOIN (");
        sb.append(" SELECT a.option_id as optionId  ");
        sb.append(" FROM poll_answer a  ");
        sb.append(" INNER JOIN proxy_responded m ON m.id = a.proxy_responded_id ");
        sb.append(" INNER JOIN poll_question_option o ON o.id = a.option_id ");
        sb.append(" INNER JOIN poll_question q ON q.id = a.question_id  ");
        sb.append(" WHERE a.proxy_responded_id IS NOT NULL ");
        sb.append(" AND a.question_id = "+questionId+"");
        sb.append(" ) as m ON m.optionId = o.id ");
        sb.append(" INNER JOIN poll_question q ON q.id = o.poll_question_id ");
        sb.append(" WHERE q.id = "+questionId+" GROUP BY o.option_name,o.id ORDER BY o.id ASC ");
        // step 1 done

        final StringBuilder sb2 = new StringBuilder();
        sb2.append("SELECT count(m.optionId) as count,");
        sb2.append(" o.option_name as option, ");
        sb2.append(" o.id as optionId ");
        sb2.append(" FROM poll_question_option o  ");
        sb2.append(" LEFT JOIN (");
        sb2.append(" SELECT a.option_id as optionId  ");
        sb2.append(" FROM poll_answer a  ");
        sb2.append(" INNER JOIN member m ON m.id = a.member_answered_by ");
        sb2.append(" INNER JOIN poll_question_option o ON o.id = a.option_id ");
        sb2.append(" INNER JOIN poll_question q ON q.id = a.question_id  ");
        sb2.append(" WHERE a.proxy_responded_id IS NULL ");
        sb2.append(" AND a.question_id = "+questionId+"");
        sb2.append(" ) as m ON m.optionId = o.id ");
        sb2.append(" INNER JOIN poll_question q ON q.id = o.poll_question_id ");
        sb2.append(" WHERE q.id = "+questionId+" GROUP BY o.option_name,o.id ORDER BY o.id ASC ");


        final StringBuilder finalQuery = new StringBuilder();
        finalQuery.append("SELECT mg.option as optionName, " );
        finalQuery.append("SUM(mg.count + pg.count) as count " );
        finalQuery.append(" FROM " );
        finalQuery.append("( " +sb + ") as mg");
        finalQuery.append(" JOIN " );
        finalQuery.append("( " +sb2 + ") as pg");
        finalQuery.append(" ON mg.optionId = pg.optionId GROUP BY mg.option, mg.optionId ORDER BY mg.optionId ASC");
        this.schema = finalQuery.toString();
    }

    public String schema() {
        return schema;
    }

    @Override
    public QuestionOptionChartData mapRow(ResultSet rs, int rowNum) throws SQLException {
        final Long count = rs.getLong("count");
        final String option = rs.getString("optionName");
        return QuestionOptionChartData.newInstance(option, count);
    }
}
