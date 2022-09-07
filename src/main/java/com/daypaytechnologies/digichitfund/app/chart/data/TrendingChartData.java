package com.daypaytechnologies.digichitfund.app.chart.data;

import lombok.Data;

import java.util.Date;

@Data
public class TrendingChartData {

    private int memberCount;

    private Date date;

    public TrendingChartData(final int memberCount, final Date date) {
        this.memberCount = memberCount;
        this.date = date;
    }

    public static TrendingChartData newInstance(final int memberCount, final Date date) {
        return new TrendingChartData(memberCount, date);
    }
}
