package com.ugo.usuaddr.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @Column(name = "role_name")
    @Enumerated(EnumType.STRING)
    private RoleName authority;

    @Override
    public String getAuthority() {
        return authority.name();
    }
}