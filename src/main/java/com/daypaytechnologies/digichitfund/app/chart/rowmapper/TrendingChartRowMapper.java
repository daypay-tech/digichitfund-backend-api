package com.daypaytechnologies.digichitfund.app.chart.rowmapper;

import com.daypaytechnologies.digichitfund.app.chart.data.TrendingChartData;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class TrendingChartRowMapper implements RowMapper<TrendingChartData> {

    private final String schema;

    public TrendingChartRowMapper() {
        final StringBuilder sb = new StringBuilder();
        sb.append("SELECT COUNT(a.member_answered_by) as memberCount, ");
        sb.append(" DATE(a.answered_on) as answeredAt ");
        sb.append(" FROM poll_answer a ");
        sb.append(" INNER JOIN poll_question q ON q.id = a.question_id ");
        sb.append(" INNER JOIN np_poll p ON p.id = q.poll_id ");
        sb.append(" INNER JOIN category c ON c.id = p.category_id ");
        this.schema = sb.toString();
    }

    public String schema() {
        return schema;
    }

    @Override
    public TrendingChartData mapRow(ResultSet rs, int rowNum) throws SQLException {
        final int memberCount = rs.getInt("memberCount");
        final Date date = rs.getDate("answeredAt");
        return TrendingChartData.newInstance(memberCount, date);
    }
}
