package com.peaksoft.accounting.db.entity;

import lombok.*;
import org.apache.catalina.User;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "roles")
public class RoleEntity implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roles_seq")
    @SequenceGenerator(name = "roles_gen", sequenceName = "roles_seq", allocationSize = 1)
    private Long role_id;
    private String name;

    @ManyToMany(targetEntity = UserEntity.class, mappedBy = "roles", cascade = CascadeType.ALL)
    private List<UserEntity> users;
    public void addUser(UserEntity user){
        if(this.users == null){
            this.users = new ArrayList<>();
        }
        this.users.add(user);
    }
    public static final String MY_ACCOUNT_ADMIN = "MY_ACCOUNT_ADMIN";
    public static final String MY_ACCOUNT_EDITOR = "MY_ACCOUNT_EDITOR";

    @Override
    public String getAuthority() {
        return name;
    }
}

