package com.daypaytechnologies.digichitfund.app.chart.data;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class QuestionChartData {

    private Long id;

    private String question;

    private List<QuestionOptionChartData> optionChartDataList;

    private List<AgeChartData> ageResultData;
    
    private Map<String, List<QuestionOptionAgeChartData>> optionAgeChartDataList;

    public QuestionChartData(final Long id, final String question) {
        this.id = id;
        this.question = question;
    }

    public static QuestionChartData newInstance(final Long id, final String question) {
        return new QuestionChartData(id, question);
    }
}
