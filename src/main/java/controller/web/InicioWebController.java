package controller.web;

import comportamentales.state.EstadoCerrado;
import comportamentales.state.EstadoEnProceso;
import comportamentales.state.EstadoPendiente;
import com.universidad.reportedanos.modelo.Reporte;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import repository.ReporteRepository;

@Controller
public class InicioWebController {

    private final ReporteRepository reporteRepository;

    public InicioWebController(ReporteRepository reporteRepository) {
        this.reporteRepository = reporteRepository;
    }

    // Pagina de inicio con estadisticas
    @GetMapping("/")
    public String inicio(Model model) {
        List<Reporte> todos = reporteRepository.findAll();

        long pendientes = todos.stream()
            .filter(r -> r.getEstado() instanceof EstadoPendiente)
            .count();

        long enProceso = todos.stream()
            .filter(r -> r.getEstado() instanceof EstadoEnProceso)
            .count();

        long cerrados = todos.stream()
            .filter(r -> r.getEstado() instanceof EstadoCerrado)
            .count();

        model.addAttribute("total", todos.size());
        model.addAttribute("pendientes", pendientes);
        model.addAttribute("enProceso", enProceso);
        model.addAttribute("cerrados", cerrados);

        return "index";
    }
}
