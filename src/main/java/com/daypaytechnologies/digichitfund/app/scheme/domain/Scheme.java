package com.daypaytechnologies.digichitfund.app.scheme.domain;

import com.daypaytechnologies.digichitfund.app.scheme.request.CreateSchemeRequest;
import com.daypaytechnologies.digichitfund.app.utils.TimeUtils;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity(name = "Scheme")
@Table(name = "scheme")
@Getter
@Setter
public class Scheme {
    @Id
    @GeneratedValue(generator = "digichit_scheme_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "digichit_scheme_gen", sequenceName = "digichit_scheme_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "scheme_name", nullable = false)
    @Basic(optional = false)
    private String schemeName;

    @Column(name = "total_amount", nullable = false)
    @Basic(optional = false)
    private Double totalAmount;

    @Column(name = "total_members", nullable = false)
    @Basic(optional = false)
    private String totalMembers;

    @Column(name = "calendar_enum", nullable = false)
    @Basic(optional = false)
    private String Calendar;

    @Column(name = "start_date", nullable = false)
    @Basic(optional = false)
    private LocalDate startDate;

    @Column(name = "start_time", nullable = false,columnDefinition = "TIME")
    @Basic(optional = false)
    private LocalTime startTime;

    @Column(name = "is_deleted", nullable = true)
    @Basic(optional = true)
    private Boolean isDeleted;

    @Column(name = "deleted_on", nullable = true)
    @Basic(optional = true)
    private LocalDate deletedOn;

    @Column(name = "deleted_by", nullable = true)
    @Basic(optional = true)
    private int deletedBy;

    public static Scheme from(final CreateSchemeRequest createSchemeRequest, String calendar) {
        final Scheme scheme = new Scheme();
        scheme.setSchemeName(createSchemeRequest.getSchemeName());
        scheme.setTotalAmount(createSchemeRequest.getTotalAmount());
        scheme.setTotalMembers(createSchemeRequest.getTotalMembers());
        scheme.setCalendar(calendar);
        scheme.setStartDate(createSchemeRequest.getStartDate());
        scheme.setStartTime(TimeUtils.convert12to24TimeFormat(createSchemeRequest.getStartTime()));
        return scheme;

    }
}
