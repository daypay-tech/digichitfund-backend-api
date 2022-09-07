package com.daypaytechnologies.digichitfund.app.chart.data;

import lombok.Data;


@Data
public class AgeChartData {

    private Long maleCount;

    private Long femaleCount;

    private Long othersCount;

    private String range;

    private AgeChartData(final Long maleCount, final Long femaleCount, final Long othersCount, final String range) {
        this.maleCount = maleCount;
        this.femaleCount = femaleCount;
        this.othersCount = othersCount;
        this.range = range;
    }

    public static AgeChartData newInstance(final Long maleCount, final Long femaleCount, final Long othersCount, final String range) {
        return new AgeChartData(maleCount, femaleCount, othersCount, range);
    }
}
