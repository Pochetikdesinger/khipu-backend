package com.khipu.api.shared.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserLoginDto {

    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El formato del email no es válido")
    public String email;

    @NotBlank(message = "La contraseña no puede estar vacía")
    public String password;
}