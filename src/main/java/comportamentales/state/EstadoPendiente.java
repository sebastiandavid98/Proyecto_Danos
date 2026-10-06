package comportamentales.state;

import com.universidad.reportedanos.modelo.Reporte;

// El reporte acaba de crearse, espera que alguien lo revise
public class EstadoPendiente implements EstadoReporte {

    @Override
    public void iniciarRevision(Reporte contexto) {
        contexto.setEstado(new EstadoEnRevision());
    }

    @Override
    public void iniciarProceso(Reporte contexto) {
        throw new IllegalStateException("El reporte debe pasar por revision antes de procesarse");
    }

    @Override
    public void registrarSolucion(Reporte contexto, String descripcionSolucion) {
        throw new IllegalStateException("No se puede registrar una solucion sin haber procesado el reporte");
    }

    @Override
    public void cerrar(Reporte contexto) {
        throw new IllegalStateException("No se puede cerrar un reporte pendiente");
    }
}
