package com.ugo.usuaddr.controller;

import com.ugo.usuaddr.dto.CredenciaisDto;
import com.ugo.usuaddr.model.Tokens;
import com.ugo.usuaddr.model.Usuario;
import com.ugo.usuaddr.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/autenticar")
    public ResponseEntity<Tokens> autenticar(@RequestBody @Valid CredenciaisDto credenciaisDto) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(credenciaisDto.getEmail(), credenciaisDto.getSenha());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        return ResponseEntity.ok(tokenService.gerarTokens((Usuario) authentication.getPrincipal()));
    }

    @PostMapping("/atualizar-autenticacao")
    public ResponseEntity<Tokens> atualizaAutenticacao(@RequestBody String refreshToken) {
        return ResponseEntity.ok(tokenService.renovarTokens(refreshToken));
    }
}