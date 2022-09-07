package com.daypaytechnologies.digichitfund.app.chart.rowmapper;

import com.daypaytechnologies.digichitfund.app.chart.data.QuestionOptionAgeChartData;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QuestionOptionByAgeChartRowMapper implements RowMapper<QuestionOptionAgeChartData>  {

    private final String schema;

    public QuestionOptionByAgeChartRowMapper(Long questionId, Long optionId) {
        final StringBuilder sb = new StringBuilder();
        sb.append("SELECT count(m.ageId) as count,");
        sb.append(" a.range as range, ");
        sb.append(" a.id as ageId ");
        sb.append(" FROM age_ref a  ");
        sb.append(" LEFT JOIN (");
        sb.append(" SELECT ar.id as ageId  ");
        sb.append(" FROM poll_answer a ");
        sb.append(" INNER JOIN proxy_responded m ON m.id = a.proxy_responded_id ");
        sb.append(" INNER JOIN age_ref ar ON ar.id = m.age_id ");
        sb.append(" INNER JOIN poll_question_option o ON o.id = a.option_id ");
        sb.append(" INNER JOIN poll_question q ON q.id = a.question_id  ");
        sb.append(" WHERE a.proxy_responded_id IS NOT NULL ");
        sb.append(" AND a.question_id = "+questionId+"");
        sb.append(" AND a.option_id = "+optionId+"");
        sb.append(" ) as m ON m.ageId = a.id ");
        sb.append(" GROUP BY a.range,a.id ORDER BY a.id ASC ");

        final StringBuilder sb2 = new StringBuilder();
        sb2.append("SELECT count(m.ageId) as count,");
        sb2.append(" a.range as range, ");
        sb2.append(" a.id as ageId ");
        sb2.append(" FROM age_ref a  ");
        sb2.append(" LEFT JOIN (");
        sb2.append(" SELECT ar.id as ageId  ");
        sb2.append(" FROM poll_answer a ");
        sb2.append(" INNER JOIN member m ON m.id = a.member_answered_by ");
        sb2.append(" INNER JOIN age_ref ar ON ar.id = m.age_id ");
        sb2.append(" INNER JOIN poll_question_option o ON o.id = a.option_id ");
        sb2.append(" INNER JOIN poll_question q ON q.id = a.question_id  ");
        sb2.append(" WHERE a.proxy_responded_id IS NULL ");
        sb2.append(" AND a.question_id = "+questionId+"");
        sb2.append(" AND a.option_id = "+optionId+"");
        sb2.append(" ) as m ON m.ageId = a.id ");
        sb2.append(" GROUP BY a.range,a.id ORDER BY a.id ASC ");

        final StringBuilder finalQuery = new StringBuilder();
        finalQuery.append("SELECT mg.range as range, " );
        finalQuery.append("SUM(mg.count + pg.count) as count " );
        finalQuery.append(" FROM " );
        finalQuery.append("( " +sb + ") as mg");
        finalQuery.append(" JOIN " );
        finalQuery.append("( " +sb2 + ") as pg");
        finalQuery.append(" ON mg.range = pg.range GROUP BY mg.range, mg.ageId ORDER BY mg.ageId ASC");

        this.schema = finalQuery.toString();
    }

    public String schema() {
        return schema;
    }

    @Override
    public QuestionOptionAgeChartData mapRow(ResultSet rs, int rowNum) throws SQLException {
        final Long count = rs.getLong("count");
        final String range = rs.getString("range");
        return QuestionOptionAgeChartData.newInstance(range, count);
    }
}
