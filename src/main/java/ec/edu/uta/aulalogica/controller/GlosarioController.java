package ec.edu.uta.aulalogica.controller;

import ec.edu.uta.aulalogica.service.ContenidoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/** Glosario de conceptos del periodo académico (RF-06). */
@Controller
public class GlosarioController {
    private final ContenidoService contenido;

    public GlosarioController(ContenidoService contenido) {
        this.contenido = contenido;
    }

    @GetMapping("/glosario")
    public String glosario(Model model) {
        model.addAttribute("terminos", contenido.glosario());
        return "glosario";
    }
}
