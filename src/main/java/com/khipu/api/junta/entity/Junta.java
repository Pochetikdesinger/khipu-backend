package com.khipu.api.junta.entity;

import com.khipu.api.user.entity.User;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "juntas")
public class Junta extends PanacheEntity {

    @Column(nullable = false)
    public String name;

    @Column(nullable = false)
    public BigDecimal amountPerParticipant;

    @Column(nullable = false)
    public Integer participantsCount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public JuntaStatus status;

    // Relación: Muchas juntas pueden ser organizadas por un usuario.
    @ManyToOne(optional = false)
    public User organizer;

    // Relación: Una junta tiene muchos participantes.
    // 'mappedBy' indica que la relación la gestiona el campo 'junta' en la clase Participant.
    @OneToMany(mappedBy = "junta", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public List<Participant> participants;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    public LocalDateTime createdAt;

    // Enum para los estados de la junta
    public enum JuntaStatus {
        FORMANDO_GRUPO,
        ACTIVA,
        FINALIZADA
    }
}