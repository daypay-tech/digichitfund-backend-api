package com.daypaytechnologies.digichitfund.app.chart.rowmapper;

import com.daypaytechnologies.digichitfund.app.chart.data.MemberSubscriptionData;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberSubscriptionChartRowMapper implements RowMapper<MemberSubscriptionData> {

    private final String schema;

    public MemberSubscriptionChartRowMapper(String yearCondition) {
        final StringBuilder mSb = new StringBuilder();
        mSb.append(" SELECT TO_CHAR(m.created_at, 'Mon')  as month, ");
        mSb.append(" SUM(case when LOWER(g.gender) = 'male' then 1 else 0 end) as maleCount, ");
        mSb.append(" SUM(case when LOWER(g.gender) = 'female' then 1 else 0 end) as femaleCount ");
        mSb.append(" FROM member m ");
        mSb.append(" INNER JOIN gender_ref g ON g.id = m.gender_id ");
        mSb.append(" WHERE extract(year from m.created_at) = "+yearCondition);
        mSb.append(" GROUP BY TO_CHAR(m.created_at, 'Mon')");

        final StringBuilder pSb = new StringBuilder();
        pSb.append(" SELECT TO_CHAR(p.created_at, 'Mon')  as month, ");
        pSb.append(" SUM(case when LOWER(g.gender) = 'male' then 1 else 0 end) as maleCount, ");
        pSb.append(" SUM(case when LOWER(g.gender) = 'female' then 1 else 0 end) as femaleCount ");
        pSb.append(" FROM proxy_responded p ");
        pSb.append(" INNER JOIN gender_ref g ON g.id = p.gender_id ");
        pSb.append(" WHERE extract(year from p.created_at) = "+yearCondition);
        pSb.append(" GROUP BY TO_CHAR(p.created_at, 'Mon')");

        final StringBuilder sb = new StringBuilder();
        sb.append("SELECT m.month,");
        sb.append("SUM(m.femaleCount + p.femaleCount) as femaleCount, ");
        sb.append("SUM(m.maleCount + p.maleCount) as maleCount ");
        sb.append("FROM ");
        sb.append("(").append(mSb).append(") m");
        sb.append(" JOIN ");
        sb.append("(").append(pSb).append(") p");
        sb.append(" ON m.month = p.month GROUP BY m.month, p.month ");
        this.schema = sb.toString();
    }

    public String schema() {
        return schema;
    }

    @Override
    public MemberSubscriptionData mapRow(ResultSet rs, int rowNum) throws SQLException {
        final String month = rs.getString("month");
        final long maleCount = rs.getLong("maleCount");
        final long femaleCount = rs.getLong("femaleCount");
        return MemberSubscriptionData.newInstance(month, maleCount, femaleCount);
    }

}
