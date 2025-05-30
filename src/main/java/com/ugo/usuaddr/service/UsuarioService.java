package com.ugo.usuaddr.service;

import com.ugo.usuaddr.model.Role;
import com.ugo.usuaddr.model.RoleName;
import com.ugo.usuaddr.model.Usuario;
import com.ugo.usuaddr.model.UsuarioDto;
import com.ugo.usuaddr.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public UsuarioDto cadastrar(UsuarioDto usuarioDto) {

        Usuario usuario = usuarioRepository.save(Usuario.builder()
                .email(usuarioDto.getEmail())
                .password(passwordEncoder.encode(usuarioDto.getPassword()))
                .nome(usuarioDto.getNome())
                .authorities(Set.of(new Role(RoleName.ROLE_USER)))
                .build());

        return UsuarioDto.builder()
                .id(usuario.getId())
                .email(usuario.getEmail())
                .nome(usuario.getNome())
                .build();

    }
}
