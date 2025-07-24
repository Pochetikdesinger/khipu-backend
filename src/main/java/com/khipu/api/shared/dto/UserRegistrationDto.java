package com.khipu.api.shared.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// Usamos 'public' para los campos para mantenerlo simple.
// Las anotaciones @NotBlank, @Email, @Size validarán los datos automáticamente.
public class UserRegistrationDto {

    @NotBlank(message = "El nombre no puede estar vacío")
    public String name;

    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El formato del email no es válido")
    public String email;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    public String password;

    @NotBlank(message = "El DNI no puede estar vacío")
    @Size(min = 8, max = 8, message = "El DNI debe tener 8 caracteres")
    public String dni;
}