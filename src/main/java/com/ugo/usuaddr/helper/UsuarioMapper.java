package com.ugo.usuaddr.helper;

import com.ugo.usuaddr.dto.UsuarioDto;
import com.ugo.usuaddr.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UsuarioDto toDto(Usuario usuario) {
        if (usuario == null) return null;

        return UsuarioDto.builder()
                .id(usuario.getId())
                .email(usuario.getEmail())
                .nome(usuario.getNome())
                .perfis(usuario.getPerfis())
                .dataCriacao(usuario.getDataCriacao())
                .build();
    }

    public Usuario toEntity(UsuarioDto dto) {
        if (dto == null) return null;

        return Usuario.builder()
                .email(dto.getEmail())
                .nome(dto.getNome())
                .senha(passwordEncoder.encode(dto.getSenha()))
                .build();
    }

}
