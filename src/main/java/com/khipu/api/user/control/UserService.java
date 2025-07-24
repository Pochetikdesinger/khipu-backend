package com.khipu.api.user.control;

import com.khipu.api.shared.dto.UserRegistrationDto;
import com.khipu.api.user.entity.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;

@ApplicationScoped
public class UserService {

    @Transactional
    public User registerUser(UserRegistrationDto registrationDto) {
        // 1. Verificar si el email o DNI ya existen
        if (User.find("email", registrationDto.email).firstResult() != null) {
            throw new BadRequestException("El email ya está en uso");
        }
        if (User.find("dni", registrationDto.dni).firstResult() != null) {
            throw new BadRequestException("El DNI ya está en uso");
        }

        // 2. Crear el nuevo usuario
        User newUser = new User();
        newUser.name = registrationDto.name;
        newUser.email = registrationDto.email;
        newUser.dni = registrationDto.dni;

        // TODO: Encriptar la contraseña antes de guardarla. ¡MUY IMPORTANTE!
        // Por ahora, para el MVP, la guardamos en texto plano.
        newUser.password = registrationDto.password;

        // 3. Guardar el usuario en la base de datos
        newUser.persist();
        return newUser;
    }
}