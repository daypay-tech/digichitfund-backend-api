package com.daypaytechnologies.digichitfund.app.pollquestion.services;

import com.daypaytechnologies.digichitfund.infrastructure.exceptions.NavPulseApplicationException;
import com.daypaytechnologies.digichitfund.infrastructure.pagination.Page;
import com.daypaytechnologies.digichitfund.infrastructure.pagination.PaginationHelper;
import com.daypaytechnologies.digichitfund.app.poll.domain.Poll;
import com.daypaytechnologies.digichitfund.app.poll.domain.PollRepositoryWrapper;
import com.daypaytechnologies.digichitfund.app.pollanswer.services.PollAnswerReadPlatformService;
import com.daypaytechnologies.digichitfund.app.pollquestion.data.PollQuestionData;
import com.daypaytechnologies.digichitfund.app.pollquestion.data.PollQuestionDetailsData;
import com.daypaytechnologies.digichitfund.app.pollquestion.domain.PollQuestion;
import com.daypaytechnologies.digichitfund.app.pollquestion.domain.PollQuestionRepository;
import com.daypaytechnologies.digichitfund.app.pollquestion.domain.PollQuestionRepositoryWrapper;
import com.daypaytechnologies.digichitfund.app.pollquestion.rowmapper.PollQuestionRowMapper;
import com.daypaytechnologies.digichitfund.app.pollquestionoption.services.PollQuestionOptionReadPlatformService;
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
public class PollQuestionReadPlatformServiceImpl implements PollQuestionReadPlatformService {

    private final JdbcTemplate jdbcTemplate;

    private final PollQuestionRepositoryWrapper pollQuestionRepositoryWrapper;

    private final PaginationHelper<PollQuestionData> paginationHelper = new PaginationHelper<>();

    private final PollQuestionOptionReadPlatformService pollQuestionOptionReadPlatformService;

    private final PollAnswerReadPlatformService pollAnswerReadPlatformService;

    private final PollQuestionRepository pollQuestionRepository;

    private final PlatformSecurityContext platformSecurityContext;

    @Override
    public Page<PollQuestionData> fetchAll(Long categoryId, Long pollId, int page, int pageSize) {
        StringBuilder sbCategoryCondition = new StringBuilder();
        sbCategoryCondition.append(" pq.is_deleted IS NULL ");
        if(pollId != null) {
            sbCategoryCondition.append(" AND pq.poll_id =").append(pollId);
        }
        if(categoryId != null) {
            sbCategoryCondition.append(" AND c.id =").append(categoryId);
        }
        AdministrationUser administrationUser = this.platformSecurityContext.validateAdministrationUser();
        if(administrationUser != null) {
            sbCategoryCondition.append(" AND u.id =").append(administrationUser.getId());
        }
        final PollQuestionRowMapper pollQuestionRowMapper = new PollQuestionRowMapper();
        String qry = "SELECT " + pollQuestionRowMapper.tableSchema() +" WHERE "+sbCategoryCondition.toString()+" GROUP BY p.id,pq.id,c.id ORDER BY pq.id ASC offset ? limit ? ";
        String countQry = "SELECT count(pq) " + pollQuestionRowMapper.schema()+ " WHERE "+sbCategoryCondition.toString();
        final Page<PollQuestionData> pollQuestionDataPage = this.paginationHelper.fetchPage(this.jdbcTemplate, countQry, qry, pollQuestionRowMapper, page, pageSize);
        addQuestionOptions(pollQuestionDataPage.getData());
        return pollQuestionDataPage;
    }

    @Override
    public PollQuestionData fetchByPollQuestionId(Long pollQuestionId) {
        final PollQuestion pollQuestion = this.pollQuestionRepositoryWrapper.findOneWithNotFoundDetection(pollQuestionId);
        final PollQuestionRowMapper pollQuestionRowMapper = new PollQuestionRowMapper();
        String qry = "SELECT "+pollQuestionRowMapper.tableSchema();
        qry = qry + " WHERE pq.id = ?";
        AdministrationUser administrationUser = this.platformSecurityContext.validateAdministrationUser();
        if(administrationUser != null) {
            qry = qry +" AND u.id = "+administrationUser.getId();
        }
        try {
            PollQuestionData pollQuestionData = this.jdbcTemplate.queryForObject(qry, pollQuestionRowMapper, new Object[] { pollQuestion.getId()});
            if(pollQuestionData != null) {
                pollQuestionData.setOptions(this.pollQuestionOptionReadPlatformService.fetchAll(pollQuestionData.getId()));
            }
            return pollQuestionData;
        } catch (EmptyResultDataAccessException e) {
            throw new NavPulseApplicationException("No record found");
        }
    }

    private void addQuestionOptions(final List<PollQuestionData> pollQuestionDataList) {
        for(PollQuestionData pollQuestionData: pollQuestionDataList) {
            pollQuestionData.setOptions(this.pollQuestionOptionReadPlatformService.fetchAll(pollQuestionData.getId()));
        }
    }

