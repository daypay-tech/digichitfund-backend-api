package com.daypaytechnologies.digichitfund.app.chart.services;

import com.daypaytechnologies.digichitfund.infrastructure.utils.DateUtils;
import com.daypaytechnologies.digichitfund.infrastructure.utils.RoleCheckerUtils;
import com.daypaytechnologies.digichitfund.master.age.domain.AgeRepository;
import com.daypaytechnologies.digichitfund.master.gender.domain.GenderRepository;
import com.daypaytechnologies.digichitfund.app.chart.data.*;
import com.daypaytechnologies.digichitfund.app.chart.rowmapper.*;
import com.daypaytechnologies.digichitfund.app.pollquestion.domain.PollQuestion;
import com.daypaytechnologies.digichitfund.app.pollquestion.domain.PollQuestionRepository;
import com.daypaytechnologies.digichitfund.app.pollquestion.domain.PollQuestionRepositoryWrapper;
import com.daypaytechnologies.digichitfund.app.pollquestionoption.domain.PollQuestionOption;
import com.daypaytechnologies.digichitfund.app.user.domain.administrationuser.AdministrationUser;
import com.daypaytechnologies.digichitfund.security.services.PlatformSecurityContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChartReadPlatformServiceImpl implements ChartReadPlatformService {

    private final JdbcTemplate jdbcTemplate;

    private final PollQuestionRepositoryWrapper pollQuestionRepositoryWrapper;

    private final ChartDataValidator chartDataValidator;

    private final GenderRepository genderRepository;

    private final AgeRepository ageRepository;

    @Override
    public List<TrendingChartData> fetchTrendingChartDateList(Long categoryId, Long pollId, String fromDate, String toDate) {
        final TrendingChartRowMapper trendingChartRowMapper = new TrendingChartRowMapper();
        String qry = trendingChartRowMapper.schema();
        if(categoryId != null || pollId != null || fromDate != null || toDate != null) {
            qry = qry + " WHERE ";
        }
        if(categoryId != null) {
            qry = qry + " c.id = "+categoryId;
        }
        if(pollId != null) {
            qry = qry + " AND p.id = "+pollId;
        }
        if(fromDate != null) {
            qry = qry + " AND DATE(a.answered_on) >= '"+fromDate + "'";
        }
        if(toDate != null) {
            qry = qry + " AND DATE(a.answered_on) <= '"+toDate + "'";
        }
        qry = qry + " GROUP BY DATE(a.answered_on) ORDER BY DATE(a.answered_on)";
        System.out.println("trending chart query ==========");
        System.out.println(qry);
        return jdbcTemplate.query(qry, trendingChartRowMapper);
    }

    @Override
    public List<MemberSubscriptionData> fetchMemberSubscriptionChartDataList(String year) {
        log.debug("Start of fetchMemberSubscriptionChartDataList(), year= {}", year);
        if(year == null) {
            year = DateUtils.getCurrentYear();
        }
        final String[] months = new String[]{"Jan", "Feb", "Mar", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        final MemberSubscriptionChartRowMapper memberSubscriptionChartRowMapper = new MemberSubscriptionChartRowMapper(year);
        String qry = memberSubscriptionChartRowMapper.schema();
        log.debug("final query ===== {} ", qry);
        List<MemberSubscriptionData> data = jdbcTemplate.query(qry, memberSubscriptionChartRowMapper);
        final List<MemberSubscriptionData> results = new ArrayList<>();
        for(String month: months) {
            boolean monthExist = false;
            for(MemberSubscriptionData memberSubscriptionData: data) {
                if(memberSubscriptionData.getMonth().equalsIgnoreCase(month)) {
                    monthExist = true;
                    results.add(memberSubscriptionData);
                    break;
                }
            }
            if(!monthExist) {
                MemberSubscriptionData memberSubscriptionData = new MemberSubscriptionData(month, 0, 0);
                results.add(memberSubscriptionData);
            }
        }
        log.debug("End of fetchMemberSubscriptionChartDataList(), year= {}", year);
        return results;
    }

    private final PollQuestionRepository pollQuestionRepository;

    @Override
    public List<QuestionChartData> fetchAllQuestionChartData(Long categoryId, Long pollId) {
        this.chartDataValidator.validateQuestionChartParameter(categoryId, pollId);
        final AdministrationUser administrationUser = this.platformSecurityContext.forceValidateAdministrationUser();
        final QuestionChartRowMapper pollQuestionRowMapper = new QuestionChartRowMapper(administrationUser);
        String qry = "SELECT "+pollQuestionRowMapper.tableSchema();
        qry = qry + " WHERE p.category_id = ? AND pq.poll_id = ?";
        if(!RoleCheckerUtils.isSuperAdmin(administrationUser)) {
            qry = qry + " AND u.id = "+administrationUser.getId();
        }
        log.debug("final query to get not answered questions {}", qry);
        System.out.println("final query "+qry);
        final List<QuestionChartData> questionChartDataList = this.jdbcTemplate.query(qry, pollQuestionRowMapper, new Object[] { categoryId, pollId});
        for(QuestionChartData questionChartData: questionChartDataList) {
            questionChartData.setOptionChartDataList(getAllQuestionOptionChartData(questionChartData.getId()));
            final List<AgeChartData> ageChartDataList = fetchAgeChartDateList(categoryId, pollId, questionChartData.getId());
            questionChartData.setOptionAgeChartDataList(fetchQuestionAgeCohortChartData(questionChartData.getId()));
            questionChartData.setAgeResultData(ageChartDataList);
        }
        return questionChartDataList;
    }

    private List<QuestionOptionChartData> getAllQuestionOptionChartData(Long questionId) {
        log.debug("Start of getAllQuestionOptionChartData() questionId {}", questionId);
        final QuestionOptionChartRowMapper questionOptionChartRowMapper = new QuestionOptionChartRowMapper(questionId);
        String qry = questionOptionChartRowMapper.schema();
        List<QuestionOptionChartData> questionOptionChartDataList = jdbcTemplate.query(qry, questionOptionChartRowMapper);
        log.debug("End of getAllQuestionOptionChartData() questionId {}", questionId);
        return questionOptionChartDataList;
    }

    private final PlatformSecurityContext platformSecurityContext;

    @Override
    public List<GenderChartData> fetchGenderChartDateList(Long categoryId, Long pollId) {
        log.debug("Start of fetchGenderChartDateList =======");
        final AdministrationUser administrationUser = this.platformSecurityContext.forceValidateAdministrationUser();
        final GenderChartRowMapper genderChartRowMapper = new GenderChartRowMapper(categoryId, pollId, administrationUser);
        String qry = genderChartRowMapper.schema();
        System.out.println("gender chart query ==========");
        System.out.println(qry);
        List<GenderChartData> genderChartDataList = jdbcTemplate.query(qry, genderChartRowMapper);
        log.debug("End of fetchGenderChartDateList =======");
        return genderChartDataList;
    }

    @Override
    public List<AgeChartData> fetchAgeChartDateList(Long categoryId, Long pollId) {
        return this.fetchAgeChartDateList(categoryId, pollId, null);
    }

    @Override
    public List<AgeChartData> fetchAgeChartDateList(Long categoryId, Long pollId, Long questionId) {
        log.debug("Start of fetchAgeChartDateList =======");
        final AdministrationUser administrationUser = this.platformSecurityContext.forceValidateAdministrationUser();
        final AgeChartRowMapper ageChartDataRowMapper = new AgeChartRowMapper(categoryId, pollId, questionId,administrationUser);
        String qry = ageChartDataRowMapper.schema();
        log.debug("End of fetchAgeChartDateList =======");
        return jdbcTemplate.query(qry, ageChartDataRowMapper);
    }

    @Override
    public Map<String, List<QuestionOptionAgeChartData>> fetchQuestionAgeCohortChartData(Long questionId) {
        log.debug("Start of fetchAgeChartDateList =======");
        final PollQuestion pollQuestion = this.pollQuestionRepositoryWrapper.findOneWithNotFoundDetection(questionId);
        final Map<String, List<QuestionOptionAgeChartData>> results = new HashMap<>();
        for(PollQuestionOption pollQuestionOption: pollQuestion.getOptions()) {
            final QuestionOptionByAgeChartRowMapper ageChartDataRowMapper = new QuestionOptionByAgeChartRowMapper(questionId, pollQuestionOption.getId());
            String qry = ageChartDataRowMapper.schema();
            System.out.println(qry);
            log.debug("End of fetchAgeChartDateList =======");
            final List<QuestionOptionAgeChartData> dataList = jdbcTemplate.query(qry, ageChartDataRowMapper);
            results.put(pollQuestionOption.getOptionName(), dataList);
        }
        return results;
    }
}
