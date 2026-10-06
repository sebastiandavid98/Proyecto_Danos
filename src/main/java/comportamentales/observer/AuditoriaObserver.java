package comportamentales.observer;

import com.universidad.reportedanos.modelo.Reporte;
import java.time.LocalDateTime;

// Registra en bitacora cuando cambia el estado del reporte
public class AuditoriaObserver implements ReporteObserver {

    @Override
    public void onReporteSolucionado(Reporte reporte) {
        System.out.println("[AUDITORIA " + LocalDateTime.now() + "] Reporte "
            + reporte.getId() + " marcado como SOLUCIONADO por "
            + reporte.getResponsable().getNombre());
    }

    @Override
    public void onReporteCerrado(Reporte reporte) {
        System.out.println("[AUDITORIA " + LocalDateTime.now() + "] Reporte "
            + reporte.getId() + " CERRADO definitivamente.");
    }
}
