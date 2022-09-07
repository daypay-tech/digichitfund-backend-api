package com.daypaytechnologies.digichitfund.app.chart.data;

import lombok.Data;

@Data
public class QuestionOptionAgeChartData {

    private String range;

    private Long count;

    public QuestionOptionAgeChartData(final String range, final Long count) {
        this.range = range;
        this.count = count;
    }

    public static QuestionOptionAgeChartData newInstance(final String range, final Long count) {
        return new QuestionOptionAgeChartData(range, count);
    }
}
