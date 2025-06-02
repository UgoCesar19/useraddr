package com.ugo.usuaddr.dto;

import com.ugo.usuaddr.validation.CepExists;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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

    @NotBlank(message = "Logradouro não pode estar em branco.")
    private String logradouro;

    @NotBlank(message = "Número não pode estar em branco.")
    private String numero;

    private String complemento;

    @NotBlank(message = "Bairro não pode estar em branco.")
    private String bairro;

    @NotBlank(message = "Cidade não pode estar em branco.")
    private String cidade;

    @NotBlank(message = "Estado não pode estar em branco.")
    private String estado;

    @CepExists
    @NotBlank(message = "CEP não pode estar em branco.")
    private String cep;

    private UsuarioDto usuario;

}
