package com.daypaytechnologies.digichitfund.app.core.baseentity;

import com.daypaytechnologies.digichitfund.app.user.domain.administrationuser.AdministrationUser;
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
public class AdministrationUserBaseEntity extends AbstractBaseEntity {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "created_by_user_id", referencedColumnName = "id", nullable = false)
    @Basic(optional = false)
    private AdministrationUser createdBy;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "updated_by_user_id", referencedColumnName = "id", nullable = false)
    @Basic(optional = false)
    private AdministrationUser updatedBy;

}
