package com.khipu.api.user.boundary;

import com.khipu.api.shared.dto.UserRegistrationDto;
import com.khipu.api.user.control.UserService;
import com.khipu.api.user.entity.User;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserService userService;

    @POST
    @Path("/register")
    public Response register(@Valid UserRegistrationDto registrationDto) {
        User newUser = userService.registerUser(registrationDto);
        // Devolvemos una respuesta 201 Created sin el cuerpo para seguridad
        return Response.status(Response.Status.CREATED).build();
    }

    // TODO: Implementar el endpoint de Login
    // @POST
    // @Path("/login")
    // public Response login(@Valid UserLoginDto loginDto) { ... }
}