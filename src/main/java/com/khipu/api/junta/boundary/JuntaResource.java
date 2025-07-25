package com.khipu.api.junta.boundary;

import jakarta.annotation.security.RolesAllowed; // <--- ¡Añade esta importación!
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.jwt.JsonWebToken; // <--- ¡Añade esta importación!
import jakarta.inject.Inject; // <--- ¡Añade esta importación!
import io.quarkus.security.Authenticated; 


@Path("/api/juntas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class JuntaResource {

    @Inject
    JsonWebToken jwt; // <--- Podemos inyectar el token para obtener datos

    @GET
    @Authenticated // <--- ¡ESTA ES LA LÍNEA CLAVE!
    public String helloJuntas() {
        // Ahora podemos saber qué usuario está haciendo la llamada
        String userEmail = jwt.getName();
        return "API de Juntas está funcionando! (Llamada autenticada por: " + userEmail + ")";
    }
}