package com.daypaytechnologies.digichitfund.app.chart.data;

import lombok.Data;

@Data
public class GenderChartData {

    private Long count;

    private String gender;

    private GenderChartData(final Long count, final String gender) {
        this.count = count;
        this.gender = gender;
    }

    public static GenderChartData newInstance(final Long count, final String gender) {
        return new GenderChartData(count, gender);
    }
}
