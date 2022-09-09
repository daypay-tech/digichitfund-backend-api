package com.daypaytechnologies.digichitfund.app.user.domain.administrationuser;


import com.daypaytechnologies.digichitfund.app.user.domain.account.Account;
import com.daypaytechnologies.digichitfund.app.user.request.AdministrationUserSignUpRequest;
import lombok.Data;

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

@Entity(name = "AdministrationUser")
@Table(name = "administration_user")
@Data
public class AdministrationUser {

    @Id
    @GeneratedValue(generator = "administration_user_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "administration_user_gen", sequenceName = "administration_user_gen_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    @Basic(optional = false)
    private Account account;



    public static AdministrationUser from(final AdministrationUserSignUpRequest memberSignUpRequest,
                                          final Account account) {
        final AdministrationUser administrationUser = new AdministrationUser();
        administrationUser.setFirstName(memberSignUpRequest.getFirstName());
        administrationUser.setLastName(memberSignUpRequest.getLastName());
        administrationUser.setAccount(account);

        return administrationUser;
    }
}
