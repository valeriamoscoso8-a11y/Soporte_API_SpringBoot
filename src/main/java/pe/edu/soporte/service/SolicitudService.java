package pe.edu.soporte.service;

import java.util.List;
import pe.edu.soporte.dto.SolicitudEntrada;
import pe.edu.soporte.model.Solicitud;

public interface SolicitudService {

    Solicitud crear(SolicitudEntrada entrada);

    List<Solicitud> listar();

    Solicitud consultar(long id);

    Solicitud actualizar(long id, SolicitudEntrada entrada);

    void eliminar(long id);
}
