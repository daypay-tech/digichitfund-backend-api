package com.daypaytechnologies.digichitfund.app.auth.request;

import lombok.Data;

@Data
public class UserLoginRequest {

    private String email;

    private String password;
}
