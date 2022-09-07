package com.daypaytechnologies.digichitfund.app.pollquestion.request;

import lombok.Data;

@Data
public class UpdateQuestionOptionRequest {

    private Long id;

    private String option;
}
