package pe.edu.soporte.controller;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pe.edu.soporte.dto.SolicitudEntrada;
import pe.edu.soporte.dto.SolicitudRespuesta;
import pe.edu.soporte.service.SolicitudService;

@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudController {

    private final SolicitudService servicio;

    public SolicitudController(SolicitudService servicio) {
        this.servicio = servicio;
    }

    @PostMapping
    public ResponseEntity<SolicitudRespuesta> crear(@Valid @RequestBody SolicitudEntrada entrada) {
        var respuesta = SolicitudRespuesta.desde(servicio.crear(entrada));
        URI ubicacion = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(respuesta.id()).toUri();
        return ResponseEntity.created(ubicacion).body(respuesta);
    }

    @GetMapping
    public List<SolicitudRespuesta> listar() {
        return servicio.listar().stream().map(SolicitudRespuesta::desde).toList();
    }

    @GetMapping("/{id}")
    public SolicitudRespuesta consultar(@PathVariable long id) {
        return SolicitudRespuesta.desde(servicio.consultar(id));
    }

    @PutMapping("/{id}")
    public SolicitudRespuesta actualizar(@PathVariable long id,
            @Valid @RequestBody SolicitudEntrada entrada) {
        return SolicitudRespuesta.desde(servicio.actualizar(id, entrada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable long id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
