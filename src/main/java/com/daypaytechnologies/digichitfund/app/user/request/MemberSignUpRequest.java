package com.daypaytechnologies.digichitfund.app.user.request;

import lombok.Data;

@Data
public class MemberSignUpRequest {

    private Long ageId;

    private String organization;

    private String mobile;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private Long genderId;
}
