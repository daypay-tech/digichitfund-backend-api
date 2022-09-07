package com.daypaytechnologies.digichitfund.app.proxyresponded.domain;

import com.daypaytechnologies.digichitfund.master.age.domain.Age;
import com.daypaytechnologies.digichitfund.master.gender.domain.Gender;
import com.daypaytechnologies.digichitfund.app.core.baseentity.AbstractBaseEntity;
import com.daypaytechnologies.digichitfund.app.user.domain.member.Member;
import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;


@Data
@Entity(name = "ProxyResponded")
@Table(name = "proxy_responded")
public class ProxyResponded extends AbstractBaseEntity {

    @Id
    @GeneratedValue(generator = "proxy_res_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "proxy_res_gen", sequenceName = "proxy_res_gen_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "gender_id", referencedColumnName = "id")
    private Gender gender;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "age_id", referencedColumnName = "id")
    private Age age;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "created_by_member_id", referencedColumnName = "id")
    private Member createdBy;

    public static ProxyResponded from(Member member, Age age, Gender gender){
        final ProxyResponded proxyResponded = new ProxyResponded();
        proxyResponded.setCreatedBy(member);
        proxyResponded.setAge(age);
        proxyResponded.setGender(gender);
        proxyResponded.setCreatedAt(LocalDateTime.now());
        proxyResponded.setUpdatedAt(LocalDateTime.now());
        return proxyResponded;
    }

}
