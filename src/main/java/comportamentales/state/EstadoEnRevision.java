package comportamentales.state;

import com.universidad.reportedanos.modelo.Reporte;

// Personal encargado verifico la informacion
public class EstadoEnRevision implements EstadoReporte {

    @Override
    public void iniciarRevision(Reporte contexto) {
        throw new IllegalStateException("El reporte ya esta en revision");
    }

    @Override
    public void iniciarProceso(Reporte contexto) {
        if (contexto.getResponsable() == null) {
            throw new IllegalStateException("Hay que asignar un responsable antes de procesar el reporte");
        }
        contexto.setEstado(new EstadoEnProceso());
    }

    @Override
    public void registrarSolucion(Reporte contexto, String descripcionSolucion) {
        throw new IllegalStateException("El reporte todavia no esta en proceso");
    }

    @Override
    public void cerrar(Reporte contexto) {
        throw new IllegalStateException("El reporte no se puede cerrar sin haber sido solucionado");
    }
}
