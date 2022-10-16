package com.daypaytechnologies.digichitfund.app.member.domain;

import com.daypaytechnologies.digichitfund.app.core.entity.AbstractBaseEntity;
import com.daypaytechnologies.digichitfund.app.useraccount.domain.account.UserAccount;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "Member")
@Table(name = "member_info")
@Getter
@Setter
public class Member extends AbstractBaseEntity {

    @Id
    @GeneratedValue(generator = "digichit_member_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "digichit_member_gen", sequenceName = "digichit_member_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "display_name")
    private String displayName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_account_id", referencedColumnName = "id", nullable = false)
    @Basic(optional = false)
    private UserAccount userAccount;
}
