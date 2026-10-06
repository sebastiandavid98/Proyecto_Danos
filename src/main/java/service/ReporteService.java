package service;

import com.universidad.reportedanos.modelo.Categoria;
import com.universidad.reportedanos.modelo.Prioridad;
import com.universidad.reportedanos.modelo.Reporte;
import com.universidad.reportedanos.modelo.Ubicacion;
import java.util.UUID;

public interface ReporteService {
    Reporte crearReporte(UUID creadorId, Categoria categoria, Ubicacion ubicacion,
                         String descripcion, Prioridad prioridad);
    Reporte iniciarRevision(UUID reporteId);
    Reporte asignarResponsable(UUID reporteId, UUID responsableId);
    Reporte iniciarProceso(UUID reporteId);
    Reporte registrarSolucion(UUID reporteId, String descripcionSolucion);
    Reporte cerrar(UUID reporteId);
}
