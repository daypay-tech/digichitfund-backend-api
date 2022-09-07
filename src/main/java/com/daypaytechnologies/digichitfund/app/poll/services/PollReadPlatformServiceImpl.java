package com.daypaytechnologies.digichitfund.app.poll.services;

import com.daypaytechnologies.digichitfund.infrastructure.pagination.Page;
import com.daypaytechnologies.digichitfund.infrastructure.pagination.PaginationHelper;
import com.daypaytechnologies.digichitfund.app.poll.data.PollData;
import com.daypaytechnologies.digichitfund.app.poll.data.PollQuestionAggregationData;
import com.daypaytechnologies.digichitfund.app.poll.domain.Poll;
import com.daypaytechnologies.digichitfund.app.poll.domain.PollRepositoryWrapper;
import com.daypaytechnologies.digichitfund.app.poll.rowmapper.PollQuestionAggregationRowMapper;
import com.daypaytechnologies.digichitfund.app.poll.rowmapper.PollRowMapper;
import com.daypaytechnologies.digichitfund.app.user.domain.administrationuser.AdministrationUser;
import com.daypaytechnologies.digichitfund.security.services.PlatformSecurityContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PollReadPlatformServiceImpl implements PollReadPlatformService {

    private final JdbcTemplate jdbcTemplate;
    private final PollRepositoryWrapper pollRepositoryWrapper;
    private final PaginationHelper<PollData> paginationHelper = new PaginationHelper<>();

    private final PlatformSecurityContext platformSecurityContext;

    @Override
    public Page<PollData> fetchAll(Long categoryId, int page, int pageSize) {
        StringBuilder sbCategoryCondition = new StringBuilder();
        sbCategoryCondition.append("p.is_deleted IS NULL ");
        if(categoryId != null) {
            sbCategoryCondition.append(" AND p.category_id =").append(categoryId);
        }
        AdministrationUser administrationUser = this.platformSecurityContext.validateAdministrationUser();
        if(administrationUser != null) {
            sbCategoryCondition.append(" AND u.id =").append(administrationUser.getId());
        }
        final PollRowMapper pollRowMapper = new PollRowMapper();
        String qry = "SELECT " + pollRowMapper.tableSchema() + " WHERE "+sbCategoryCondition.toString()+" ORDER BY p.id DESC offset ? limit ? ";
        String countQry = "SELECT count(p) " + pollRowMapper.schema() + " WHERE "+sbCategoryCondition.toString();
        return this.paginationHelper.fetchPage(this.jdbcTemplate, countQry, qry, pollRowMapper, page, pageSize);
    }

    @Override
    public List<PollData> fetchAll(Long categoryId) {
        final PollRowMapper pollRowMapper = new PollRowMapper();
        String qry = "SELECT "+pollRowMapper.tableSchema();
        StringBuilder sbCategoryCondition = new StringBuilder();
        sbCategoryCondition.append("p.is_deleted IS NULL ");
        if(categoryId != null) {
            sbCategoryCondition.append(" AND p.category_id =").append(categoryId);
        }
        AdministrationUser administrationUser = this.platformSecurityContext.validateAdministrationUser();
        if(administrationUser != null) {
            qry = qry +" AND u.id = "+administrationUser.getId();
        }
        qry = qry + " WHERE "+sbCategoryCondition.toString();
        return this.jdbcTemplate.query(qry, pollRowMapper);
    }

    @Override
    public PollData fetchByPollId(Long pollId) {
        final Poll poll = this.pollRepositoryWrapper.findOneWithNotFoundDetection(pollId);
        final PollRowMapper pollRowMapper = new PollRowMapper();
        String qry = "SELECT "+pollRowMapper.tableSchema();
        qry = qry + " WHERE p.id = ?";
        AdministrationUser administrationUser = this.platformSecurityContext.validateAdministrationUser();
        if(administrationUser != null) {
            qry = qry +" AND u.id = "+administrationUser.getId();
        }
        return this.jdbcTemplate.queryForObject(qry, pollRowMapper, new Object[] { poll.getId() });
    }

    @Override
    public Page<PollQuestionAggregationData> fetchAllPollQuestionAggregation(Long categoryId, Long pollId, int page, int pageSize) {
        StringBuilder sbCategoryCondition = new StringBuilder();
        sbCategoryCondition.append("p.is_deleted IS NULL ");
        if(categoryId != null) {
            sbCategoryCondition.append(" AND p.category_id =").append(categoryId);
        }
        if(pollId != null) {
            sbCategoryCondition.append(" AND p.id =").append(pollId);
        }
        AdministrationUser administrationUser = this.platformSecurityContext.forceValidateAdministrationUser();
        sbCategoryCondition.append(" AND u.id =").append(administrationUser.getId());
        final PollQuestionAggregationRowMapper pollRowMapper = new PollQuestionAggregationRowMapper(categoryId, pollId, administrationUser.getId());
        String qry = "SELECT " + pollRowMapper.tableSchema() + " WHERE "+sbCategoryCondition+" ORDER BY p.id DESC offset ? limit ? ";
        String countQry = "SELECT count(p) " + pollRowMapper.schema() + " WHERE "+sbCategoryCondition;
        final PaginationHelper<PollQuestionAggregationData> paginationHelper = new PaginationHelper<>();
        return paginationHelper.fetchPage(this.jdbcTemplate, countQry, qry, pollRowMapper, page, pageSize);
    }
}
