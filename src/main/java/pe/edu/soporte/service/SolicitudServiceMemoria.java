package pe.edu.soporte.service;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import pe.edu.soporte.dto.SolicitudEntrada;
import pe.edu.soporte.exception.SolicitudNoEncontradaException;
import pe.edu.soporte.model.Cliente;
import pe.edu.soporte.model.Solicitud;
import pe.edu.soporte.model.Tecnico;

@Service
public class SolicitudServiceMemoria implements SolicitudService {

    private final Map<Long, Solicitud> solicitudes = new LinkedHashMap<>();
    private long ultimoId;

    @Override
    public synchronized Solicitud crear(SolicitudEntrada entrada) {
        long id = ++ultimoId;
        Solicitud solicitud = construir(id, entrada, Instant.now());
        solicitudes.put(id, solicitud);
        return solicitud;
    }

    @Override
    public synchronized List<Solicitud> listar() {
        return List.copyOf(solicitudes.values());
    }

    @Override
    public synchronized Solicitud consultar(long id) {
        Solicitud solicitud = solicitudes.get(id);
        if (solicitud == null) {
            throw new SolicitudNoEncontradaException(id);
        }
        return solicitud;
    }

    @Override
    public synchronized Solicitud actualizar(long id, SolicitudEntrada entrada) {
        Solicitud anterior = consultar(id);
        Solicitud actualizada = construir(id, entrada, anterior.fechaCreacion());
        solicitudes.put(id, actualizada);
        return actualizada;
    }

    @Override
    public synchronized void eliminar(long id) {
        if (solicitudes.remove(id) == null) {
            throw new SolicitudNoEncontradaException(id);
        }
    }

    private Solicitud construir(long id, SolicitudEntrada entrada, Instant fechaCreacion) {
        var datosCliente = entrada.cliente();
        var datosTecnico = entrada.tecnico();
        var cliente = new Cliente(datosCliente.nombre().strip(), datosCliente.correo());
        var tecnico = datosTecnico == null
                ? null
                : new Tecnico(datosTecnico.nombre().strip(), datosTecnico.correo());
        return new Solicitud(id, entrada.titulo().strip(), entrada.descripcion().strip(),
                cliente, tecnico, entrada.estado(), fechaCreacion);
    }
}
