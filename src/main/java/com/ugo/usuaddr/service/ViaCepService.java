package com.ugo.usuaddr.service;

import com.ugo.usuaddr.dto.ViaCepResponseDto;
import com.ugo.usuaddr.rest.ViaCepClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ViaCepService {

    @Autowired
    private ViaCepClient viaCepClient;

    public boolean isValid(String cep) {
        if (!cep.matches("\\d{8}"))
            return false;

        try {
            ViaCepResponseDto viaCepResponseDto = viaCepClient.buscarCep(cep);
            return !viaCepResponseDto.isErro();
        } catch (Exception e) {
            return false;
        }

    }

}
