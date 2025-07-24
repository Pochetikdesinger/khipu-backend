package com.khipu.api.junta.boundary;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/api/juntas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class JuntaResource {

    @GET
    public String helloJuntas() {
        return "API de Juntas está funcionando!";
    }

    // Aquí irán los endpoints para GET, POST, etc. de las juntas.
}