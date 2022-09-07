package com.daypaytechnologies.digichitfund.app.pollquestion.request;

import lombok.Data;

import java.util.List;

@Data
public class UpdateBulkQuestionRequest {

    private Long pollId;

    private List<UpdateQuestionRequest> questions;
}
