package ec.edu.uta.aulalogica.controller;

import ec.edu.uta.aulalogica.model.Evaluacion;
import ec.edu.uta.aulalogica.model.Pregunta;
import ec.edu.uta.aulalogica.model.Revision;
import ec.edu.uta.aulalogica.model.SesionEvaluacion;
import ec.edu.uta.aulalogica.model.Usuario;
import ec.edu.uta.aulalogica.service.ContenidoService;
import ec.edu.uta.aulalogica.service.EvaluacionService;
import ec.edu.uta.aulalogica.service.ReporteService;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/** Módulo "Evaluación": preguntas, respuestas en sesión y nota calculada en Java. */
@Controller
public class EvaluacionController {
    private static final String INTENTO = "intentoEvaluacion";

    private final ContenidoService contenido;
    private final EvaluacionService evaluaciones;

    public EvaluacionController(ContenidoService contenido, EvaluacionService evaluaciones) {
        this.contenido = contenido;
        this.evaluaciones = evaluaciones;
    }

    @GetMapping("/evaluaciones")
    public String menu() {
        return "evaluaciones";
    }

    @GetMapping("/evaluacion/{e}/iniciar")
    public String iniciar(@PathVariable int e, HttpSession sesion) {
        int idx = ContenidoService.clamp(e, contenido.evaluaciones().size());
        sesion.setAttribute(INTENTO, new SesionEvaluacion(idx, contenido.evaluacion(idx).preguntas().size()));
        return "redirect:/evaluacion/" + idx + "/pregunta/0";
    }

    @GetMapping("/evaluacion/{e}/pregunta/{q}")
    public String pregunta(@PathVariable int e, @PathVariable int q, HttpSession sesion, Model model) {
        return mostrar(e, q, sesion, model, "Elige una opción y pulsa “Enviar respuesta”.");
    }

    @PostMapping("/evaluacion/{e}/pregunta/{q}/elegir")
    public String elegir(@PathVariable int e, @PathVariable int q, @RequestParam int opcion, HttpSession sesion) {
        SesionEvaluacion s = intento(e, sesion);
        s.responder(ContenidoService.clamp(q, s.getRespuestas().size()), opcion);
        return "redirect:/evaluacion/" + s.getEvaluacion() + "/pregunta/" + q;
    }

    @PostMapping("/evaluacion/{e}/pregunta/{q}/enviar")
    public String enviar(@PathVariable int e, @PathVariable int q, HttpSession sesion, Model model) {
        SesionEvaluacion s = intento(e, sesion);
        int idx = ContenidoService.clamp(q, s.getRespuestas().size());
        if (s.getRespuestas().get(idx) == null) {
            return mostrar(e, idx, sesion, model, "Primero elige una opción.");
        }
        if (idx < s.getRespuestas().size() - 1) {
            return "redirect:/evaluacion/" + s.getEvaluacion() + "/pregunta/" + (idx + 1);
        }
        return "redirect:/evaluacion/" + s.getEvaluacion() + "/resultado";
    }

    @GetMapping("/evaluacion/{e}/resultado")
    public String resultado(@PathVariable int e, HttpSession sesion, Model model) {
        SesionEvaluacion s = intento(e, sesion);
        Evaluacion ev = contenido.evaluacion(s.getEvaluacion());
        int total = ev.preguntas().size();
        int aciertos = evaluaciones.puntaje(ev, s.getRespuestas());
        Usuario u = SesionUtil.usuario(sesion);
        Integer previa = u.getNotas().get(s.getEvaluacion());
        u.getNotas().merge(s.getEvaluacion(), aciertos, Math::max);
        if (previa == null || aciertos >= previa) {
            u.getFechas().put(s.getEvaluacion(), LocalDate.now(ReporteService.ZONA));
        }

        List<Revision> revision = new ArrayList<>();
        for (int i = 0; i < total; i++) {
            Pregunta p = ev.preguntas().get(i);
            Integer r = s.getRespuestas().get(i);
            boolean ok = r != null && r == p.correcta();
            revision.add(new Revision(i + 1, p.enunciado(),
                r == null ? "sin responder" : p.opciones().get(r), ok,
                p.opciones().get(p.correcta()), p.retroalimentacion()));
        }
        model.addAttribute("e", s.getEvaluacion());
        model.addAttribute("evaluacion", ev);
        model.addAttribute("aciertos", aciertos);
        model.addAttribute("total", total);
        model.addAttribute("nota", evaluaciones.sobreDiez(aciertos, total));
        model.addAttribute("porcentaje", aciertos * 100 / total);
        model.addAttribute("revision", revision);
        return "evaluacion-resultado";
    }

    private String mostrar(int e, int q, HttpSession sesion, Model model, String mensaje) {
        SesionEvaluacion s = intento(e, sesion);
        Evaluacion ev = contenido.evaluacion(s.getEvaluacion());
        int total = ev.preguntas().size();
        int idx = ContenidoService.clamp(q, total);
        model.addAttribute("e", s.getEvaluacion());
        model.addAttribute("q", idx);
        model.addAttribute("total", total);
        model.addAttribute("evaluacion", ev);
        model.addAttribute("pregunta", ev.preguntas().get(idx));
        model.addAttribute("respuestas", s.getRespuestas());
        model.addAttribute("faltan", total - s.contestadas());
        model.addAttribute("mensaje", mensaje);
        return "pregunta";
    }

    /** Recupera el intento de la sesión o crea uno nuevo si no corresponde a esta evaluación. */
    private SesionEvaluacion intento(int e, HttpSession sesion) {
        int idx = ContenidoService.clamp(e, contenido.evaluaciones().size());
        SesionEvaluacion s = (SesionEvaluacion) sesion.getAttribute(INTENTO);
        if (s == null || s.getEvaluacion() != idx) {
            s = new SesionEvaluacion(idx, contenido.evaluacion(idx).preguntas().size());
            sesion.setAttribute(INTENTO, s);
        }
        return s;
    }
}
