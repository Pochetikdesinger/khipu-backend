package com.khipu.api.user.boundary;

import com.khipu.api.shared.dto.UserLoginDto;
import com.khipu.api.shared.dto.UserRegistrationDto;
import com.khipu.api.user.control.UserService;
import com.khipu.api.user.entity.User;
import io.smallrye.jwt.build.Jwt; // <--- ¡Añade esta importación!
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.Duration;
import java.util.HashSet;

@Path("/api/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserService userService;

    @POST
    @Path("/register")
    public Response register(@Valid UserRegistrationDto registrationDto) {
        userService.registerUser(registrationDto);
        return Response.status(Response.Status.CREATED).build();
    }

    @POST
    @Path("/login")
    public Response login(@Valid UserLoginDto loginDto) {
        User user = userService.login(loginDto);

        // Si el login es exitoso, generamos un token JWT.
        String token = Jwt.issuer("https://khipu.api/issuer")
                .upn(user.email)
                .groups(new HashSet<>(java.util.Arrays.asList("User"))) // Rol del usuario
                .claim("userId", user.id) // <--- Dato extra en el token
                .expiresIn(Duration.ofHours(24))
                .sign(); // <--- Quarkus usa 'privatekey.pem' automáticamente para firmar

        // Devolvemos el token en la respuesta
        return Response.ok(java.util.Collections.singletonMap("token", token)).build();
    }
}