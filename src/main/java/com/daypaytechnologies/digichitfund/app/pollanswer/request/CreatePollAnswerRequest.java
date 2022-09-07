package com.daypaytechnologies.digichitfund.app.pollanswer.request;

import lombok.Data;

@Data
public class CreatePollAnswerRequest {

    private Long questionId;

    private Long optionId;

    @Data
    public static class PollQuestion {
        private Long questionId;
    }
    @Data
    public static class PollQuestionOption {
        private Long optionId;
    }
}
