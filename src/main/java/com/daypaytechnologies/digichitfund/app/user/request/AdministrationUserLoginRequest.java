package com.daypaytechnologies.digichitfund.app.user.request;

import lombok.Data;

@Data
public class AdministrationUserLoginRequest extends LoginRequest {

    private Long orgId;
}
