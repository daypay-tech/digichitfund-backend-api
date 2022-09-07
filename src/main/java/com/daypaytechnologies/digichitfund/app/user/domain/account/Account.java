package com.daypaytechnologies.digichitfund.app.user.domain.account;

import com.daypaytechnologies.digichitfund.app.user.domain.role.Role;
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

@Entity(name = "Account")
@Table(name = "account")
@Data
public class Account {

    @Id
    @GeneratedValue(generator = "account_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "account_gen", sequenceName = "account_gen_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "account_role",
            joinColumns = @JoinColumn(name = "account_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

    public static Account from(final String email, String mobile, String password,
                                          final List<Role> roleList,
                                          final PasswordEncoder passwordEncoder) {
        final Account account = new Account();
        account.setEmail(email);
        account.setMobile(mobile);
        final String encodedPassword = passwordEncoder.encode(password.trim());
        account.setPassword(encodedPassword);
        account.addAllRoles(roleList);
        return account;
    }

    public void addAllRoles(List<Role> roleList) {
        if(roles == null) {
            roles = new HashSet<>();
        }
        roles.addAll(roleList);
    }
}
