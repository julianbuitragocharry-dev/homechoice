package com.homechoice.persistence.entity.user;

import com.homechoice.persistence.entity.property.Property;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

    @Column(name = "use_first_name", length = 50)
    private String firstName;

    @Column(name = "use_last_name", length = 50)
    private String lastName;

    @Column(name = "use_phone", length = 25)
    private String phone;

    @Column(name = "use_address")
    private String address;

    @Column(name = "use_nit", length = 25, unique = true)
    private String nit;

    @Column(name = "use_email", length = 100, unique = true)
    private String email;

    @Column(name = "use_password")
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Property> properties;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "usr_use_id"),
            inverseJoinColumns = @JoinColumn(name = "usr_rol_id")
    )
    private List<Rol> roles;
}
