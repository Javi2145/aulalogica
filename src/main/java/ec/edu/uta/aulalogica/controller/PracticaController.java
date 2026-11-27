package ec.edu.uta.aulalogica.controller;

import ec.edu.uta.aulalogica.model.Ejercicio;
import ec.edu.uta.aulalogica.model.Resultado;
import ec.edu.uta.aulalogica.service.ContenidoService;
import ec.edu.uta.aulalogica.service.PracticaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/** Módulo "Práctica en Java": el formulario se procesa en el servidor con Java. */
@Controller
public class PracticaController {
    private final ContenidoService contenido;
    private final PracticaService practicas;

    public PracticaController(ContenidoService contenido, PracticaService practicas) {
        this.contenido = contenido;
        this.practicas = practicas;
    }

    @GetMapping({"/practica", "/practica/{i}"})
    public String ver(@PathVariable(required = false) Integer i, Model model) {
        return vista(i == null ? 0 : i, "", null, model);
    }

    @PostMapping("/practica/{i}")
    public String probar(@PathVariable int i, @RequestParam(defaultValue = "") String dato, Model model) {
        Ejercicio e = contenido.ejercicio(i);
        Resultado r = practicas.procesar(e, dato);
        return vista(i, dato, r, model);
    }

    private String vista(int i, String dato, Resultado r, Model model) {
        int idx = ContenidoService.clamp(i, contenido.ejercicios().size());
        model.addAttribute("pi", idx);
        model.addAttribute("ejercicio", contenido.ejercicio(idx));
        model.addAttribute("dato", dato);
        model.addAttribute("resultado", r);
        return "practica";
    }
}
