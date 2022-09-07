package com.daypaytechnologies.digichitfund.app.poll.domain;

import com.daypaytechnologies.digichitfund.app.category.domain.Category;
import com.daypaytechnologies.digichitfund.app.core.baseentity.AdministrationUserBaseEntity;
import com.daypaytechnologies.digichitfund.app.poll.request.CreatePollRequest;
import com.daypaytechnologies.digichitfund.app.user.domain.administrationuser.AdministrationUser;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "Poll")
@Table(name = "np_poll")
@Data
public class Poll extends AdministrationUserBaseEntity {

    @Id
    @GeneratedValue(generator = "np_poll_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "np_poll_gen", sequenceName = "np_poll_gen_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="category_id", nullable=false)
    @Basic(optional = false)
    private Category category;

    @Column(name = "poll_name")
    private String pollName;

    @Column(name = "is_deleted", nullable = true)
    @Basic(optional = true)
    private Boolean isDeleted;

    @Column(name = "deleted_on", nullable = true)
    @Basic(optional = true)
    private LocalDate deletedOn;

    @Column(name = "deleted_by", nullable = true)
    @Basic(optional = true)
    private Integer deletedBy;

    public static Poll from(final CreatePollRequest createPollRequest,
                            final Category category, final AdministrationUser administrationUser) {
        final Poll poll = new Poll();
        poll.setCategory(category);
        poll.setPollName(createPollRequest.getPollName());
        poll.setCreatedAt(LocalDateTime.now());
        poll.setUpdatedAt(LocalDateTime.now());
        poll.setCreatedBy(administrationUser);
        poll.setUpdatedBy(administrationUser);
        return poll;

    }

}
