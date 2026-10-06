package comportamentales.state;

import com.universidad.reportedanos.modelo.Reporte;

// El reporte esta archivado, no se puede modificar
public class EstadoCerrado implements EstadoReporte {

    @Override
    public void iniciarRevision(Reporte contexto) {
        throw new IllegalStateException("El reporte ya esta cerrado");
    }

    @Override
    public void iniciarProceso(Reporte contexto) {
        throw new IllegalStateException("El reporte ya esta cerrado");
    }

    @Override
    public void registrarSolucion(Reporte contexto, String descripcionSolucion) {
        throw new IllegalStateException("El reporte ya esta cerrado");
    }

    @Override
    public void cerrar(Reporte contexto) {
        throw new IllegalStateException("El reporte ya esta cerrado");
    }
}
