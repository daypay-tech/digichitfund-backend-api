package com.daypaytechnologies.digichitfund.app.user.domain.role;

import com.daypaytechnologies.digichitfund.app.user.data.RoleData;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity(name = "Role")
@Table(name = "role", uniqueConstraints = @UniqueConstraint(columnNames = "role_code"))
@Data
public class Role {

    @Id
    @GeneratedValue(generator = "np_role_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "np_role_gen", sequenceName = "np_role_gen_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "role_name")
    private String name;

    @Column(name = "role_code")
    private String code;

    @Column(name = "is_user_defined", columnDefinition="tinyint(1) default 1")
    private boolean isUserDefined;

    public Role() {

    }

    public Role(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public RoleData toData() {
        return RoleData.newInstance(this.id, this.name, this.code);
    }
}