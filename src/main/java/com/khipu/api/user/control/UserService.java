package com.khipu.api.user.control;

import com.khipu.api.shared.dto.UserLoginDto;
import com.khipu.api.shared.dto.UserRegistrationDto;
import com.khipu.api.user.entity.User;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotAuthorizedException;

@ApplicationScoped
public class UserService {

    @Transactional
    public User registerUser(UserRegistrationDto registrationDto) {
        if (User.find("email", registrationDto.email).firstResult() != null) {
            throw new BadRequestException("El email ya está en uso");
        }
        if (User.find("dni", registrationDto.dni).firstResult() != null) {
            throw new BadRequestException("El DNI ya está en uso");
        }

        User newUser = new User();
        newUser.name = registrationDto.name;
        newUser.email = registrationDto.email;
        newUser.dni = registrationDto.dni;

        // Ahora encriptamos la contraseña antes de guardarla.
        newUser.password = BcryptUtil.bcryptHash(registrationDto.password);

        newUser.persist();
        return newUser;
    }

    public User login(UserLoginDto loginDto) {
        User user = User.find("email", loginDto.email).firstResult();

        // Verificamos que el usuario exista y que la contraseña coincida con el hash guardado.
        if (user == null || !BcryptUtil.matches(loginDto.password, user.password)) {
            throw new NotAuthorizedException("Email o contraseña incorrectos");
        }

        return user;
    }
}