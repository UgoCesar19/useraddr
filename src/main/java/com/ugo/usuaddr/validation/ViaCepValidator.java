package com.ugo.usuaddr.validation;

import com.ugo.usuaddr.service.ViaCepService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ViaCepValidator implements ConstraintValidator<CepExists, String> {

    @Autowired
    private ViaCepService viaCepService;

    @Override
    public boolean isValid(String cep, ConstraintValidatorContext context) {
        if (cep == null) return true;

        return viaCepService.isValid(cep);

    }

}
