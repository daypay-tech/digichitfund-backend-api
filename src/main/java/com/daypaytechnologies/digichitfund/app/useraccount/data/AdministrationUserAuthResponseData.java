package com.daypaytechnologies.digichitfund.app.useraccount.data;

import lombok.Data;

import java.util.List;

@Data
public class AdministrationUserAuthResponseData {

    private String firstName;

    private String lastName;

    private String email;

    private String mobile;

    private Long userId;

    private List<RoleData> roles;

    private JwtResponse jwtResponse;


    private AdministrationUserAuthResponseData(final Long userId, final String firstName,
                                               final String lastname, final List<RoleData> roles, final JwtResponse jwtResponse) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastname;
        this.roles = roles;
        this.jwtResponse = jwtResponse;
    }

    public static AdministrationUserAuthResponseData newInstance(final Long userId, final String firstName,
                                                                 final String lastname,
                                                                 final String email, final String mobile,
                                                                 final List<RoleData> roles, final JwtResponse jwtResponse) {
        return new AdministrationUserAuthResponseData(userId, firstName, lastname, roles, jwtResponse);
    }
}
