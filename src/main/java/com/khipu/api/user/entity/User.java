package com.khipu.api.user.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * Representa a un usuario en la base de datos.
 * Cada instancia de esta clase es una fila en la tabla 'khipu_users'.
 */
@Entity
@Table(name = "khipu_users")
public class User extends PanacheEntity {

    // PanacheEntity nos da el 'id' de tipo Long automáticamente.

    @Column(nullable = false)
    public String name;

    @Column(nullable = false, unique = true)
    public String email;

    @Column(nullable = false)
    public String password;

    @Column(nullable = false, unique = true, length = 8)
    public String dni;

    @Column(nullable = false)
    public Integer reputationScore = 700; // Puntaje inicial por defecto

    @CreationTimestamp // Hibernate asignará la fecha y hora actual al crear
    @Column(updatable = false, nullable = false)
    public LocalDateTime createdAt;

}