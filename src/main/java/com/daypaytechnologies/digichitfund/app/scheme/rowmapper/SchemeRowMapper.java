package com.daypaytechnologies.digichitfund.app.scheme.rowmapper;

import com.daypaytechnologies.digichitfund.app.enumoptions.CalendarTypeEnum;
import com.daypaytechnologies.digichitfund.app.scheme.data.SchemeData;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class SchemeRowMapper implements RowMapper<SchemeData> {
    private final String schema;

    public SchemeRowMapper() {
        final StringBuilder builder = new StringBuilder(200);
        builder.append(" FROM scheme sc ");
        this.schema = builder.toString();
    }
    public String schema() {
        return this.schema;
    }

    public String tableSchema() {
        final StringBuilder builder = new StringBuilder(200);
        builder.append("sc.id as id, ");
        builder.append("sc.scheme_name as schemeName, ");
        builder.append("sc.total_amount as totalAmount, ");
        builder.append("sc.total_members as totalMembers, ");
        builder.append("sc.calendar_enum as calendarCode, ");
        builder.append("sc.start_date as startDate, ");
        builder.append("sc.start_time as startTime, ");
        builder.append("sc.is_deleted as isDeleted ");
        builder.append(this.schema);
        return builder.toString();
    }
    @Override
    public SchemeData mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final long id = rs.getLong("id");
        final String schemeName = rs.getString("schemeName");
        final Double totalAmount = rs.getDouble("totalAmount");
        final String totalMembers = rs.getString("totalMembers");
        final String calendarCode = rs.getString("calendarCode");
        final LocalDate startDate = LocalDate.parse("startData");
        final String startTime = rs.getString("startTime");
        final Boolean isDeleted = rs.getBoolean("isDeleted");
        final String calendar = CalendarTypeEnum.getValueFromCode(calendarCode);
        return SchemeData.newInstance(id, schemeName,totalAmount,totalMembers,  calendar, calendarCode,startDate,startTime,isDeleted);
    }
}
