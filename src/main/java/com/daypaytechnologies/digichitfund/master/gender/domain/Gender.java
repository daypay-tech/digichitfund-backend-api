package com.daypaytechnologies.digichitfund.master.gender.domain;

import com.daypaytechnologies.digichitfund.master.gender.request.SaveGenderRequest;
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

@Entity(name = "Gender")
@Table(name = "gender_ref")
@Data
public class Gender extends AbstractBaseEntity {

    @Id
    @GeneratedValue(generator = "gender_ref_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "gender_ref_gen", sequenceName = "gender_ref_gen_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Basic(optional = false)
    @Column(name = "gender", unique = true, nullable = false)
    private String gender;

    public static Gender createNewInstance(final SaveGenderRequest request){
        final Gender gender1 = new Gender();
        gender1.setGender(request.getGender());
        return gender1;
    }
}
