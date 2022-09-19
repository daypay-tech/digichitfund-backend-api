package com.daypaytechnologies.digichitfund.app.scheme.data;

import com.daypaytechnologies.digichitfund.app.data.EnumOptionData;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class SchemeData {
    private long id;

    private String schemeName;

    private Double totalAmount;

    private String totalMembers;

    private String calendarCode;

    private List<EnumOptionData> calendarList;

    private String calendar;

    private LocalDate startDate;

    private String startTime;

    private  Boolean isDeleted;

    private SchemeData(long id, final String schemeName,final Double totalAmount,final String totalMembers, final String calendar,final String calendarCode,final  LocalDate startDate, final String startTime,final Boolean isDeleted){

        this.id = id;
        this.schemeName = schemeName;
        this.totalAmount = totalAmount;
        this.totalMembers = totalMembers;
        this.calendar = calendar;
        this.calendarCode= calendarCode;
        this.isDeleted = isDeleted;
        this.startDate = startDate;
        this.startTime = startTime;

    }

    public static SchemeData newInstance(final long id,
                                          final String schemeName ,final Double totalAmount ,final String totalMembers ,final String calendar, final String calendarCode ,final LocalDate startDate,final String startTime,final Boolean isDeleted){
        return new SchemeData(id, schemeName,totalAmount,totalMembers,calendar,calendarCode,startDate,startTime,isDeleted);
    }
}
