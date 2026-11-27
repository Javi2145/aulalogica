package ec.edu.uta.aulalogica.controller;

import ec.edu.uta.aulalogica.model.Usuario;
import ec.edu.uta.aulalogica.service.ContenidoService;
import ec.edu.uta.aulalogica.service.ProgresoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/** Progreso, perfil, configuración y equipo. */
@Controller
public class PerfilController {
    private final ProgresoService progreso;
    private final ContenidoService contenido;

    public PerfilController(ProgresoService progreso, ContenidoService contenido) {
        this.progreso = progreso;
        this.contenido = contenido;
    }

    @GetMapping("/progreso")
    public String progreso(HttpSession sesion, Model model) {
        model.addAttribute("p", progreso.resumen(SesionUtil.usuario(sesion)));
        return "progreso";
    }

    @GetMapping("/perfil")
    public String perfil(HttpSession sesion, Model model) {
        model.addAttribute("p", progreso.resumen(SesionUtil.usuario(sesion)));
        return "perfil";
    }

    @GetMapping("/equipo")
    public String equipo(Model model) {
        model.addAttribute("integrantes", ContenidoService.INTEGRANTES);
        model.addAttribute("miembros", contenido.miembros());
        model.addAttribute("creditos", contenido.creditos());
        return "equipo";
    }

    @GetMapping("/configuracion")
    public String configuracion(Model model) {
        return vista(model, null, false);
    }

    @PostMapping("/configuracion/nombre")
    public String nombre(@RequestParam(defaultValue = "") String nombre, HttpSession sesion, Model model) {
        Usuario u = SesionUtil.usuario(sesion);
        if (u.isInvitado()) return vista(model, null, false);
        String n = nombre.trim();
        if (n.isEmpty()) return vista(model, "Escribe el nuevo nombre.", true);
        u.setNombre(n);
        model.addAttribute("usuario", u);
        return vista(model, "Nombre actualizado a " + n + ".", false);
    }

    @PostMapping("/configuracion/clave")
    public String clave(@RequestParam(defaultValue = "") String actual,
                        @RequestParam(defaultValue = "") String nueva,
                        HttpSession sesion, Model model) {
        Usuario u = SesionUtil.usuario(sesion);
        if (u.isInvitado()) return vista(model, null, false);
        if (!actual.equals(u.getClave())) return vista(model, "La contraseña actual no coincide.", true);
        if (nueva.length() < 6) return vista(model, "La nueva contraseña necesita al menos 6 caracteres.", true);
        u.setClave(nueva);
        return vista(model, "Contraseña actualizada.", false);
    }

    private String vista(Model model, String mensaje, boolean error) {
        model.addAttribute("mensaje", mensaje);
        model.addAttribute("error", error);
        return "configuracion";
    }
}
