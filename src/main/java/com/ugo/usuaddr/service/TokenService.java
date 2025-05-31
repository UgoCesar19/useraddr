package com.ugo.usuaddr.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ugo.usuaddr.exception.RefreshTokenException;
import com.ugo.usuaddr.model.Tokens;
import com.ugo.usuaddr.model.Usuario;
import com.ugo.usuaddr.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Value("${api.token.isuer}")
    private String tokenIsuer;

    @Value("${api.token.expiration}")
    private Integer tokenExpiration;

    @Value("${api.HMAC256.secret}")
    private String algorithmSecret;

    @Value("${api.HMAC256.refresh.secret}")
    private String algorithmRefreshSecret;

    public String gerarToken(Usuario usuario, String secret, Integer expiration) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer(tokenIsuer)
                    .withSubject(usuario.getUsername())
                    .withExpiresAt(expiracao(expiration))
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token JWT!");
        }
    }

    public String verificarToken(String token, String secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(tokenIsuer)
                    .build();

            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getSubject();
        } catch (JWTVerificationException exception) {
            throw new RefreshTokenException("Erro ao verificar token JWT!");
        }
    }

    private Instant expiracao(Integer minutos) {
        return LocalDateTime.now().plusMinutes(minutos).toInstant(ZoneOffset.of("-03:00"));
    }

    public Tokens gerarTokens(Usuario usuario) {
        return new Tokens(
                gerarToken(usuario, algorithmSecret, tokenExpiration),
                gerarToken(usuario, algorithmRefreshSecret, tokenExpiration * 2));
    }

    public Tokens renovarTokens(String refreshToken) {
        String email = verificarToken(refreshToken, algorithmRefreshSecret);
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow();
        return gerarTokens(usuario);
    }

    public String verificarTokenAcesso(String tokenAcesso) {
        return verificarToken(tokenAcesso, algorithmSecret);
    }

}
