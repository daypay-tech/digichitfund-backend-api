package com.daypaytechnologies.digichitfund.app.enumoptions;

import com.daypaytechnologies.digichitfund.app.data.EnumOptionData;

import java.util.ArrayList;
import java.util.List;

public enum CalendarTypeEnum {

    ENGLISH(1, "english", "English"), TAMIL(2, "tamil", "Tamil"),HINDI(3,"hindi","Hindi");

    private final Integer value;
    private final String code;
    private final String label;

    CalendarTypeEnum(final Integer value, final String code, final String label) {
        this.value = value;
        this.code = code;
        this.label = label;
    }

    public static CalendarTypeEnum fromInt(final Integer value) {
        CalendarTypeEnum enumeration = CalendarTypeEnum.ENGLISH;
        switch (value) {
            case 1:
                enumeration = CalendarTypeEnum.ENGLISH;
                break;
            case 2:
                enumeration = CalendarTypeEnum.TAMIL;
                break;
            case 3:
                enumeration = CalendarTypeEnum.HINDI;
                break;
        }
        return enumeration;
    }

    public static Integer getCodeByCode(String code) {
        Integer enumeration = 0;
        switch (code) {
            case "english":
                enumeration = 1;
                break;
            case "tamil":
                enumeration = 2;
                break;
            case "hindi":
                enumeration = 3;
                break;
        }
        return enumeration;
    }

    public Integer getValue() {
        return this.value;
    }

    public String getCode() {
        return this.code;
    }

    public String getLabel() {
        return this.label;
    }

    public boolean hasStateOf(final CalendarTypeEnum officeType) {
        return this.value.equals(officeType.getValue());
    }

    public static String getValueFromCode(final String code) {
        String enumeration = "";
        switch (code) {
            case "english":
                enumeration = CalendarTypeEnum.ENGLISH.getLabel();
                break;
            case "tamil":
                enumeration = CalendarTypeEnum.TAMIL.getLabel();
                break;

            case "hindi":
                enumeration = CalendarTypeEnum.HINDI.getLabel();
                break;
        }
        return enumeration;
    }

    public static List<EnumOptionData> getAllCalendarTypes(final CalendarTypeEnum[] calendarTypeEnums) {
        final List<EnumOptionData> optionDatas = new ArrayList<>();
        for (final CalendarTypeEnum enumData : calendarTypeEnums) {
            optionDatas.add(forwardCalendarEnum(enumData));
        }
        return optionDatas;
    }

    public static List<EnumOptionData> getAllCalendarTypes() {
        return getAllCalendarTypes(CalendarTypeEnum.values());
    }

    public static EnumOptionData forwardCalendarEnum(final CalendarTypeEnum officeTypeEnum) {
        final EnumOptionData optionData = new EnumOptionData(officeTypeEnum.getValue().longValue(), officeTypeEnum.getCode(),
                officeTypeEnum.getLabel());
        return optionData;
    }
}
