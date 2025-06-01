package com.ugo.usuaddr.helper;

import com.ugo.usuaddr.dto.EnderecoDto;
import com.ugo.usuaddr.dto.UsuarioDto;
import com.ugo.usuaddr.model.Endereco;
import com.ugo.usuaddr.model.Role;
import com.ugo.usuaddr.model.RoleName;
import com.ugo.usuaddr.model.Usuario;
import com.ugo.usuaddr.repository.RoleRepository;
import com.ugo.usuaddr.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class EnderecoMapper {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    public EnderecoDto toDto(Endereco endereco) {
        if (endereco == null) return null;

        return EnderecoDto.builder()
                .id(endereco.getId())
                .logradouro(endereco.getLogradouro())
                .numero(endereco.getNumero())
                .complemento(endereco.getComplemento())
                .bairro(endereco.getBairro())
                .cidade(endereco.getCidade())
                .estado(endereco.getEstado())
                .cep(endereco.getCep())
                .usuario(usuarioMapper.toDto(endereco.getUsuario()))
                .build();
    }

    public Endereco toEntity(EnderecoDto endereco, Long usuarioId) {
        if (endereco == null) return null;

        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(() -> {
            return new EntityNotFoundException("Usuário com o ID " + usuarioId + " não encontrado.");
        });

        return Endereco.builder()
                .id(endereco.getId())
                .logradouro(endereco.getLogradouro())
                .numero(endereco.getNumero())
                .complemento(endereco.getComplemento())
                .bairro(endereco.getBairro())
                .cidade(endereco.getCidade())
                .estado(endereco.getEstado())
                .cep(endereco.getCep())
                .usuario(usuario)
                .build();
    }

}
