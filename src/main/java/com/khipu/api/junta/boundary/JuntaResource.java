package com.khipu.api.junta.boundary;

import com.khipu.api.junta.control.JuntaService;
import com.khipu.api.junta.entity.Junta;
import com.khipu.api.junta.entity.Participant;
import com.khipu.api.shared.dto.JuntaCreationDto;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Path("/api/juntas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated // ¡Todo en este recurso requerirá un token!
public class JuntaResource {

    @Inject
    JuntaService juntaService;

    @Inject
    JsonWebToken jwt; // Inyectamos el token para saber quién hace la llamada

    @POST
    public Response createJunta(@Valid JuntaCreationDto juntaDto) {
        Long organizerId = Long.parseLong(jwt.getClaim("userId").toString());
        Junta nuevaJunta = juntaService.createJunta(juntaDto, organizerId);
        return Response.status(Response.Status.CREATED).entity(nuevaJunta).build();
    }

    @GET
    @Path("/{id}")
    public Response getJuntaById(@PathParam("id") Long id) {
        Junta junta = juntaService.findJuntaById(id);
        return Response.ok(junta).build();
    }

    // --- ASEGÚRATE DE QUE ESTE MÉTODO ESTÉ EXACTAMENTE ASÍ ---
    @POST
    @Path("/{id}/join")
    public Response joinJunta(@PathParam("id") Long id) {
        Long userId = Long.parseLong(jwt.getClaim("userId").toString());
        Participant newParticipant = juntaService.addUserToJunta(id, userId);
        return Response.ok(newParticipant).build();
    }
}