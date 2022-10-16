package com.daypaytechnologies.digichitfund.app.member.data;

import lombok.Data;

@Data
public class MemberData {

    private final String displayName;

    public MemberData(final String displayName) {
        this.displayName = displayName;
    }

    public static MemberData newInstance(final String displayName) {
        return new MemberData(displayName);
    }
}
