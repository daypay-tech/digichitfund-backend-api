package com.daypaytechnologies.digichitfund.app.chart.data;

import lombok.Data;

@Data
public class MemberSubscriptionData {

    private String month;

    private long maleCount;

    private long femaleCount;

    public MemberSubscriptionData(final String month, final long maleCount, final long femaleCount) {
        this.month = month;
        this.maleCount = maleCount;
        this.femaleCount = femaleCount;
    }

    public static MemberSubscriptionData newInstance(final String month, final long maleCount, final long femaleCount) {
        return new MemberSubscriptionData(month, maleCount, femaleCount);
    }

}
