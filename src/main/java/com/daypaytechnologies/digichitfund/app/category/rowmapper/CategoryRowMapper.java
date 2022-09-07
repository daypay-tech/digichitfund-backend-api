package com.daypaytechnologies.digichitfund.app.category.rowmapper;

import com.daypaytechnologies.digichitfund.app.category.data.CategoryData;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryRowMapper implements RowMapper<CategoryData> {

    private final String schema;

    public CategoryRowMapper() {
        final StringBuilder builder = new StringBuilder(200);
        builder.append("FROM category c ");
        builder.append("INNER JOIN administration_user u ON u.id = c.updated_by_user_id ");
        this.schema = builder.toString();
    }
    public String schema() {
        return this.schema;
    }
    public String tableSchema() {
        final StringBuilder builder = new StringBuilder(200);
        builder.append("c.id as id, ");
        builder.append("c.category_name as categoryName, ");
        builder.append("c.created_at as createdAt, ");
        builder.append("u.first_name as firstName, ");
        builder.append("u.last_name as lastName ");
        builder.append(this.schema);
        return builder.toString();
    }
    @Override
    public CategoryData mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final long id = rs.getLong("id");
        final String categoryName = rs.getString("categoryName");
        final String createdAt = rs.getString("createdAt");
        final String firstName = rs.getString("firstName");
        final String lastName = rs.getString("lastName");
        return CategoryData.newInstance(id, categoryName, createdAt, firstName, lastName);
    }
}
