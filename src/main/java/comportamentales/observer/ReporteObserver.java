package comportamentales.observer;

import com.universidad.reportedanos.modelo.Reporte;

// Interfaz que deben implementar los observadores del reporte
public interface ReporteObserver {
    void onReporteSolucionado(Reporte reporte);
    default void onReporteCerrado(Reporte reporte) {}
}
