package com.daypaytechnologies.digichitfund.app.core.entity;

import com.daypaytechnologies.digichitfund.app.useraccount.domain.account.UserAccount;
import com.daypaytechnologies.digichitfund.app.useraccount.domain.role.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

@MappedSuperclass
@Getter
@Setter
public class UserAccountBaseEntity extends AbstractBaseEntity {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "created_by_user_id", referencedColumnName = "id", nullable = false)
    @Basic(optional = false)
    private UserAccount createdBy;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "updated_by_user_id", referencedColumnName = "id", nullable = false)
    @Basic(optional = false)
    private UserAccount updatedBy;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "created_by_role_id", referencedColumnName = "id", nullable = false)
    @Basic(optional = false)
    private Role createdByRole;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "updated_by_role_id", referencedColumnName = "id", nullable = false)
    @Basic(optional = false)
    private Role updatedByRole;

    public void setUp(final UserAccount userAccount, final Role role) {
        this.createdBy = userAccount;
        this.updatedBy = userAccount;
        this.createdByRole = role;
        this.updatedByRole = role;
    }
}
