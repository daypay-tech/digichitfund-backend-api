package com.daypaytechnologies.digichitfund.app.pollquestion.request;

import com.daypaytechnologies.digichitfund.app.pollquestionoption.request.CreateQuestionOptionRequest;
import lombok.Data;

import java.util.List;

@Data
public class CreateQuestionRequest {

    private String question;

    private List<CreateQuestionOptionRequest> options;
}
