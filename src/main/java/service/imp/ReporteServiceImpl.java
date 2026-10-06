package service.imp;

import com.universidad.reportedanos.modelo.Categoria;
import com.universidad.reportedanos.modelo.Prioridad;
import com.universidad.reportedanos.modelo.Reporte;
import com.universidad.reportedanos.modelo.Ubicacion;
import com.universidad.reportedanos.modelo.Usuario;
import jakarta.transaction.Transactional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import repository.ReporteRepository;
import repository.UsuarioRepository;
import service.ReporteService;

@Service
public class ReporteServiceImpl implements ReporteService {

    private final ReporteRepository reporteRepository;
    private final UsuarioRepository usuarioRepository;

    // Inyeccion por constructor
    public ReporteServiceImpl(ReporteRepository reporteRepository,
                               UsuarioRepository usuarioRepository) {
        this.reporteRepository = reporteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional
    public Reporte crearReporte(UUID creadorId, Categoria categoria, Ubicacion ubicacion,
                                String descripcion, Prioridad prioridad) {
        Usuario creador = usuarioRepository.findById(creadorId)
            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        // El dominio valida las reglas al instanciarse
        Reporte reporte = new Reporte(creador, categoria, ubicacion, descripcion, prioridad);
        return reporteRepository.save(reporte);
    }

    @Override
    @Transactional
    public Reporte iniciarRevision(UUID reporteId) {
        Reporte reporte = buscarReporte(reporteId);
        reporte.iniciarRevision();
        return reporteRepository.save(reporte);
    }

    @Override
    @Transactional
    public Reporte asignarResponsable(UUID reporteId, UUID responsableId) {
        Reporte reporte = buscarReporte(reporteId);
        Usuario responsable = usuarioRepository.findById(responsableId)
            .orElseThrow(() -> new IllegalArgumentException("Responsable no encontrado"));

        reporte.asignarResponsable(responsable);
        return reporteRepository.save(reporte);
    }

    @Override
    @Transactional
    public Reporte iniciarProceso(UUID reporteId) {
        Reporte reporte = buscarReporte(reporteId);
        reporte.iniciarProceso();
        return reporteRepository.save(reporte);
    }

    @Override
    @Transactional
    public Reporte registrarSolucion(UUID reporteId, String descripcionSolucion) {
        Reporte reporte = buscarReporte(reporteId);
        reporte.registrarSolucion(descripcionSolucion);
        return reporteRepository.save(reporte);
    }

    @Override
    @Transactional
    public Reporte cerrar(UUID reporteId) {
        Reporte reporte = buscarReporte(reporteId);
        reporte.cerrar();
        return reporteRepository.save(reporte);
    }

    // Buscamos el reporte o lanzamos excepcion
    private Reporte buscarReporte(UUID id) {
        return reporteRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Reporte no encontrado"));
    }
}
