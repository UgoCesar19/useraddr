package com.ugo.usuaddr.controller;

import com.ugo.usuaddr.dto.EnderecoDto;
import com.ugo.usuaddr.service.EnderecoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @GetMapping
    public ResponseEntity<Page<EnderecoDto>> getEnderecos(Pageable pageable) {
        return new ResponseEntity<>(enderecoService.getEnderecos(pageable), HttpStatus.OK);
    }

    @PostMapping(path = "/{usuarioId}")
    public ResponseEntity<EnderecoDto> persistir(@PathVariable("usuarioId") Long usuarioId,
                                                 @RequestBody @Valid EnderecoDto enderecoDto) {
        return new ResponseEntity<>(enderecoService.persistir(enderecoDto, usuarioId), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> apagar(@PathVariable("id") Long id) {
        enderecoService.apagar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}