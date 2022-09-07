package com.daypaytechnologies.digichitfund.app.pollquestion.request;

import lombok.Data;

import java.util.List;

@Data
public class UpdateQuestionRequest {

    private Long id;

    private String question;

    private List<UpdateQuestionOptionRequest> options;
}
