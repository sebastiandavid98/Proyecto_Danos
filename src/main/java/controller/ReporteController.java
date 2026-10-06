package controller;

import com.universidad.reportedanos.modelo.Reporte;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.ReporteService;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @PatchMapping("/{id}/revision")
    public ResponseEntity<String> iniciarRevision(@PathVariable UUID id) {
        Reporte reporte = reporteService.iniciarRevision(id);
        return ResponseEntity.ok("Reporte " + reporte.getId() + " en revision");
    }

    @PatchMapping("/{id}/responsable/{responsableId}")
    public ResponseEntity<String> asignarResponsable(@PathVariable UUID id,
                                                      @PathVariable UUID responsableId) {
        Reporte reporte = reporteService.asignarResponsable(id, responsableId);
        return ResponseEntity.ok("Responsable asignado al reporte " + reporte.getId());
    }

    @PatchMapping("/{id}/proceso")
    public ResponseEntity<String> iniciarProceso(@PathVariable UUID id) {
        Reporte reporte = reporteService.iniciarProceso(id);
        return ResponseEntity.ok("Reporte " + reporte.getId() + " en proceso");
    }

    @PatchMapping("/{id}/solucion")
    public ResponseEntity<String> registrarSolucion(@PathVariable UUID id,
                                                     @RequestParam String descripcion) {
        Reporte reporte = reporteService.registrarSolucion(id, descripcion);
        return ResponseEntity.ok("Solucion registrada para el reporte " + reporte.getId());
    }

    @PatchMapping("/{id}/cerrar")
    public ResponseEntity<String> cerrar(@PathVariable UUID id) {
        Reporte reporte = reporteService.cerrar(id);
        return ResponseEntity.ok("Reporte " + reporte.getId() + " cerrado");
    }
}
