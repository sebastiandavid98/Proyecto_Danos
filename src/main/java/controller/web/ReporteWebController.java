package controller.web;

import com.universidad.reportedanos.modelo.Categoria;
import com.universidad.reportedanos.modelo.Prioridad;
import com.universidad.reportedanos.modelo.Reporte;
import com.universidad.reportedanos.modelo.Ubicacion;
import com.universidad.reportedanos.modelo.Usuario;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import repository.ReporteRepository;
import repository.UsuarioRepository;
import service.ReporteService;

@Controller
@RequestMapping("/reportes")
public class ReporteWebController {

    private final ReporteService reporteService;
    private final ReporteRepository reporteRepository;
    private final UsuarioRepository usuarioRepository;

    public ReporteWebController(ReporteService reporteService,
                                 ReporteRepository reporteRepository,
                                 UsuarioRepository usuarioRepository) {
        this.reporteService = reporteService;
        this.reporteRepository = reporteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // Lista todos los reportes
    @GetMapping
    public String lista(Model model) {
        List<Reporte> reportes = reporteRepository.findAll();
        model.addAttribute("reportes", reportes);
        return "reportes/lista";
    }

    // Formulario de nuevo reporte
    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        model.addAttribute("usuarios", usuarioRepository.findAll());
        model.addAttribute("categorias", Categoria.values());
        model.addAttribute("prioridades", Prioridad.values());
        return "reportes/nuevo";
    }

    // Crea el reporte
    @PostMapping
    public String crear(@RequestParam UUID creadorId,
                        @RequestParam String categoria,
                        @RequestParam String prioridad,
                        @RequestParam String edificio,
                        @RequestParam String espacio,
                        @RequestParam String descripcion,
                        RedirectAttributes attrs) {
        try {
            Ubicacion ubicacion = new Ubicacion(edificio, espacio);
            reporteService.crearReporte(
                creadorId,
                Categoria.valueOf(categoria),
                ubicacion,
                descripcion,
                Prioridad.valueOf(prioridad)
            );
            attrs.addFlashAttribute("mensaje", "Reporte creado correctamente");
            return "redirect:/reportes";
        } catch (Exception e) {
            attrs.addFlashAttribute("error", e.getMessage());
            return "redirect:/reportes/nuevo";
        }
    }

    // Detalle del reporte
    @GetMapping("/{id}")
    public String detalle(@PathVariable UUID id, Model model) {
        Reporte reporte = reporteRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Reporte no encontrado"));
        model.addAttribute("reporte", reporte);
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "reportes/detalle";
    }

    // Inicia revision
    @PostMapping("/{id}/revision")
    public String iniciarRevision(@PathVariable UUID id, RedirectAttributes attrs) {
        try {
            reporteService.iniciarRevision(id);
            attrs.addFlashAttribute("mensaje", "Revision iniciada");
        } catch (Exception e) {
            attrs.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/reportes/" + id;
    }

    // Asigna responsable
    @PostMapping("/{id}/responsable")
    public String asignarResponsable(@PathVariable UUID id,
                                      @RequestParam UUID responsableId,
                                      RedirectAttributes attrs) {
        try {
            reporteService.asignarResponsable(id, responsableId);
            attrs.addFlashAttribute("mensaje", "Responsable asignado");
        } catch (Exception e) {
            attrs.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/reportes/" + id;
    }

    // Inicia proceso
    @PostMapping("/{id}/proceso")
    public String iniciarProceso(@PathVariable UUID id, RedirectAttributes attrs) {
        try {
            reporteService.iniciarProceso(id);
            attrs.addFlashAttribute("mensaje", "Reporte en proceso");
        } catch (Exception e) {
            attrs.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/reportes/" + id;
    }

    // Registra solucion
    @PostMapping("/{id}/solucion")
    public String registrarSolucion(@PathVariable UUID id,
                                     @RequestParam String descripcionSolucion,
                                     RedirectAttributes attrs) {
        try {
            reporteService.registrarSolucion(id, descripcionSolucion);
            attrs.addFlashAttribute("mensaje", "Solucion registrada");
        } catch (Exception e) {
            attrs.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/reportes/" + id;
    }

    // Cierra el reporte
    @PostMapping("/{id}/cerrar")
    public String cerrar(@PathVariable UUID id, RedirectAttributes attrs) {
        try {
            reporteService.cerrar(id);
            attrs.addFlashAttribute("mensaje", "Reporte cerrado");
        } catch (Exception e) {
            attrs.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/reportes/" + id;
    }
}
