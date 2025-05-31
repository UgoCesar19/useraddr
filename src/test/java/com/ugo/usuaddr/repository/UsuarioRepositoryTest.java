package com.ugo.usuaddr.repository;

import com.ugo.usuaddr.model.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

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

        Usuario usuario = new Usuario(
                "matuto@email.com",
                passwordEncoder.encode("matuto123"),
                "Matuto");

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