    @Override
    public PollQuestionData fetchNextQuestion(Long pollId, Long questionId, Boolean answerForOtherProxyUser) {
        final PollQuestionRowMapper pollQuestionRowMapper = new PollQuestionRowMapper();
        String qry = "SELECT "+pollQuestionRowMapper.tableSchema();
        String subQuery = "SELECT MIN(pq1.id) FROM poll_question pq1 WHERE pq1.poll_id = "+pollId;
        if(questionId != null) {
            subQuery = subQuery + " AND pq1.id > "+questionId;
        }
        if(answerForOtherProxyUser != null && !answerForOtherProxyUser) {
            final String answeredQuestionIds = this.pollAnswerReadPlatformService.fetchAllMyAnsweredQuestionIdsAsStr(pollId);
            if(answeredQuestionIds != null) {
                subQuery = subQuery + " AND pq1.id NOT IN ("+answeredQuestionIds+")";
            }
        }
        subQuery = subQuery + " GROUP BY pq1.id ORDER BY pq1.id ASC LIMIT 1";
        log.debug("Sub query print {}", subQuery);
        qry = qry + " WHERE pq.poll_id = ? AND pq.id = ("+subQuery+") ";
        log.debug("final query ======= {}", qry);
        System.out.println("not answered questions "+qry);
        try {
            PollQuestionData pollQuestionData = this.jdbcTemplate.queryForObject(qry, pollQuestionRowMapper, new Object[] { pollId});
            if(pollQuestionData != null) {
                pollQuestionData.setOptions(this.pollQuestionOptionReadPlatformService.fetchAll(pollQuestionData.getId()));
            }
            return pollQuestionData;
        } catch (EmptyResultDataAccessException e) {
            throw new NavPulseApplicationException("End of Poll");
        }
    }

    @Override
    public PollQuestionData fetchPreviousQuestion(Long pollId, Long questionId, Boolean answerForOtherProxyUser) {
        final PollQuestionRowMapper pollQuestionRowMapper = new PollQuestionRowMapper();
        String qry = "SELECT "+pollQuestionRowMapper.tableSchema();
        String subQuery = "SELECT MAX(pq1.id) FROM poll_question pq1 WHERE pq1.poll_id = "+pollId;
        if(questionId != null) {
            subQuery = subQuery + " AND pq1.id < "+questionId;
        }
        if(answerForOtherProxyUser != null && !answerForOtherProxyUser) {
            final String answeredQuestionIds = this.pollAnswerReadPlatformService.fetchAllMyAnsweredQuestionIdsAsStr(pollId);
            if(answeredQuestionIds != null) {
                subQuery = subQuery + " AND pq1.id NOT IN ("+answeredQuestionIds+")";
            }
        }
        subQuery = subQuery +" GROUP BY pq1.id ORDER BY pq1.id DESC LIMIT 1";
        log.debug("Sub query print {}", subQuery);
        qry = qry + " WHERE pq.poll_id = ? AND pq.id = ("+subQuery+") ";
        log.debug("final query ======= {}", qry);
        System.out.println("not answered questions "+qry);
        try {
            PollQuestionData pollQuestionData = this.jdbcTemplate.queryForObject(qry, pollQuestionRowMapper, new Object[] { pollId});
            if(pollQuestionData != null) {
                pollQuestionData.setOptions(this.pollQuestionOptionReadPlatformService.fetchAll(pollQuestionData.getId()));
            }
            return pollQuestionData;
        } catch (EmptyResultDataAccessException e) {
            throw new NavPulseApplicationException("End of Poll");
        }
    }

    @Override
    public List<PollQuestionData> fetchAll(Long pollId) {
        final PollQuestionRowMapper pollQuestionRowMapper = new PollQuestionRowMapper();
        String qry = "SELECT "+pollQuestionRowMapper.tableSchema();
        qry = qry + " WHERE pq.poll_id = ?";
        AdministrationUser administrationUser = this.platformSecurityContext.validateAdministrationUser();
        if(administrationUser != null) {
            qry = qry +" AND u.id = "+administrationUser.getId();
        }
        List<PollQuestionData> pollQuestionDataList = this.jdbcTemplate.query(qry, pollQuestionRowMapper, new Object[] { pollId});
        addQuestionOptions(pollQuestionDataList);
        return pollQuestionDataList;
    }

    @Override
    public List<PollQuestionData> fetchAllNonAnsweredQuestions(Long pollId) {
        final PollQuestionRowMapper pollQuestionRowMapper = new PollQuestionRowMapper();
        String qry = "SELECT "+pollQuestionRowMapper.tableSchema();
        qry = qry + " WHERE pq.poll_id = ?";
        final String answeredQuestionIds = this.pollAnswerReadPlatformService.fetchAllMyAnsweredQuestionIdsAsStr(pollId);
        if(answeredQuestionIds != null) {
            qry = qry + " AND pq.id NOT IN ("+answeredQuestionIds+")";
        }
        log.debug("final query to get not answered questions {}", qry);
        System.out.println("not answered questions "+qry);
        return this.jdbcTemplate.query(qry, pollQuestionRowMapper, new Object[] { pollId});
    }

    @Override
    public Long totalQuestion(Long pollId) {
        return this.pollQuestionRepository.totalQuestionCount(pollId);
    }

    private final PollRepositoryWrapper pollRepositoryWrapper;

    @Override
    public PollQuestionDetailsData fetchQuestionDetails(Long pollId) {
        final Poll poll = this.pollRepositoryWrapper.findOneWithNotFoundDetection(pollId);
        final Long categoryId = poll.getCategory().getId();
        List<PollQuestionData> questions = fetchAll(poll.getId());
        return new PollQuestionDetailsData(questions, pollId, categoryId);
    }
}
