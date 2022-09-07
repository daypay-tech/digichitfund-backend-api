package com.daypaytechnologies.digichitfund.master.gender.data;

import lombok.Data;

@Data
public class GenderData {

    private Long genderId;

    private String gender;

    public GenderData(final Long genderId, final String gender) {
        this.genderId = genderId;
        this.gender = gender;
    }

    public static GenderData newInstance(final Long genderId, final String gender) {
        return new GenderData(genderId, gender);
    }
}
