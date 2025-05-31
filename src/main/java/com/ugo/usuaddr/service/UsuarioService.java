package com.ugo.usuaddr.service;

import com.ugo.usuaddr.helper.UsuarioMapper;
import com.ugo.usuaddr.model.Usuario;
import com.ugo.usuaddr.dto.UsuarioDto;
import com.ugo.usuaddr.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }

    public UsuarioDto cadastrar(UsuarioDto usuarioDto) {
        Usuario usuario = usuarioRepository.save(usuarioMapper.toEntity(usuarioDto));
        return usuarioMapper.toDto(usuario);
    }

    public UsuarioDto alterar(UsuarioDto usuarioDto) {
        return null;
    }

    public String apagar(UsuarioDto usuarioDto) {
        return null;
    }

    public Page<UsuarioDto> getUsuarios(Pageable pageable) {
        return null;
    }

}
