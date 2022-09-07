package com.daypaytechnologies.digichitfund.app.pollquestion.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.daypaytechnologies.digichitfund.app.pollquestionoption.data.PollQuestionOptionData;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include. NON_NULL)
public class PollQuestionData {

    private Long id;

    private String question;

    private String pollName;

    private Long pollId;

    private List<PollQuestionOptionData> options;

    private String categoryName;

    private Long categoryId;


    private PollQuestionData(long id, final String question, final String pollName,
                             final Long pollId, final String categoryName, final Long categoryId) {
        this.id = id;
        this.question = question;
        this.pollName = pollName;
        this.pollId = pollId;
        this.categoryName = categoryName;
        this.categoryId = categoryId;
    }

    public static PollQuestionData newInstance(final long id,
                                               final String question, final String pollName,
                                               final Long pollId, final String categoryName,
                                               final Long categoryId){
        return new PollQuestionData(id, question, pollName, pollId, categoryName, categoryId);
    }
}
