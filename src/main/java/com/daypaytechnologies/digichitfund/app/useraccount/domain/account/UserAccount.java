package com.daypaytechnologies.digichitfund.app.useraccount.domain.account;

import com.daypaytechnologies.digichitfund.app.useraccount.domain.role.Role;
import com.daypaytechnologies.digichitfund.app.useraccount.request.CreateUserAccountRequest;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "UserAccount")
@Table(name = "user_account")
@Data
public class UserAccount {

    @Id
    @GeneratedValue(generator = "user_account_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "user_account_gen", sequenceName = "user_account_gen_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_account_role",
            joinColumns = @JoinColumn(name = "user_account_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

    public static UserAccount from(final CreateUserAccountRequest createUserAccountRequest,
                                   final List<Role> roleList,
                                   final PasswordEncoder passwordEncoder) {
        final UserAccount userAccount = new UserAccount();
        userAccount.setEmail(createUserAccountRequest.getEmail());
        userAccount.setMobile(createUserAccountRequest.getMobile());
        final String encodedPassword = passwordEncoder.encode(createUserAccountRequest.getPassword().trim());
        userAccount.setPassword(encodedPassword);
        userAccount.addAllRoles(roleList);
        return userAccount;
    }

    public void addAllRoles(List<Role> roleList) {
        if(roles == null) {
            roles = new HashSet<>();
        }
        roles.addAll(roleList);
    }
}
