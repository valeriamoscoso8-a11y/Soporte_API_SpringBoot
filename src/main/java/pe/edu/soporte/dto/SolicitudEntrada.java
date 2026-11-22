package pe.edu.soporte.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import pe.edu.soporte.model.Estado;

public record SolicitudEntrada(
        @NotBlank(message = "El título es obligatorio")
        @Size(max = 120, message = "El título admite hasta 120 caracteres")
        String titulo,

        @NotBlank(message = "La descripción es obligatoria")
        @Size(max = 2000, message = "La descripción admite hasta 2000 caracteres")
        String descripcion,

        @NotNull(message = "El cliente es obligatorio")
        @Valid PersonaEntrada cliente,

        @Valid PersonaEntrada tecnico,

        @NotNull(message = "El estado es obligatorio")
        Estado estado) {
}
