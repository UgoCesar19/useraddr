package com.ugo.usuaddr.rest;

import com.ugo.usuaddr.dto.ViaCepResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "viacep", url = "${via.cep.path}")
public interface ViaCepClient {

    @GetMapping("ws/{cep}/json")
    ViaCepResponseDto buscarCep(@PathVariable("cep") String cep);

}
