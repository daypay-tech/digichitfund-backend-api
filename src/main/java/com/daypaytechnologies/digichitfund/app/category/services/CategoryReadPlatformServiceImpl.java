package com.daypaytechnologies.digichitfund.app.category.services;

import com.daypaytechnologies.digichitfund.infrastructure.exceptions.NotFoundException;
import com.daypaytechnologies.digichitfund.app.category.data.CategoryData;
import com.daypaytechnologies.digichitfund.app.category.domain.Category;
import com.daypaytechnologies.digichitfund.app.category.domain.CategoryRepositoryWrapper;
import com.daypaytechnologies.digichitfund.app.category.rowmapper.CategoryRowMapper;
import com.daypaytechnologies.digichitfund.app.user.domain.administrationuser.AdministrationUser;
import com.daypaytechnologies.digichitfund.security.services.PlatformSecurityContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryReadPlatformServiceImpl implements CategoryReadPlatformService{

    private final JdbcTemplate jdbcTemplate;
    private final CategoryRepositoryWrapper categoryRepositoryWrapper;

    private final PlatformSecurityContext platformSecurityContext;

    @Override
    public List<CategoryData> fetchAll() {
        final CategoryRowMapper categoryRowMapper = new CategoryRowMapper();
        String qry = "SELECT " + categoryRowMapper.tableSchema() +" WHERE c.is_deleted is null";
        AdministrationUser administrationUser = this.platformSecurityContext.validateAdministrationUser();
        if(administrationUser != null) {
            qry = qry +" AND u.id = "+administrationUser.getId();
        }
        return this.jdbcTemplate.query(qry, categoryRowMapper);
    }

    @Override
    public CategoryData fetchByCategoryId(Long categoryId) {
        final Category category = this.categoryRepositoryWrapper.findOneWithNotFoundDetection(categoryId);
        final CategoryRowMapper categoryRowMapper = new CategoryRowMapper();
        String qry = "SELECT "+categoryRowMapper.tableSchema();
        qry = qry + " WHERE c.id = ?";
        AdministrationUser administrationUser = this.platformSecurityContext.validateAdministrationUser();
        if(administrationUser != null) {
            qry = qry +" AND u.id = "+administrationUser.getId();
        }
        try {
            return this.jdbcTemplate.queryForObject(qry, categoryRowMapper, new Object[] { category.getId() });
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Category Id not found");
        }
    }
}
