package com.daypaytechnologies.digichitfund.app.chart.data;

import lombok.Data;

@Data
public class QuestionOptionChartData {

    private String option;

    private Long count;

    private QuestionOptionChartData(final String option, final Long count) {
        this.option = option;
        this.count = count;
    }

    public static QuestionOptionChartData newInstance(final String option, final Long count) {
        return new QuestionOptionChartData(option, count);
    }
}
