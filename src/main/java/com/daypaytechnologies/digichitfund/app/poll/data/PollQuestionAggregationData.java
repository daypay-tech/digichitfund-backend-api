package com.daypaytechnologies.digichitfund.app.poll.data;

public class PollQuestionAggregationData extends PollData {

    private Long questionCount;

    public PollQuestionAggregationData(long id, String pollName, String categoryName, Long categoryId,
                                       final Long questionCount, final String firstName, final String lastName) {
        super(id, pollName, categoryName, categoryId, firstName, lastName);
        this.questionCount = questionCount;
    }

    public static PollQuestionAggregationData newInstance(final long id,
                                       final String pollName, final String categoryName,
                                       final Long categoryId, final Long questionCount,  final String firstName, final String lastName){
        return new PollQuestionAggregationData(id, pollName, categoryName, categoryId,questionCount, firstName, lastName);
    }

    public Long getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(Long questionCount) {
        this.questionCount = questionCount;
    }
}
