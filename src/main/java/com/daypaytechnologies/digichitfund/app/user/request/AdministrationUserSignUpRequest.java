package com.daypaytechnologies.digichitfund.app.user.request;

import lombok.Data;

@Data
public class AdministrationUserSignUpRequest {

    private String firstName;

    private String lastName;

    private Long orgId;

    private CreateAccountRequest account;
}
