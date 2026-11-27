package ec.edu.uta.aulalogica.controller;

import ec.edu.uta.aulalogica.model.Tema;
import ec.edu.uta.aulalogica.model.Usuario;
import ec.edu.uta.aulalogica.service.ContenidoService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/** Módulo "Contenidos": listado de temas y lecciones con pregunta de comprobación. */
@Controller
public class ContenidoController {
    private final ContenidoService contenido;

    public ContenidoController(ContenidoService contenido) {
        this.contenido = contenido;
    }

    @GetMapping("/contenidos")
    public String contenidos(@RequestParam(defaultValue = "") String q, Model model) {
        List<Integer> visibles = contenido.buscarTemas(q);
        model.addAttribute("visibles", visibles);
        model.addAttribute("q", q);
        return "contenidos";
    }

    @GetMapping("/leccion/{i}")
    public String leccion(@PathVariable int i, Model model) {
        return mostrar(i, null, model, null);
    }

    @PostMapping("/leccion/{i}/responder")
    public String responder(@PathVariable int i, @RequestParam int opcion, HttpSession sesion, Model model) {
        Usuario u = SesionUtil.usuario(sesion);
        int idx = ContenidoService.clamp(i, contenido.temas().size());
        Tema t = contenido.tema(idx);
        boolean acierto = opcion == t.correcta();
        String msg;
        if (acierto) {
            u.getLeccionesHechas().add(idx);
            msg = "¡Correcto! Lección marcada como completada.";
        } else {
            msg = "✘ Incorrecto. Respuesta correcta: " + t.opciones().get(t.correcta())
                + ".\nRetroalimentación: " + t.explicacion();
        }
        model.addAttribute("acierto", acierto);
        return mostrar(idx, opcion, model, msg);
    }

    private String mostrar(int i, Integer elegida, Model model, String mensaje) {
        int idx = ContenidoService.clamp(i, contenido.temas().size());
        model.addAttribute("i", idx);
        model.addAttribute("tema", contenido.tema(idx));
        model.addAttribute("ejemplo", contenido.ejemplo(idx));
        model.addAttribute("practicaRel", contenido.practicaRelacionada(idx));
        model.addAttribute("elegida", elegida);
        model.addAttribute("mensaje", mensaje == null ? "Elige una opción para comprobar lo aprendido." : mensaje);
        model.addAttribute("anterior", Math.max(0, idx - 1));
        model.addAttribute("siguiente", Math.min(contenido.temas().size() - 1, idx + 1));
        return "leccion";
    }
}
