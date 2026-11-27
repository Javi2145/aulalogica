package ec.edu.uta.aulalogica.controller;

import ec.edu.uta.aulalogica.service.ContenidoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

/** Datos comunes para todas las vistas (menú lateral, usuario en sesión). */
@ControllerAdvice
public class DatosGlobales {
    private final ContenidoService contenido;

    public DatosGlobales(ContenidoService contenido) {
        this.contenido = contenido;
    }

    @ModelAttribute
    public void agregar(Model model, HttpSession sesion) {
        model.addAttribute("usuario", sesion.getAttribute(SesionUtil.CLAVE));
        model.addAttribute("temas", contenido.temas());
        model.addAttribute("ejercicios", contenido.ejercicios());
        model.addAttribute("niveles", contenido.niveles());
        model.addAttribute("evaluaciones", contenido.evaluaciones());
        model.addAttribute("colores", ContenidoService.COLORES);
    }
}
