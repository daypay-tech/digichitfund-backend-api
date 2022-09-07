package com.daypaytechnologies.digichitfund.app.poll.data;

import lombok.Data;

@Data
public class PollData {

    private Long id;

    private String pollName;

    private String categoryName;

    private Long categoryId;

    private String firstName;

    private String lastName;

    public PollData(long id, final String pollName, final String categoryName,
                     final Long categoryId, final String firstName, final String lastName){
        this.id = id;
        this.pollName = pollName;
        this.categoryName = categoryName;
        this.categoryId = categoryId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static PollData newInstance(final long id,
                                        final String pollName, final String categoryName,
                                       final Long categoryId, final String firstName, final String lastName){
        return new PollData(id, pollName, categoryName, categoryId, firstName, lastName);
    }
}
