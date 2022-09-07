package com.daypaytechnologies.digichitfund.app.poll.request;

import lombok.Data;

@Data
public class CreatePollRequest {

    private String pollName;

    private Long categoryId;
}
