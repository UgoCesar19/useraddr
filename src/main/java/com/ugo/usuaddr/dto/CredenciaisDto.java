package com.ugo.usuaddr.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CredenciaisDto {

    @NotBlank(message = "Email não pode estar em branco")
    @Email(message = "Email inválido")
    private String email;

    @NotBlank(message = "Senha não pode estar em branco")
    private String senha;

}
