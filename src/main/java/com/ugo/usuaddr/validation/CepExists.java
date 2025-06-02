package com.ugo.usuaddr.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ViaCepValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CepExists {
    String message() default "CEP não encontrado";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
