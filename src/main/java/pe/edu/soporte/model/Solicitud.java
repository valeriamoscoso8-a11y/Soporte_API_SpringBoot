package pe.edu.soporte.model;

import java.time.Instant;

public record Solicitud(
        long id,
        String titulo,
        String descripcion,
        Cliente cliente,
        Tecnico tecnico,
        Estado estado,
        Instant fechaCreacion) {
}
