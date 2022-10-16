package com.daypaytechnologies.digichitfund.app.useraccount.data;

import lombok.Data;

@Data
public class MemberAuthResponseData {

    private Long id;

    private String organization;

    private String firstName;

    private String lastName;

    private String mobile;

    private String email;

    private JwtResponse jwtResponse;


    private MemberAuthResponseData(long id, final String organization, final String firstName,
                       final String lastName, final String mobile, final String email,
                       final JwtResponse jwtResponse){
        this.id = id;
        this.organization = organization;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobile = mobile;
        this.email = email;
        this.jwtResponse = jwtResponse;
    }

    public static MemberAuthResponseData newInstance(final long id,
                                         final String organization, final String firstName,
                                         final String lastName, final String mobile, final String email,
                                         final JwtResponse jwtResponse){
        return new MemberAuthResponseData(id, organization, firstName, lastName, mobile, email, jwtResponse);
    }
}
