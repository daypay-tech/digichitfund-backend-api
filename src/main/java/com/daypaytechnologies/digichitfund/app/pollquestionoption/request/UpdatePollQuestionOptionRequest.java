package com.daypaytechnologies.digichitfund.app.pollquestionoption.request;

import lombok.Data;

import java.util.List;

@Data
public class UpdatePollQuestionOptionRequest {

    private Long pollQuestionId;

    private List<String> option;
}
