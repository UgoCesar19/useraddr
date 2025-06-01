package com.ugo.usuaddr.service;

import com.ugo.usuaddr.helper.UsuarioMapper;
import com.ugo.usuaddr.model.Usuario;
import com.ugo.usuaddr.dto.UsuarioDto;
import com.ugo.usuaddr.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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

    public Page<UsuarioDto> getUsuarios(Pageable pageable) {
        return usuarioRepository.findAll(pageable).map(u -> {
            return usuarioMapper.toDto(u);
        });
    }

    @Transactional
    public UsuarioDto cadastrar(UsuarioDto usuarioDto) {
        Usuario usuario = usuarioRepository.save(usuarioMapper.toSave(usuarioDto));
        return usuarioMapper.toDto(usuario);
    }

    @Transactional
    public UsuarioDto alterar(UsuarioDto usuarioDto) {
        Usuario usuario = usuarioRepository.save(usuarioMapper.toUpdate(usuarioDto,
                encontraUsuario(usuarioDto.getId())));
        return usuarioMapper.toDto(usuario);
    }

    @Transactional
    public void apagar(Long id) {
        usuarioRepository.delete(encontraUsuario(id));
    }

    private Usuario encontraUsuario(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> {
            return new EntityNotFoundException("Usuário com ID " + id + " não encontrado!");
        });
    }

}
