package com.ugo.usuaddr.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

}
