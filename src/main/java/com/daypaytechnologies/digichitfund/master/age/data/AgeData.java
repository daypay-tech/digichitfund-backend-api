package com.daypaytechnologies.digichitfund.master.age.data;

import lombok.Data;

@Data
public class AgeData {

    private Long ageId;

    private String range;

    public AgeData(final Long ageId, final String range) {
        this.ageId = ageId;
        this.range = range;
    }

    public static AgeData newInstance(final Long ageId, final String range) {
        return new AgeData(ageId, range);
    }
}
