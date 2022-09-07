package com.daypaytechnologies.digichitfund.app.pollquestionoption.data;

import lombok.Data;

@Data
public class PollQuestionOptionData {

    private Long id;

    private String option;

    private PollQuestionOptionData(long id, final String option){
        this.id = id;
        this.option = option;
    }

    public static PollQuestionOptionData newInstance(final long id, final String option){
        return new PollQuestionOptionData(id, option);
    }
}
