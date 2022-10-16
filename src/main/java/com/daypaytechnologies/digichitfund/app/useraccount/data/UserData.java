package com.daypaytechnologies.digichitfund.app.useraccount.data;

import lombok.Data;

@Data
public class UserData {

    private String firstName;

    private String lastName;

    private String email;

    private String mobile;

    private Long userId;


    private UserData(final Long userId, final String firstName,
                     final String lastname, final String email, final String mobile) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastname;
        this.email = email;
        this.mobile = mobile;
    }

    public static UserData newInstance(final Long userId, final String firstName,
                                       final String lastname,
                                       final String email, final String mobile) {
        return new UserData(userId, firstName, lastname, email, mobile);
    }
}
