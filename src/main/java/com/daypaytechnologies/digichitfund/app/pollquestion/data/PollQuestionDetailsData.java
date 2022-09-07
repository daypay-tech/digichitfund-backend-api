package com.daypaytechnologies.digichitfund.app.pollquestion.data;

import lombok.Data;

import java.util.List;

@Data
public class PollQuestionDetailsData {

    private List<PollQuestionData> questions;

    private Long pollId;

    private Long categoryId;

    public PollQuestionDetailsData(final List<PollQuestionData> questions, final Long pollId, final Long categoryId) {
        this.questions = questions;
        this.pollId = pollId;
        this.categoryId = categoryId;
    }
}
