package com.khipu.api.junta.control;

import com.khipu.api.junta.entity.Junta;
import com.khipu.api.junta.entity.Participant;
import com.khipu.api.shared.dto.JuntaCreationDto;
import com.khipu.api.user.entity.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

import java.util.ArrayList;

@ApplicationScoped
public class JuntaService {

    // --- MÉTODO PARA CREAR UNA JUNTA ---
    @Transactional
    public Junta createJunta(JuntaCreationDto dto, Long organizerId) {
        // 1. Busca al usuario organizador en la BD
        User organizer = User.findById(organizerId);
        if (organizer == null) {
            throw new NotFoundException("Organizador no encontrado");
        }

        // 2. Crea la nueva entidad Junta
        Junta nuevaJunta = new Junta();
        nuevaJunta.name = dto.name;
        nuevaJunta.amountPerParticipant = dto.amountPerParticipant;
        nuevaJunta.participantsCount = dto.participantsCount;
        nuevaJunta.status = Junta.JuntaStatus.FORMANDO_GRUPO;
        nuevaJunta.organizer = organizer;
        nuevaJunta.participants = new ArrayList<>();

        // 3. El organizador es automáticamente el primer participante
        Participant organizerAsParticipant = new Participant();
        organizerAsParticipant.user = organizer;
        organizerAsParticipant.junta = nuevaJunta;
        organizerAsParticipant.turnOrder = 1; // Por defecto, el organizador es el primero
        organizerAsParticipant.paymentStatus = Participant.PaymentStatus.PAGADO; // El primer pago se asume hecho

        // 4. Añade al organizador a la lista de participantes de la junta
        nuevaJunta.participants.add(organizerAsParticipant);

        // 5. Guarda todo en la base de datos
        nuevaJunta.persist();

        return nuevaJunta;
    }

    // --- MÉTODO PARA BUSCAR UNA JUNTA POR SU ID ---
    public Junta findJuntaById(Long juntaId) {
        Junta junta = Junta.findById(juntaId);
        if (junta == null) {
            throw new NotFoundException("Junta no encontrada con el id: " + juntaId);
        }
        return junta;
    }

    // --- MÉTODO PARA AÑADIR UN USUARIO A UNA JUNTA ---
    @Transactional
    public Participant addUserToJunta(Long juntaId, Long userId) {
        // 1. Buscamos la junta y el usuario
        Junta junta = findJuntaById(juntaId); // Reutilizamos el método anterior
        User user = User.findById(userId);
        if (user == null) {
            throw new NotFoundException("Usuario no encontrado");
        }

        // 2. Validaciones de negocio
        if (junta.participants.size() >= junta.participantsCount) {
            throw new BadRequestException("La junta ya está llena.");
        }

        boolean alreadyParticipant = junta.participants.stream()
                .anyMatch(p -> p.user.id.equals(userId));
        if (alreadyParticipant) {
            throw new BadRequestException("El usuario ya es parte de esta junta.");
        }

        // 3. Si todo está bien, creamos el nuevo participante
        Participant newParticipant = new Participant();
        newParticipant.user = user;
        newParticipant.junta = junta;
        newParticipant.paymentStatus = Participant.PaymentStatus.PENDIENTE;
        newParticipant.turnOrder = junta.participants.size() + 1; // Le toca el siguiente turno disponible

        // 4. Guardamos el nuevo participante
        newParticipant.persist();

        return newParticipant;
    }
}