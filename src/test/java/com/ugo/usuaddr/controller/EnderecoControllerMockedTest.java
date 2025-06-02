package com.ugo.usuaddr.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ugo.usuaddr.dto.EnderecoDto;
import com.ugo.usuaddr.repository.EnderecoRepository;
import com.ugo.usuaddr.repository.UsuarioRepository;
import com.ugo.usuaddr.rest.ViaCepClient;
import com.ugo.usuaddr.service.EnderecoService;
import com.ugo.usuaddr.service.TokenService;
import com.ugo.usuaddr.service.ViaCepService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EnderecoController.class)
public class EnderecoControllerMockedTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private EnderecoService enderecoService;

    @MockitoBean
    private TokenService tokenService;

    @MockitoBean
    private UsuarioRepository usuarioRepository;

    @MockitoBean
    private EnderecoRepository enderecoRepository;

    @MockitoBean
    private ViaCepService viaCepService;

    @WithMockUser("matuto")
    @Test
    void whenPersistirMethodIsCalled_thenReturnEnderecoDto() throws Exception {

        when(viaCepService.isValid(any())).thenReturn(true);

        EnderecoDto input = EnderecoDto.builder()
                .logradouro("teste")
                .numero("teste")
                .complemento("teste")
                .bairro("teste")
                .cidade("teste")
                .estado("teste")
                .cep("teste")
                .build();

        EnderecoDto output = EnderecoDto.builder()
                .id(1L)
                .build();

        when(enderecoService.persistir(input, 1L)).thenReturn(output);

        mockMvc.perform(post("/enderecos/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

    }

    @WithMockUser("matuto")
    @Test
    void whenPersistirMethodIsCalledUsingIdOnDto_thenReturnUpdatedEnderecoDto() throws Exception {

        when(viaCepService.isValid(any())).thenReturn(true);

        EnderecoDto input = EnderecoDto.builder()
                .id(2L)
                .logradouro("teste")
                .numero("teste")
                .complemento("teste")
                .bairro("teste")
                .cidade("teste")
                .estado("teste")
                .cep("teste")
                .build();

        EnderecoDto output = EnderecoDto.builder()
                .id(2L)
                .build();

        when(enderecoService.persistir(input, 2L)).thenReturn(output);

        mockMvc.perform(post("/enderecos/2")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2L));

    }

    @WithMockUser("matuto")
    @Test
    void whenGetEnderecosMethodIsCalled_thenReturnAnEnderecoDtoList() throws Exception {

        EnderecoDto input1 = EnderecoDto.builder().build();
        EnderecoDto input2 = EnderecoDto.builder().build();
        Page<EnderecoDto> output = new PageImpl<>(Arrays.asList(input1, input2));

        when(enderecoService.getEnderecos(any())).thenReturn(output);

        mockMvc.perform(get("/enderecos")
                        .with(csrf())
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "logradouro,asc"))
                .andExpect(status().isOk());

        ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);
        verify(enderecoService).getEnderecos(captor.capture());

        Pageable passedPageable = captor.getValue();
        assertEquals(0, passedPageable.getPageNumber());
        assertEquals(10, passedPageable.getPageSize());
        assertEquals(Sort.by("logradouro").ascending(), passedPageable.getSort());

    }

    @WithMockUser("matuto")
    @Test
    void whenApagarMethodIsCalled_thenReturnOk() throws Exception {
        Long id = 1L;

        doNothing().when(enderecoService).apagar(id);

        mockMvc.perform(delete("/enderecos/{id}", id)
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(enderecoService, times(1)).apagar(id);

    }

}
