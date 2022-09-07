package com.daypaytechnologies.digichitfund.app.pollquestion.request;

import lombok.Data;

import java.util.List;

@Data
public class CreatePollQuestionBulkRequest {

    private Long pollId;

    private List<CreateQuestionRequest> questions;

}
