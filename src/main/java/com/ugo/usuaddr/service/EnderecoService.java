package com.ugo.usuaddr.service;

import com.ugo.usuaddr.dto.EnderecoDto;
import com.ugo.usuaddr.helper.EnderecoMapper;
import com.ugo.usuaddr.model.Endereco;
import com.ugo.usuaddr.repository.EnderecoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private EnderecoMapper enderecoMapper;

    public Page<EnderecoDto> getEnderecos(Pageable pageable) {
        return enderecoRepository.findAll(pageable).map(e -> {
            return enderecoMapper.toDto(e);
        });
    }

    @Transactional
    public EnderecoDto persistir(EnderecoDto enderecoDto, Long usuarioId) {
        Endereco endereco = enderecoRepository.save(enderecoMapper.toEntity(enderecoDto, usuarioId));
        return enderecoMapper.toDto(endereco);
    }

    @Transactional
    public void apagar(Long id) {
        enderecoRepository.delete(encontraEndereco(id));
    }

    private Endereco encontraEndereco(Long id) {
        return enderecoRepository.findById(id).orElseThrow(() -> {
            return new EntityNotFoundException("Endereco com ID " + id + " não encontrado!");
        });
    }

}
