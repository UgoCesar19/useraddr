package com.ugo.usuaddr.controller;

import com.ugo.usuaddr.dto.UsuarioDto;
import com.ugo.usuaddr.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioDto> cadastrar(@RequestBody @Valid UsuarioDto usuarioDto) {
        return new ResponseEntity<>(usuarioService.cadastrar(usuarioDto), HttpStatus.OK);
    }

}