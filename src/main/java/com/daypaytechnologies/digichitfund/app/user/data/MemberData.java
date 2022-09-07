package com.daypaytechnologies.digichitfund.app.user.data;

import lombok.Data;

@Data
public class MemberData {

    private Long id;

    private String organization;

    private String firstName;

    private String lastName;

    private String mobile;

    private String email;


    private MemberData(long id, final String organization, final String firstName,
                       final String lastName, final String mobile, final String email){

        this.id = id;
        this.organization = organization;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobile = mobile;
        this.email = email;

    }

    public static MemberData newInstance(final long id,
                                           final String organization, final String firstName,
                                           final String lastName, final String mobile, final String email){
        return new MemberData(id, organization, firstName, lastName, mobile, email);
    }
}
