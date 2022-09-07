package com.daypaytechnologies.digichitfund.app.chart.rowmapper;

import com.daypaytechnologies.digichitfund.infrastructure.utils.RoleCheckerUtils;
import com.daypaytechnologies.digichitfund.app.chart.data.AgeChartData;
import com.daypaytechnologies.digichitfund.app.user.domain.administrationuser.AdministrationUser;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AgeChartRowMapper implements RowMapper<AgeChartData> {

    private final String schema;

    public AgeChartRowMapper(Long categoryId, Long pollId, Long questionId, AdministrationUser administrationUser) {
        final StringBuilder sb = new StringBuilder();
        sb.append("SELECT SUM(case when LOWER(m.gender) = 'male' then 1 else 0 end) as maleCount,");
        sb.append(" SUM(case when LOWER(m.gender) = 'female' then 1 else 0 end) as femaleCount, ");
        sb.append(" SUM(case when LOWER(m.gender) = 'others' then 1 else 0 end) as othersCount, ");
        sb.append(" ar.range as range, ");
        sb.append(" ar.id as ageId ");
        sb.append(" FROM age_ref ar ");
        sb.append(" LEFT JOIN (");
        sb.append(" SELECT g.gender as gender,ar.id as ageId ");
        sb.append(" FROM poll_answer a ");
        sb.append(" INNER JOIN member m ON m.id = a.member_answered_by ");
        sb.append(" INNER JOIN age_ref ar ON ar.id = m.age_id ");
        sb.append(" INNER JOIN gender_ref g ON g.id = m.gender_id ");
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
        if(questionId != null) {
            sb.append(" AND a.question_id = "+questionId+"");
        }
        sb.append(" ) as m ON m.ageId = ar.id ");
        sb.append(" GROUP BY ar.range,ar.id ORDER BY ar.id ASC ");
        // step 1 done

        final StringBuilder sb2 = new StringBuilder();
        sb2.append("SELECT SUM(case when LOWER(m.gender) = 'male' then 1 else 0 end) as maleCount,");
        sb2.append(" SUM(case when LOWER(m.gender) = 'female' then 1 else 0 end) as femaleCount, ");
        sb2.append(" SUM(case when LOWER(m.gender) = 'others' then 1 else 0 end) as othersCount, ");
        sb2.append(" ar.range as range, ");
        sb2.append(" ar.id as ageId ");
        sb2.append(" FROM age_ref ar ");
        sb2.append(" LEFT JOIN (");
        sb2.append(" SELECT g.gender as gender,ar.id as ageId ");
        sb2.append(" FROM poll_answer a ");
        sb2.append(" INNER JOIN proxy_responded m ON m.id = a.proxy_responded_id ");
        sb2.append(" INNER JOIN age_ref ar ON ar.id = m.age_id ");
        sb2.append(" INNER JOIN gender_ref g ON g.id = m.gender_id ");
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
        if(questionId != null) {
            sb2.append(" AND a.question_id = "+questionId+"");
        }
        sb2.append(" ) as m ON m.ageId = ar.id ");
        sb2.append(" GROUP BY ar.range,ar.id ORDER BY ar.id ASC ");


        final StringBuilder finalQuery = new StringBuilder();
        finalQuery.append("SELECT mg.range as range, " );
        finalQuery.append("SUM(mg.maleCount + pg.maleCount) as maleCount," );
        finalQuery.append("SUM(mg.femaleCount + pg.femaleCount) as femaleCount," );
        finalQuery.append("SUM(mg.othersCount + pg.othersCount) as othersCount" );
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
    public AgeChartData mapRow(ResultSet rs, int rowNum) throws SQLException {
        final Long maleCount = rs.getLong("maleCount");
        final Long femaleCount = rs.getLong("femaleCount");
        final Long othersCount = rs.getLong("othersCount");
        final String range = rs.getString("range");
        return AgeChartData.newInstance(maleCount, femaleCount, othersCount, range);
    }
}
