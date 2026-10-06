package comportamentales.state;

import com.universidad.reportedanos.modelo.Reporte;

// El dano fue reparado, falta confirmacion final
public class EstadoSolucionado implements EstadoReporte {

    @Override
    public void iniciarRevision(Reporte contexto) {
        throw new IllegalStateException("El reporte ya fue solucionado");
    }

    @Override
    public void iniciarProceso(Reporte contexto) {
        throw new IllegalStateException("El reporte ya fue solucionado");
    }

    @Override
    public void registrarSolucion(Reporte contexto, String descripcionSolucion) {
        throw new IllegalStateException("La solucion ya fue registrada");
    }

    @Override
    public void cerrar(Reporte contexto) {
        contexto.setEstado(new EstadoCerrado());

        // Notificamos el cierre
        if (contexto.getGestorEventos() != null) {
            contexto.getGestorEventos().notificarCierre(contexto);
        }
    }
}
