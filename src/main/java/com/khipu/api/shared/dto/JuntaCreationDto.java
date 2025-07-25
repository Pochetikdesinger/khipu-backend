package com.khipu.api.shared.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class JuntaCreationDto {
    @NotBlank(message = "El nombre de la junta no puede ir vacio")
    public  String name;

    @NotNull(message = "El monto por participante es obligatorio")
    @Min(value = 1, message = "El monto minimo es 1")
    public BigDecimal amountPerParticipant;

    @NotNull(message = "El número de participantes es obligatorio")
    @Min(value = 2, message = "La junta debe tener al menos 2 participantes")
    public Integer participantsCount;

    @NotBlank(message = "La frecuencia de pago es obligatoria (ej: MENSUAL, QUINCENAL)")
    public String frequency;
}
