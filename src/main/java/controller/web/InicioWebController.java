package controller.web;

import comportamentales.state.*;
import com.universidad.reportedanos.modelo.Reporte;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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

    // Dashboard principal con estadisticas
    @GetMapping("/")
    public String inicio(Model model) {
        List<Reporte> todos = reporteRepository.findAll();

        long pendientes  = todos.stream().filter(r -> r.getEstado() instanceof EstadoPendiente).count();
        long enRevision  = todos.stream().filter(r -> r.getEstado() instanceof EstadoEnRevision).count();
        long enProceso   = todos.stream().filter(r -> r.getEstado() instanceof EstadoEnProceso).count();
        long solucionados= todos.stream().filter(r -> r.getEstado() instanceof EstadoSolucionado).count();
        long cerrados    = todos.stream().filter(r -> r.getEstado() instanceof EstadoCerrado).count();

        // Reportes recientes (ultimos 6)
        List<Reporte> recientes = todos.stream()
            .sorted((a, b) -> b.getFechaCreacion().compareTo(a.getFechaCreacion()))
            .limit(6)
            .toList();

        // Conteo por categoria
        Map<String, Long> porCategoria = new LinkedHashMap<>();
        todos.forEach(r -> {
            String cat = r.getCategoria().name().replace('_', ' ');
            porCategoria.merge(cat, 1L, Long::sum);
        });

        model.addAttribute("total",       todos.size());
        model.addAttribute("pendientes",  pendientes);
        model.addAttribute("enRevision",  enRevision);
        model.addAttribute("enProceso",   enProceso);
        model.addAttribute("solucionados",solucionados);
        model.addAttribute("cerrados",    cerrados);
        model.addAttribute("recientes",   recientes);
        model.addAttribute("porCategoria",porCategoria);

        return "index";
    }
}
