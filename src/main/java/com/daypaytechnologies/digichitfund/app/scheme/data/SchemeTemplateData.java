package com.daypaytechnologies.digichitfund.app.scheme.data;

import com.daypaytechnologies.digichitfund.app.data.EnumOptionData;
import lombok.Data;

import java.util.List;
@Data

public class SchemeTemplateData {
    private List<EnumOptionData> calendarList;
    private SchemeTemplateData(final List<EnumOptionData> calendarList) {
        this.calendarList = calendarList;

    }
    public static SchemeTemplateData newInstance(final List<EnumOptionData> calendarList) {
        return new SchemeTemplateData(calendarList);
    }
}
