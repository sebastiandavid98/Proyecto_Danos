package comportamentales.state;

import com.universidad.reportedanos.modelo.Reporte;

// Interfaz del patron State: cada estado sabe que transiciones permite
public interface EstadoReporte {
    void iniciarRevision(Reporte contexto);
    void iniciarProceso(Reporte contexto);
    void registrarSolucion(Reporte contexto, String descripcionSolucion);
    void cerrar(Reporte contexto);
}
