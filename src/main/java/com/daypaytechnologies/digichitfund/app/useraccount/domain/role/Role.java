package com.daypaytechnologies.digichitfund.app.useraccount.domain.role;

import com.daypaytechnologies.digichitfund.app.useraccount.data.RoleData;
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
    @GeneratedValue(generator = "role_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "role_gen", sequenceName = "role_gen_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "role_name")
    private String name;

    @Column(name = "role_code")
    private String code;

    @Column(name = "is_user_defined")
    private boolean isUserDefined = false;

    public Role() {
        this.isUserDefined = false;
    }

    public Role(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public RoleData toData() {
        return RoleData.newInstance(this.id, this.name, this.code);
    }
}
