package com.daypaytechnologies.digichitfund.master.age.domain;

import com.daypaytechnologies.digichitfund.master.age.request.CreateAgeRequest;
import com.daypaytechnologies.digichitfund.app.core.baseentity.AbstractBaseEntity;
import lombok.Data;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name = "Age")
@Table(name = "age_ref")
@Data
public class Age extends AbstractBaseEntity {

    @Id
    @GeneratedValue(generator = "age_ref_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "age_ref_gen", sequenceName = "age_ref_gen_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Basic(optional = false)
    @Column(name = "range", nullable = false)
    private String range;

    public static Age createNewInstance(final CreateAgeRequest request){
        final Age age = new Age();
        age.setRange(request.getRange());
        return age;
    }
    public void updateAgeRange(final CreateAgeRequest request){
        this.setRange(request.getRange());
    }
}
