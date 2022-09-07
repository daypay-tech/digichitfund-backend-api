package com.daypaytechnologies.digichitfund.app.pollquestion.request;

import lombok.Data;

@Data
public class CreatePollQuestionRequest {

    private Long pollId;

    private CreateQuestionRequest question;


}

