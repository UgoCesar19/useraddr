package com.ugo.usuaddr.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoDto {

    private Long id;
    @NotBlank private String logradouro;
    @NotBlank private String numero;
    @NotBlank private String complemento;
    @NotBlank private String bairro;
    @NotBlank private String cidade;
    @NotBlank private String estado;
    @NotBlank private String cep;
    private UsuarioDto usuario;

}
