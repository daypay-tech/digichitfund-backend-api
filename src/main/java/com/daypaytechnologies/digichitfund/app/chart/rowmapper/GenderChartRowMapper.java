package com.daypaytechnologies.digichitfund.app.chart.rowmapper;

import com.daypaytechnologies.digichitfund.infrastructure.utils.RoleCheckerUtils;
import com.daypaytechnologies.digichitfund.app.chart.data.GenderChartData;
import com.daypaytechnologies.digichitfund.app.user.domain.administrationuser.AdministrationUser;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenderChartRowMapper implements RowMapper<GenderChartData> {

    private String schema;


    public GenderChartRowMapper(Long categoryId, Long pollId, AdministrationUser administrationUser) {
        final StringBuilder sb = new StringBuilder();
        sb.append("SELECT count(m.gender_id) as count,");
        sb.append(" g.gender as gender, ");
        sb.append(" g.id as genderId ");
        sb.append(" FROM gender_ref g");
        sb.append(" LEFT JOIN (");
        sb.append(" SELECT m.gender_id FROM poll_answer a");
        sb.append(" INNER JOIN member m ON m.id = a.member_answered_by ");
        sb.append(" INNER JOIN poll_question q ON q.id = a.question_id ");
        sb.append(" INNER JOIN np_poll p ON p.id = q.poll_id ");
        if(!RoleCheckerUtils.isSuperAdmin(administrationUser)) {
            sb.append(" INNER JOIN administration_user u ON u.id = p.updated_by_user_id ");
        }
        sb.append(" INNER JOIN category c ON c.id = p.category_id ");
        sb.append(" WHERE a.proxy_responded_id IS NULL ");
        if(!RoleCheckerUtils.isSuperAdmin(administrationUser)) {
            sb.append(" AND u.id = "+administrationUser.getId()+" ");
        }
        if(categoryId != null) {
            sb.append(" AND c.id = "+categoryId+"");
        }
        if(pollId != null) {
            sb.append(" AND p.id = "+pollId+"");
        }
        sb.append(" ) as m");
        sb.append(" ON m.gender_id = g.id ");
        sb.append(" GROUP BY m.gender_id, g.id, g.gender ");

        final StringBuilder sb2 = new StringBuilder();
        sb2.append("SELECT count(p.gender_id) as count,");
        sb2.append(" g.gender as gender, ");
        sb2.append(" g.id as genderId ");
        sb2.append(" FROM gender_ref g ");
        sb2.append(" LEFT JOIN ( ");
        sb2.append(" SELECT m.gender_id FROM poll_answer a");
        sb2.append(" INNER JOIN proxy_responded m ON m.id = a.proxy_responded_id ");
        sb2.append(" INNER JOIN poll_question q ON q.id = a.question_id ");
        sb2.append(" INNER JOIN np_poll p ON p.id = q.poll_id ");
        if(!RoleCheckerUtils.isSuperAdmin(administrationUser)) {
            sb2.append(" INNER JOIN administration_user u ON u.id = p.updated_by_user_id ");
        }
        sb2.append(" INNER JOIN category c ON c.id = p.category_id ");
        sb2.append(" WHERE a.proxy_responded_id IS NOT NULL ");
        if(!RoleCheckerUtils.isSuperAdmin(administrationUser)) {
            sb2.append(" AND u.id = "+administrationUser.getId()+" ");
        }
        if(categoryId != null) {
            sb2.append(" AND c.id = "+categoryId+"");
        }
        if(pollId != null) {
            sb2.append(" AND p.id = "+pollId+"");
        }
        sb2.append(" ) as p");
        sb2.append(" ON p.gender_id = g.id ");
        sb2.append(" GROUP BY p.gender_id, g.id, g.gender");


        final StringBuilder finalQuery = new StringBuilder();
        finalQuery.append("SELECT mg.gender as gender, " );
        finalQuery.append("SUM(mg.count + pg.count) as count" );
        finalQuery.append(" FROM " );
        finalQuery.append("( " +sb + ") as mg");
        finalQuery.append(" JOIN " );
        finalQuery.append("( " +sb2 + ") as pg");
        finalQuery.append(" ON mg.gender = pg.gender group by mg.gender,mg.genderId ORDER BY mg.genderId ASC");
        this.schema = finalQuery.toString();
    }

    public String schema() {
        return this.schema;
    }

    @Override
    public GenderChartData mapRow(ResultSet rs, int rowNum) throws SQLException {
        final Long count = rs.getLong("count");
        final String gender = rs.getString("gender");
        return GenderChartData.newInstance(count, gender);
    }
}
