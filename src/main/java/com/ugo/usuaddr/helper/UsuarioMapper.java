package com.ugo.usuaddr.helper;

import com.ugo.usuaddr.dto.UsuarioDto;
import com.ugo.usuaddr.model.Role;
import com.ugo.usuaddr.model.RoleName;
import com.ugo.usuaddr.model.Usuario;
import com.ugo.usuaddr.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class UsuarioMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    public UsuarioDto toDto(Usuario usuario) {
        if (usuario == null) return null;

        return UsuarioDto.builder()
                .id(usuario.getId())
                .email(usuario.getEmail())
                .senha(usuario.getSenha())
                .nome(usuario.getNome())
                .perfis(usuario.getPerfis())
                .dataCriacao(usuario.getDataCriacao())
                .build();
    }

    public Usuario toSave(UsuarioDto dto) {
        if (dto == null) return null;

        Role role = roleRepository.findByAuthority(RoleName.ROLE_USER).orElseThrow(() -> {
            return new EntityNotFoundException("Perfil " + RoleName.ROLE_USER + " não encontrado!");
        });

        return Usuario.builder()
                .email(dto.getEmail())
                .nome(dto.getNome())
                .senha(passwordEncoder.encode(dto.getSenha()))
                .perfis(Set.of(role))
                .build();
    }

    public Usuario toUpdate(UsuarioDto dto, Usuario usuario) {
        Usuario usuarioAlterado = toSave(dto);
        usuarioAlterado.setId(dto.getId());
        usuarioAlterado.setSenha(usuario.getSenha());
        usuarioAlterado.setPerfis(dto.getPerfis());
        usuarioAlterado.setDataCriacao(usuario.getDataCriacao());
        return usuarioAlterado;
    }

}
