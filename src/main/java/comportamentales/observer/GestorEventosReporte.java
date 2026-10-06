package comportamentales.observer;

import com.universidad.reportedanos.modelo.Reporte;
import java.util.ArrayList;
import java.util.List;

// Gestor de eventos: avisa a los observadores cuando algo cambia
public class GestorEventosReporte {

    private final List<ReporteObserver> observadores = new ArrayList<>();

    public void suscribir(ReporteObserver observer) {
        if (observer != null && !observadores.contains(observer)) {
            observadores.add(observer);
        }
    }

    public void desuscribir(ReporteObserver observer) {
        observadores.remove(observer);
    }

    public void notificarSolucion(Reporte reporte) {
        for (ReporteObserver obs : observadores) {
            obs.onReporteSolucionado(reporte);
        }
    }

    public void notificarCierre(Reporte reporte) {
        for (ReporteObserver obs : observadores) {
            obs.onReporteCerrado(reporte);
        }
    }
}
