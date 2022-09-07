package com.daypaytechnologies.digichitfund.app.pollanswer.data;

import lombok.Data;

@Data
public class PollAnswerData {

    private Long id;
    private Long questionId;
    private Long optionId;

    private String question;

    private String option;


    private PollAnswerData(long id, final Long questionId,
                           final Long optionId , final String question, final String option){

        this.id = id;
        this.questionId = questionId;
        this.optionId = optionId;
        this.question = question;
        this.option = option;

    }
    public static PollAnswerData newInstance(final long id,
                                       final Long questionId, final Long optionId,
                                             final String question, final String option) {
        return new PollAnswerData(id, questionId, optionId, question, option);
    }
}
