package comportamentales.state;

import com.universidad.reportedanos.modelo.Reporte;

// El responsable esta trabajando en el dano
public class EstadoEnProceso implements EstadoReporte {

    @Override
    public void iniciarRevision(Reporte contexto) {
        throw new IllegalStateException("El reporte ya paso por revision");
    }

    @Override
    public void iniciarProceso(Reporte contexto) {
        throw new IllegalStateException("El reporte ya esta en proceso");
    }

    @Override
    public void registrarSolucion(Reporte contexto, String descripcionSolucion) {
        if (descripcionSolucion == null || descripcionSolucion.isBlank()) {
            throw new IllegalArgumentException("La descripcion de la solucion es obligatoria");
        }
        contexto.setSolucion(descripcionSolucion);
        contexto.setEstado(new EstadoSolucionado());

        // Notificamos que se soluciono
        if (contexto.getGestorEventos() != null) {
            contexto.getGestorEventos().notificarSolucion(contexto);
        }
    }

    @Override
    public void cerrar(Reporte contexto) {
        throw new IllegalStateException("El reporte debe estar solucionado antes de cerrarse");
    }
}
