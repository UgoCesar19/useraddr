package com.ugo.usuaddr.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ugo.usuaddr.dto.EnderecoDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class EnderecoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @WithMockUser("matuto")
    @Test
    void whenPersistirMethodCalledAndValidCepIsProvided_thenEnderecoDtoWithNewIdIsReturned() throws Exception {

        EnderecoDto input = EnderecoDto.builder()
                .logradouro("teste")
                .numero("teste")
                .complemento("teste")
                .bairro("teste")
                .cidade("teste")
                .estado("teste")
                .cep("01001000")
                .build();

        mockMvc.perform(post("/enderecos/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber());
    }

    @WithMockUser("matuto")
    @Test
    void whenPersistirMethodCalledAndInvalidCepIsProvided_thenBadRequestResponseIsReturned() throws Exception {

        EnderecoDto input = EnderecoDto.builder()
                .logradouro("teste")
                .numero("teste")
                .complemento("teste")
                .bairro("teste")
                .cidade("teste")
                .estado("teste")
                .cep("teste")
                .build();

        mockMvc.perform(post("/enderecos/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest());
    }

}
