package com.daypaytechnologies.digichitfund.app.chart.rowmapper;

import com.daypaytechnologies.digichitfund.infrastructure.utils.RoleCheckerUtils;
import com.daypaytechnologies.digichitfund.app.chart.data.QuestionChartData;
import com.daypaytechnologies.digichitfund.app.user.domain.administrationuser.AdministrationUser;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QuestionChartRowMapper implements RowMapper<QuestionChartData> {

    private final String schema;

    public QuestionChartRowMapper(AdministrationUser administrationUser) {
        final StringBuilder builder = new StringBuilder(200);
        builder.append("FROM poll_question pq ");
        builder.append("INNER JOIN np_poll p ON p.id = pq.poll_id ");
        builder.append("INNER JOIN category c ON c.id = p.category_id ");
        if(!RoleCheckerUtils.isSuperAdmin(administrationUser)) {
            builder.append(" INNER JOIN administration_user u ON u.id = p.updated_by_user_id ");
        }
        this.schema = builder.toString();
    }
    public String schema() {
        return this.schema;
    }

    public String tableSchema() {
        final StringBuilder builder = new StringBuilder(200);
        builder.append("pq.id as id, ");
        builder.append("pq.question as question ");
        builder.append(this.schema);
        return builder.toString();
    }
    @Override
    public QuestionChartData mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final long id = rs.getLong("id");
        final String question = rs.getString("question");
        return QuestionChartData.newInstance(id, question);
    }
}
