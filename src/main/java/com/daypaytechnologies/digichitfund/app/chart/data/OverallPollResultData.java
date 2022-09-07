package com.daypaytechnologies.digichitfund.app.chart.data;

import lombok.Data;

import java.util.List;

@Data
public class OverallPollResultData {

    private List<GenderChartData> overAllGenderChartData;

    private List<AgeChartData> overallAgeResultData;

    private List<QuestionChartData> questionChartDataList;
}
