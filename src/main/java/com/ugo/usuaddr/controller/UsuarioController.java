package com.ugo.usuaddr.controller;

import com.ugo.usuaddr.dto.UsuarioDto;
import com.ugo.usuaddr.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<Page<UsuarioDto>> getUsuarios(Pageable pageable) {
        return new ResponseEntity<>(usuarioService.getUsuarios(pageable), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UsuarioDto> cadastrar(@RequestBody @Valid UsuarioDto usuarioDto) {
        return new ResponseEntity<>(usuarioService.cadastrar(usuarioDto), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<UsuarioDto> alterar(@RequestBody @Valid UsuarioDto usuarioDto) {
        return new ResponseEntity<>(usuarioService.alterar(usuarioDto), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> apagar(@PathVariable("id") Long id) {
        usuarioService.apagar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}