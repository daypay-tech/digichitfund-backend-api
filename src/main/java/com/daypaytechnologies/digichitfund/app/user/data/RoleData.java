package com.daypaytechnologies.digichitfund.app.user.data;

import lombok.Data;

@Data
public class RoleData {

    private Long roleId;

    private String name;

    private String code;


    public RoleData(final Long roleId, final String name, final String code) {
        this.roleId = roleId;
        this.name = name;
        this.code = code;
    }

    public static RoleData newInstance(final Long roleId, final String name, final String code) {
        return new RoleData(roleId, name, code);
    }
}
