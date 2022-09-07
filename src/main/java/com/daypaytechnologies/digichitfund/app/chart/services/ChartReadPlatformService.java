package com.daypaytechnologies.digichitfund.app.chart.services;

import com.daypaytechnologies.digichitfund.app.chart.data.*;

import java.util.List;
import java.util.Map;

public interface ChartReadPlatformService {

    List<TrendingChartData> fetchTrendingChartDateList(Long categoryId, Long pollId, String fromDate, String toDate);

    List<MemberSubscriptionData> fetchMemberSubscriptionChartDataList(String year);

    List<QuestionChartData> fetchAllQuestionChartData(Long categoryId, Long pollId);

    List<GenderChartData> fetchGenderChartDateList(Long categoryId, Long pollId);

    List<AgeChartData> fetchAgeChartDateList(Long categoryId, Long pollId, Long questionId);

    List<AgeChartData> fetchAgeChartDateList(Long categoryId, Long pollId);

    Map<String, List<QuestionOptionAgeChartData>> fetchQuestionAgeCohortChartData(Long questionId);
}
