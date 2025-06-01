package com.ugo.usuaddr.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ugo.usuaddr.dto.UsuarioDto;
import com.ugo.usuaddr.repository.UsuarioRepository;
import com.ugo.usuaddr.service.TokenService;
import com.ugo.usuaddr.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerMockedTest {

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

    @WithMockUser("matuto")
    @Test
    void whenAlterarMethodIsCalled_thenReturnUsuarioDto() throws Exception {
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

        UsuarioDto input2 = UsuarioDto.builder()
                .id(1L)
                .email("matuto2@mail.com")
                .senha("matuto123")
                .nome("Matuto2")
                .build();

        UsuarioDto output2 = UsuarioDto.builder()
                .id(1L)
                .nome("Matuto2")
                .build();

        when(usuarioService.alterar(input2)).thenReturn(output2);

        mockMvc.perform(put("/usuarios")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input2)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Matuto2"));
    }

    @WithMockUser("matuto")
    @Test
    void whenGetUsuariosMethodIsCalled_thenReturnAnUsuarioDtoList() throws Exception {

        UsuarioDto input1 = UsuarioDto.builder().build();
        UsuarioDto input2 = UsuarioDto.builder().build();
        Page<UsuarioDto> output = new PageImpl<>(Arrays.asList(input1, input2));

        when(usuarioService.getUsuarios(any())).thenReturn(output);

        mockMvc.perform(get("/usuarios")
                        .with(csrf())
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "nome,asc"))
                .andExpect(status().isOk());

        ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);
        verify(usuarioService).getUsuarios(captor.capture());

        Pageable passedPageable = captor.getValue();
        assertEquals(0, passedPageable.getPageNumber());
        assertEquals(10, passedPageable.getPageSize());
        assertEquals(Sort.by("nome").ascending(), passedPageable.getSort());

    }

    @WithMockUser("matuto")
    @Test
    void whenApagarMethodIsCalled_thenReturnOk() throws Exception {
        Long id = 1L;

        doNothing().when(usuarioService).apagar(id);

        mockMvc.perform(delete("/usuarios/{id}", id)
                        .with(csrf())) // Required if CSRF is enabled
                .andExpect(status().isOk());

        verify(usuarioService, times(1)).apagar(id);

    }

}
