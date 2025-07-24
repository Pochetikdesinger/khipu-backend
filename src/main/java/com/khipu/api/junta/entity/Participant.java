package com.khipu.api.junta.entity;

import com.khipu.api.user.entity.User;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "participants")
public class Participant extends PanacheEntity {

    // Relación: El participante es un usuario del sistema.
    @ManyToOne(optional = false)
    public User user;

    // Relación: El participante pertenece a una junta.
    @ManyToOne(optional = false)
    public Junta junta;

    @Column(nullable = false)
    public Integer turnOrder; // El número de turno que le toca

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public PaymentStatus paymentStatus; // Estado del pago para el ciclo actual

    public enum PaymentStatus {
        PENDIENTE,
        PAGADO
    }
}