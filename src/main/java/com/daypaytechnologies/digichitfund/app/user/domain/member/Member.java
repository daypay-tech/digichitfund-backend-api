package com.daypaytechnologies.digichitfund.app.user.domain.member;

import com.daypaytechnologies.digichitfund.app.user.domain.AbstractBaseUser;
import com.daypaytechnologies.digichitfund.app.user.request.MemberSignUpRequest;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDate;
@Entity(name = "Member")
@Table(name = "member", uniqueConstraints = { @UniqueConstraint(columnNames = "mobile"),
        @UniqueConstraint(columnNames = "email")})
@Data

public class Member extends AbstractBaseUser

  {

        @Id
        @GeneratedValue(generator = "member_gen", strategy = GenerationType.SEQUENCE)
        @SequenceGenerator(name = "member_gen", sequenceName = "member_gen_seq", allocationSize = 1)
        @Column(name = "id")
        private Long id;

        @Column(name = "organization")
        private String organization;



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

        public static Member from(final MemberSignUpRequest memberSignUpRequest,
                                  final PasswordEncoder passwordEncoder) {
            final Member member = new Member();
            member.setOrganization(memberSignUpRequest.getOrganization());

            member.setFirstName(memberSignUpRequest.getFirstName().trim());
            member.setLastName(memberSignUpRequest.getLastName().trim());
            member.setEmail(memberSignUpRequest.getEmail().trim());
            member.setMobile(memberSignUpRequest.getMobile().trim());
            final String encodedPassword = passwordEncoder.encode(memberSignUpRequest.getPassword().trim());
            member.setPassword(encodedPassword);
            return member;
        }
    }


