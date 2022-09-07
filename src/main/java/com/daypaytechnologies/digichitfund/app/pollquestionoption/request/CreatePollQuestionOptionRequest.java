package com.daypaytechnologies.digichitfund.app.pollquestionoption.request;

import lombok.Data;

import java.util.List;

@Data
public class CreatePollQuestionOptionRequest {

    private List<CreateQuestionOptionRequest> options;
}
