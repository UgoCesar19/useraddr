package com.ugo.usuaddr.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ugo.usuaddr.dto.UsuarioDto;
import com.ugo.usuaddr.repository.UsuarioRepository;
import com.ugo.usuaddr.service.TokenService;
import com.ugo.usuaddr.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UsuarioService usuarioService;

    @MockitoBean
    private TokenService tokenService;

    @MockitoBean
    private UsuarioRepository usuarioRepository;

    @WithMockUser("matuto")
    @Test
    void whenCadastrarMethodIsCalled_thenReturnUsuarioDto() throws Exception {
        UsuarioDto input = UsuarioDto.builder()
                .email("matuto@mail.com")
                .senha("matuto123")
                .nome("Matuto")
                .build();

        UsuarioDto output = UsuarioDto.builder()
                .id(1L)
                .build();

        when(usuarioService.cadastrar(any(UsuarioDto.class))).thenReturn(output);

        mockMvc.perform(post("/usuarios")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

    }
}
