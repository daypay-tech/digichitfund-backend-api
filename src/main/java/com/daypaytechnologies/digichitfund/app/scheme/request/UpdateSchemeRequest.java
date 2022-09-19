package com.daypaytechnologies.digichitfund.app.scheme.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateSchemeRequest {
    private String schemeName;

    private Double totalAmount;

    private String totalMembers;

    private String calendar;

    private LocalDate startDate;

    private String startTime;
}
