package com.peaksoft.accounting.db.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Entity
@Table(name = "users")
@Getter
@Setter
public class UserEntity  implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_seq", allocationSize = 1)
    private Long id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String password;
    private String businessName;
    private String address;
    private boolean isActive = true;
    private boolean enabled = true;
    private boolean deleted = false;
    @OneToOne(cascade = ALL)
    @JoinColumn(name = "businessArea_id")
    private BusinessAreaEntity businessArea;

    @CreatedDate
    @CreationTimestamp
    private LocalDateTime created;

    @ManyToMany(targetEntity = RoleEntity.class, cascade = {CascadeType.PERSIST, CascadeType.DETACH,
            CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private List<RoleEntity> roles;

    @UpdateTimestamp
    private LocalDateTime updateAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();

        for (RoleEntity role: roles){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return grantedAuthorities;
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
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", businessName='" + businessName + '\'' +
                ", address='" + address + '\'' +
                ", isActive=" + isActive +
                ", enabled=" + enabled +
                ", deleted=" + deleted +
                ", businessArea=" + businessArea +
                ", created=" + created +
                ", roles=" + roles +
                ", updateAt=" + updateAt +
                '}';
    }
}
