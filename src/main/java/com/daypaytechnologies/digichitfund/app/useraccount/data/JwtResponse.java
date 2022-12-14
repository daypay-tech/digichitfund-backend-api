package com.daypaytechnologies.digichitfund.app.useraccount.data;

import lombok.Data;

@Data
public class JwtResponse {

    private String accessToken;

    private String tokenType = "Bearer";

    public JwtResponse(final String accessToken) {
        this.accessToken = accessToken;
    }
}
