package com.ugo.usuaddr.controller;

import com.ugo.usuaddr.model.dto.UsuarioDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @GetMapping("/public/hello")
    public String publicHello() {
        return "Hello from public endpoint!";
    }

    @GetMapping("/secure/hello")
    public String secureHello() {
        return "Hello from secured endpoint!";
    }

    @PostMapping
    public ResponseEntity<UsuarioDto> cadastrarUsuario() {
        return null;
    }

}