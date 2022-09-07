package com.daypaytechnologies.digichitfund.app.user.rowmapper;
import com.daypaytechnologies.digichitfund.app.user.data.RoleData;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleRowMapper implements RowMapper<RoleData> {

    private final String schema;


    public RoleRowMapper() {
        final StringBuilder builder = new StringBuilder(200);
        builder.append(" FROM role r");
        this.schema = builder.toString();
    }

    public String tableSchema() {
        return this.schema;
    }

    public String selectSchema() {
        final StringBuilder builder = new StringBuilder(200);
        builder.append("r.id as roleId, ");
        builder.append("r.role_code as code, ");
        builder.append("r.role_name as name ");
        builder.append(this.schema);
        return builder.toString();
    }

    @Override
    public RoleData mapRow(ResultSet rs, int rowNum) throws SQLException {
        final Long roleId = rs.getLong("roleId");
        final String name = rs.getString("name");
        final String code = rs.getString("code");
        return RoleData.newInstance(roleId, name, code);    }
}
