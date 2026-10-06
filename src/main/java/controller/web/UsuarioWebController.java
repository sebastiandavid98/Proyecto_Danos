package controller.web;

import com.universidad.reportedanos.modelo.Email;
import com.universidad.reportedanos.modelo.Rol;
import com.universidad.reportedanos.modelo.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import repository.UsuarioRepository;

@Controller
@RequestMapping("/usuarios")
public class UsuarioWebController {

    private final UsuarioRepository usuarioRepository;

    public UsuarioWebController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Lista usuarios
    @GetMapping
    public String lista(Model model) {
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "usuarios/lista";
    }

    // Formulario nuevo usuario
    @GetMapping("/nuevo")
    public String formulario(Model model) {
        model.addAttribute("roles", Rol.values());
        return "usuarios/nuevo";
    }

    // Crea el usuario
    @PostMapping
    public String crear(@RequestParam String nombre,
                        @RequestParam String email,
                        @RequestParam String rol,
                        RedirectAttributes attrs) {
        try {
            Usuario usuario = new Usuario(nombre, new Email(email), Rol.valueOf(rol));
            usuarioRepository.save(usuario);
            attrs.addFlashAttribute("mensaje", "Usuario creado correctamente");
            return "redirect:/usuarios";
        } catch (Exception e) {
            attrs.addFlashAttribute("error", e.getMessage());
            return "redirect:/usuarios/nuevo";
        }
    }
}
