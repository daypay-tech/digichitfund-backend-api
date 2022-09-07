package com.daypaytechnologies.digichitfund.app.pollquestionoption.services;

import com.daypaytechnologies.digichitfund.infrastructure.pagination.Page;
import com.daypaytechnologies.digichitfund.infrastructure.pagination.PaginationHelper;
import com.daypaytechnologies.digichitfund.app.pollquestionoption.data.PollQuestionOptionData;
import com.daypaytechnologies.digichitfund.app.pollquestionoption.domain.PollQuestionOption;
import com.daypaytechnologies.digichitfund.app.pollquestionoption.domain.PollQuestionOptionRepositoryWrapper;
import com.daypaytechnologies.digichitfund.app.pollquestionoption.rowmapper.PollQuestionOptionRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PollQuestionOptionReadPlatformServiceImpl implements PollQuestionOptionReadPlatformService {

    private final JdbcTemplate jdbcTemplate;

    private final PollQuestionOptionRepositoryWrapper pollQuestionOptionRepositoryWrapper;

    private final PaginationHelper<PollQuestionOptionData> paginationHelper = new PaginationHelper<>();

    @Override
    public Page<PollQuestionOptionData> fetchAll(int pollQuestionId, int page, int pageSize) {
        final PollQuestionOptionRowMapper pollQuestionOptionRowMapper = new PollQuestionOptionRowMapper(this.pollQuestionOptionRepositoryWrapper);
        String qry = "SELECT " + pollQuestionOptionRowMapper.tableSchema() +" WHERE po.poll_question_id = ? AND po.is_deleted is null offset ? limit ? ";
        String countQry = "SELECT count(po) " + pollQuestionOptionRowMapper.schema()+ "WHERE po.poll_question_id = ? AND po.is_deleted is null";
        return this.paginationHelper.fetchPage(this.jdbcTemplate, countQry, qry, Arrays.asList(new Object[] {pollQuestionId}), pollQuestionOptionRowMapper, page, pageSize);
    }

    @Override
    public PollQuestionOptionData fetchByPollQuestionOptionId(Long pollQuestionOptionId) {
        final PollQuestionOption pollQuestionOption = this.pollQuestionOptionRepositoryWrapper.findOneWithNotFoundDetection(pollQuestionOptionId);
        final PollQuestionOptionRowMapper pollQuestionOptionRowMapper = new PollQuestionOptionRowMapper(this.pollQuestionOptionRepositoryWrapper);
        String qry = "SELECT "+pollQuestionOptionRowMapper.tableSchema();
        qry = qry + " WHERE po.id = ?";
        return this.jdbcTemplate.queryForObject(qry, pollQuestionOptionRowMapper, new Object[] { pollQuestionOptionId});
    }
    @Override
    public List<PollQuestionOptionData> fetchAll(Long pollQuestionId) {
        final PollQuestionOptionRowMapper pollQuestionOptionRowMapper = new PollQuestionOptionRowMapper(this.pollQuestionOptionRepositoryWrapper);
        String qry = "SELECT "+pollQuestionOptionRowMapper.tableSchema();
        qry = qry + " WHERE po.poll_question_id = ?";
        return this.jdbcTemplate.query(qry, pollQuestionOptionRowMapper, new Object[] { pollQuestionId});
    }
}
