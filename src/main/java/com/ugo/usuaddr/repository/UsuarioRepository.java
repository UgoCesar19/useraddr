package com.ugo.usuaddr.repository;

import com.ugo.usuaddr.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    public Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);

}
