package com.daypaytechnologies.digichitfund.app.scheme.request;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
@Data
public class CreateSchemeRequest {
    private String schemeName;

    private Double totalAmount;

    private String totalMembers;

    private String calendar;

    private LocalDate startDate;

    private String startTime;

}
