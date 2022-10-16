package com.daypaytechnologies.digichitfund.app.staff.data;

import lombok.Data;

@Data
public class StaffData {

    private final String displayName;

    public StaffData(final String displayName) {
        this.displayName = displayName;
    }

    public static StaffData newInstance(String displayName) {
        return new StaffData(displayName);
    }
}
