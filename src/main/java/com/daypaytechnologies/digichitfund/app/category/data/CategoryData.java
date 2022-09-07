package com.daypaytechnologies.digichitfund.app.category.data;

import lombok.Data;

@Data
public class CategoryData {

    private Long id;
    private String categoryName;
    private String createdAt;

    private String firstName;

    private String lastName;


    private CategoryData(long id, final String categoryName, final String createdAt,final String firstName, final String lastName){

        this.id = id;
        this.categoryName = categoryName;
        this.createdAt = createdAt;
        this.firstName = firstName;
        this.lastName = lastName;

    }

    public static CategoryData newInstance(final long id,
                                       final String categoryName, final String createdAt, final String firstName, final String lastName){
        return new CategoryData(id, categoryName, createdAt, firstName, lastName);
    }
}
