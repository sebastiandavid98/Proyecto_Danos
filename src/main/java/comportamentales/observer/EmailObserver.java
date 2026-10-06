package comportamentales.observer;

import com.universidad.reportedanos.modelo.Reporte;

// Notifica al creador del reporte por correo
public class EmailObserver implements ReporteObserver {

    @Override
    public void onReporteSolucionado(Reporte reporte) {
        System.out.println("[EMAIL] Correo enviado a "
            + reporte.getCreador().getEmail().valor()
            + ": Su reporte ha sido solucionado.");
    }

    @Override
    public void onReporteCerrado(Reporte reporte) {
        System.out.println("[EMAIL] Correo enviado a "
            + reporte.getCreador().getEmail().valor()
            + ": Su reporte fue cerrado. Gracias por reportarlo.");
    }
}
