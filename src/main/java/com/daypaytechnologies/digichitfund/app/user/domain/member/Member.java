package com.daypaytechnologies.digichitfund.app.user.domain.member;

import com.daypaytechnologies.digichitfund.master.age.domain.Age;
import com.daypaytechnologies.digichitfund.master.gender.domain.Gender;
import com.daypaytechnologies.digichitfund.app.user.domain.AbstractBaseUser;
import com.daypaytechnologies.digichitfund.app.user.request.MemberSignUpRequest;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

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
import javax.persistence.UniqueConstraint;
import java.time.LocalDate;

@Entity(name = "Member")
@Table(name = "member", uniqueConstraints = { @UniqueConstraint(columnNames = "mobile"),
        @UniqueConstraint(columnNames = "email")})
@Data
public class Member extends AbstractBaseUser {

    @Id
    @GeneratedValue(generator = "member_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "member_gen", sequenceName = "member_gen_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "organization")
    private String organization;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "gender_id", referencedColumnName = "id")
    private Gender gender;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "age_id", referencedColumnName = "id")
    private Age age;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "is_deleted", nullable = true)
    @Basic(optional = true)
    private Boolean isDeleted;

    @Column(name = "deleted_on", nullable = true)
    @Basic(optional = true)
    private LocalDate deletedOn;

    @Column(name = "deleted_by", nullable = true)
    @Basic(optional = true)
    private Integer deletedBy;

    public static Member from(final MemberSignUpRequest memberSignUpRequest, final Gender gender, final Age age,
                              final PasswordEncoder passwordEncoder) {
        final Member member = new Member();
        member.setOrganization(memberSignUpRequest.getOrganization());
        member.setGender(gender);
        member.setAge(age);
        member.setFirstName(memberSignUpRequest.getFirstName().trim());
        member.setLastName(memberSignUpRequest.getLastName().trim());
        member.setEmail(memberSignUpRequest.getEmail().trim());
        member.setMobile(memberSignUpRequest.getMobile().trim());
        final String encodedPassword = passwordEncoder.encode(memberSignUpRequest.getPassword().trim());
        member.setPassword(encodedPassword);
        return member;
    }
}
