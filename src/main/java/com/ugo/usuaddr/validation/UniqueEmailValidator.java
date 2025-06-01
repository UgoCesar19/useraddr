package com.ugo.usuaddr.validation;

import com.ugo.usuaddr.dto.UsuarioDto;
import com.ugo.usuaddr.exception.UniqueEmailException;
import com.ugo.usuaddr.model.Usuario;
import com.ugo.usuaddr.repository.UsuarioRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UniqueEmailValidator  implements ConstraintValidator<UniqueEmail, UsuarioDto> {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public boolean isValid(UsuarioDto dto, ConstraintValidatorContext context) {
        if (dto.getEmail() == null) return true; // Let @Email handle it

        Optional<Usuario> existente = usuarioRepository.findByEmail(dto.getEmail());

        // If not found, email is free to use
        if (existente.isEmpty()) return true;

        // If found and belongs to the same user, allow it (it's an update)
        if (existente.get().getId().equals(dto.getId()))
            return true;
        else
            throw new UniqueEmailException("E-Mail em uso. Tente outro.");
    }

}
