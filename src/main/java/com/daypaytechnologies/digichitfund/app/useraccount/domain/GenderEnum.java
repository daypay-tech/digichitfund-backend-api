package com.daypaytechnologies.digichitfund.app.useraccount.domain;

public enum GenderEnum {

    MALE("male", "Male"),

    OTHERS("others", "Others"),

    FEMALE("female", "Female");

    private final String code;

    private final String label;

    GenderEnum(final String code, final String label) {
        this.label = label;
        this.code = code;
    }

    public static GenderEnum fromCode(final String code) {
        GenderEnum enumeration = GenderEnum.OTHERS;
        switch (code) {
            case "male":
                enumeration = GenderEnum.MALE;
                break;
            case "female":
                enumeration = GenderEnum.FEMALE;
                break;
        }
        return enumeration;
    }
}
