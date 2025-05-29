package com.ugo.usuaddr.model.dto;

import com.ugo.usuaddr.model.Role;
import lombok.Data;

import java.util.Set;

@Data
public class UsuarioDto {

    private Long id;
    private String email;
    private String password;
    private String nome;
    private Set<Role> authorities;

}
