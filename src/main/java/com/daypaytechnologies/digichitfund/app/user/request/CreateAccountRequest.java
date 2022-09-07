package com.daypaytechnologies.digichitfund.app.user.request;

import lombok.Data;

import java.util.List;

@Data
public class CreateAccountRequest {

    private String email;

    private String password;

    private String mobile;

    private List<Long> roles;
}
