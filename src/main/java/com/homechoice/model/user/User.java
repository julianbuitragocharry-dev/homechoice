package com.homechoice.model.user;

import com.homechoice.audit.model.Auditable;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
@Schema(description = "Represents a user in the system with their personal information details.")
public class User extends Auditable<String> implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the user", example = "1")
    @Column(name = "user_id")
    private Integer id;

    @Column(name = "use_first_name", length = 50)
    @Schema(description = "First name of the user", example = "John")
    private String firstName;

    @Column(name = "use_last_name", length = 50)
    @Schema(description = "Last name of the user", example = "Doe")
    private String lastName;

    @Column(name = "use_phone", length = 25)
    @Schema(description = "Phone number of the user", example = "301 5610 703")
    private String phone;

    @Column(name = "use_address")
    @Schema(description = "Address of the user", example = "Calle 13 #22-42, Neiva")
    private String address;

    @Column(name = "use_nit", length = 25, unique = true)
    @Schema(description = "National Identification number of the user", example = "1077284243")
    private String nit;

    @Column(name = "use_email", length = 100, unique = true)
    @Schema(description = "Email address of the user", example = "john.doe@example.com")
    private String email;

    @Column(name = "use_password")
    @Schema(description = "Password for the user account", example = "password123")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "usr_use_id"),
            inverseJoinColumns = @JoinColumn(name = "usr_rol_id")
    )
    @Schema(description = "Roles assigned to the user")
    private List<Rol> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(rol -> new SimpleGrantedAuthority(rol.getRol()))
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
