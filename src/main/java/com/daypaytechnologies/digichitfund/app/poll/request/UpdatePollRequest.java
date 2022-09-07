package com.daypaytechnologies.digichitfund.app.poll.request;

import lombok.Data;

@Data
public class UpdatePollRequest {

    private String pollName;

    private Long categoryId;
}
