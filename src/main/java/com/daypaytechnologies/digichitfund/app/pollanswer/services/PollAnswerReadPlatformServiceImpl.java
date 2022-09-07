package com.daypaytechnologies.digichitfund.app.pollanswer.services;

import com.daypaytechnologies.digichitfund.infrastructure.pagination.PaginationHelper;
import com.daypaytechnologies.digichitfund.app.pollanswer.data.PollAnswerData;
import com.daypaytechnologies.digichitfund.app.pollanswer.domain.PollAnswer;
import com.daypaytechnologies.digichitfund.app.pollanswer.domain.PollAnswerRepository;
import com.daypaytechnologies.digichitfund.app.pollanswer.domain.PollAnswerRepositoryWrapper;
import com.daypaytechnologies.digichitfund.app.pollanswer.rowmapper.PollAnswerRowMapper;
import com.daypaytechnologies.digichitfund.app.user.domain.member.Member;
import com.daypaytechnologies.digichitfund.security.services.PlatformSecurityContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PollAnswerReadPlatformServiceImpl implements PollAnswerReadPlatformService{

    private final JdbcTemplate jdbcTemplate;

    private final PollAnswerRepositoryWrapper pollAnswerRepositoryWrapper;

    private final PaginationHelper<PollAnswerData> paginationHelper = new PaginationHelper<>();

    private final PlatformSecurityContext platformSecurityContext;

    private final PollAnswerRepository pollAnswerRepository;

//    @Override
//    public Page<PollAnswerData> fetchAll(int page, int pageSize) {
//        final PollAnswerRowMapper pollAnswerRowMapper = new PollAnswerRowMapper();
//        String qry = "SELECT " + pollAnswerRowMapper.tableSchema() +" WHERE offset ? limit ? ";
//        String countQry = "SELECT count(pa) " + pollAnswerRowMapper.schema();
//        return this.paginationHelper.fetchPage(this.jdbcTemplate, countQry, qry, pollAnswerRowMapper, page, pageSize);
//    }


    @Override
    public List<PollAnswerData> fetchAll() {
        final PollAnswerRowMapper pollAnswerRowMapper = new PollAnswerRowMapper();
        String qry = "SELECT "+pollAnswerRowMapper.tableSchema();
        return this.jdbcTemplate.query(qry, pollAnswerRowMapper);
    }
    @Override
    public PollAnswerData fetchByPollAnswerId(Long pollAnswerId) {
        final PollAnswer pollAnswer = this.pollAnswerRepositoryWrapper.findOneWithNotFoundDetection(pollAnswerId);
        final PollAnswerRowMapper pollAnswerRowMapper = new PollAnswerRowMapper();
        String qry = "SELECT "+pollAnswerRowMapper.tableSchema();
        qry = qry + " WHERE pa.id = ?";
        return this.jdbcTemplate.queryForObject(qry, pollAnswerRowMapper, new Object[] {pollAnswer.getId()});
    }

    @Override
    public String fetchAllMyAnsweredQuestionIdsAsStr(final Long pollId) {
        final Member member = this.platformSecurityContext.validateMember();
        final List<PollAnswer> pollAnswerList = this.pollAnswerRepository.findAllMyAnsweredQuestion(pollId, member.getId());
        List<Long> questionIds = new ArrayList<>();
        for(final PollAnswer pollAnswer: pollAnswerList) {
            questionIds.add(pollAnswer.getQuestion().getId());
        }
        if(questionIds.isEmpty()) {
            return null;
        }
        return StringUtils.join(questionIds, ',');
    }
}
