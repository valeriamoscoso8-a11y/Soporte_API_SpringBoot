package pe.edu.soporte.dto;

import java.time.Instant;
import pe.edu.soporte.model.Estado;
import pe.edu.soporte.model.Solicitud;

public record SolicitudRespuesta(
        long id,
        String titulo,
        String descripcion,
        PersonaRespuesta cliente,
        PersonaRespuesta tecnico,
        Estado estado,
        Instant fechaCreacion) {

    public record PersonaRespuesta(String nombre, String correo) {
    }

    public static SolicitudRespuesta desde(Solicitud solicitud) {
        var cliente = solicitud.cliente();
        var tecnico = solicitud.tecnico();
        return new SolicitudRespuesta(
                solicitud.id(),
                solicitud.titulo(),
                solicitud.descripcion(),
                new PersonaRespuesta(cliente.nombre(), cliente.correo()),
                tecnico == null ? null : new PersonaRespuesta(tecnico.nombre(), tecnico.correo()),
                solicitud.estado(),
                solicitud.fechaCreacion());
    }
}
