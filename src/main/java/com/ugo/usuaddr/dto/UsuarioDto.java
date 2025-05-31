package com.ugo.usuaddr.dto;

import com.ugo.usuaddr.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDto {

    private Long id;

    @NotBlank(message = "Email não pode estar em branco")
    @Email(message = "Email inválido")
    private String email;

    @NotBlank(message = "Senha não pode estar em branco")
    private String senha;

    @NotBlank(message = "Nome não pode estar em branco")
    private String nome;

    private LocalDateTime dataCriacao;

    private Set<Role> perfis;

}
