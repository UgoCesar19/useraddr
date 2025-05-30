package com.ugo.usuaddr.repository;

import com.ugo.usuaddr.model.Role;
import com.ugo.usuaddr.model.RoleName;
import com.ugo.usuaddr.model.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void itShouldInsertAndLoadAnUsuarioIntoTheUsersTable() {

        Role role = new Role(RoleName.ROLE_USER);

        Usuario usuario = new Usuario(null, "matuto@email.com", passwordEncoder.encode("matuto123"), "Matuto", Set.of(role), null);
        usuarioRepository.save(usuario);

        Optional<Usuario> usuarioEncontrado = usuarioRepository.findByEmail("matuto@email.com");

        assertTrue(usuarioEncontrado.isPresent());

    }

    @Test
    public void itShouldReturnTheAdminUserCreatedOnProjectInitialization() {

        Optional<Usuario> usuarioEncontrado = usuarioRepository.findByEmail("admin@email.com");

        System.out.println(usuarioEncontrado.toString());

        assertTrue(usuarioEncontrado.isPresent());

    }

}
