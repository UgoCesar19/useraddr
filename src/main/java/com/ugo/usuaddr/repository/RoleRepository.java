package com.ugo.usuaddr.repository;

import com.ugo.usuaddr.model.Role;
import com.ugo.usuaddr.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    public Optional<Role> findByAuthority(RoleName authority);

}
