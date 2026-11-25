package ec.edu.uta.aulalogica.controller;

import ec.edu.uta.aulalogica.model.ResultadoTeclado;
import ec.edu.uta.aulalogica.model.Usuario;
import ec.edu.uta.aulalogica.service.ContenidoService;
import ec.edu.uta.aulalogica.service.TecladoService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/** Módulo "Mecanografía": niveles, bloqueo y puntaje calculados en Java. */
@Controller
public class TecladoController {
    private final ContenidoService contenido;
    private final TecladoService teclado;

    public TecladoController(ContenidoService contenido, TecladoService teclado) {
        this.contenido = contenido;
        this.teclado = teclado;
    }

    @GetMapping("/mecanografia")
    public String menu() {
        return "mecanografia";
    }

    @GetMapping("/mecanografia/{i}")
    public String jugar(@PathVariable int i, HttpSession sesion, Model model) {
        List<String> niveles = contenido.niveles();
        Usuario u = SesionUtil.usuario(sesion);
        if (i < 0 || i >= niveles.size() || !u.nivelAbierto(i)) {
            return "redirect:/mecanografia";
        }
        model.addAttribute("nivel", i);
        model.addAttribute("texto", niveles.get(i));
        model.addAttribute("hayMas", i < niveles.size() - 1);
        return "nivel";
    }

    @PostMapping("/mecanografia/{i}/resultado")
    @ResponseBody
    public ResultadoTeclado resultado(@PathVariable int i, @RequestParam int aciertos,
                                      @RequestParam int errores, HttpSession sesion) {
        List<String> niveles = contenido.niveles();
        Usuario u = SesionUtil.usuario(sesion);
        if (i < 0 || i >= niveles.size() || !u.nivelAbierto(i)) {
            return new ResultadoTeclado(0, 0, false, false, "Nivel no disponible.");
        }
        int ok = Math.max(0, Math.min(aciertos, niveles.get(i).length()));
        int err = Math.max(0, Math.min(errores, 100000));
        int puntaje = teclado.puntaje(ok, err);
        int precision = teclado.precision(ok, err);
        boolean pasa = teclado.supera(precision);
        Usuario.Intento previo = u.getTeclado().get(i);
        u.getTeclado().put(i, new Usuario.Intento(
            Math.max(previo == null ? 0 : previo.puntaje, puntaje),
            Math.max(previo == null ? 0 : previo.precision, precision),
            (previo != null && previo.superado) || pasa));
        String msg = pasa
            ? "¡Nivel superado! " + puntaje + " puntos con " + precision + " % de precisión."
            : "Lograste " + precision + " % de precisión. Necesitas " + TecladoService.PRECISION_MINIMA
                + " % para pasar: inténtalo otra vez.";
        return new ResultadoTeclado(puntaje, precision, pasa, pasa && i < niveles.size() - 1, msg);
    }
}
