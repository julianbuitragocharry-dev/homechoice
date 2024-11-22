package com.homechoice.model.user;

import com.homechoice.audit.model.Auditable;
import com.homechoice.audit.service.HistoryUserListener;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Entity representing a user in the system with their personal information details.
 */
@Entity
@EntityListeners(HistoryUserListener.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class User extends Auditable<String> implements UserDetails {

    /**
     * Unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

    /**
     * First name of the user.
     */
    @Column(name = "use_first_name", length = 50)
    private String firstName;

    /**
     * Last name of the user.
     */
    @Column(name = "use_last_name", length = 50)
    private String lastName;

    /**
     * Phone number of the user.
     */
    @Column(name = "use_phone", length = 25)
    private String phone;

    /**
     * Address of the user.
     */
    @Column(name = "use_address")
    private String address;

    /**
     * National Identification number of the user.
     */
    @Column(name = "use_nit", length = 25, unique = true)
    private String nit;

    /**
     * Email address of the user.
     */
    @Column(name = "use_email", length = 100, unique = true)
    private String email;

    /**
     * Password for the user account.
     */
    @Column(name = "use_password")
    private String password;

    /**
     * Roles assigned to the user.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "usr_use_id"),
            inverseJoinColumns = @JoinColumn(name = "usr_rol_id")
    )
    private List<Rol> roles;

    /**
     * Returns the authorities granted to the user.
     * @return a collection of granted authorities
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(rol -> new SimpleGrantedAuthority(rol.getRol()))
                .collect(Collectors.toList());
    }

    /**
     * Returns the username used to authenticate the user.
     * @return the email of the user
     */
    @Override
    public String getUsername() {
        return email;
    }

    /**
     * Indicates whether the user's account has expired.
     * @return true if the account is non-expired, false otherwise
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is locked or unlocked.
     * @return true if the account is non-locked, false otherwise
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials (password) has expired.
     * @return true if the credentials are non-expired, false otherwise
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled or disabled.
     * @return true if the user is enabled, false otherwise
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
