package ec.edu.uta.aulalogica.controller;

import ec.edu.uta.aulalogica.model.Usuario;
import ec.edu.uta.aulalogica.service.ContenidoService;
import ec.edu.uta.aulalogica.service.ReporteService;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/** Descarga del reporte PDF de evaluaciones con los datos de la cuenta en sesión. */
@Controller
public class ReporteController {
    private final ContenidoService contenido;
    private final ReporteService reportes;

    public ReporteController(ContenidoService contenido, ReporteService reportes) {
        this.contenido = contenido;
        this.reportes = reportes;
    }

    @GetMapping("/reporte/evaluaciones")
    public ResponseEntity<byte[]> evaluaciones(HttpSession sesion) {
        Usuario u = SesionUtil.usuario(sesion);
        byte[] pdf = reportes.generar(u, contenido.evaluaciones(), LocalDate.now(ReporteService.ZONA));
        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_PDF)
            .header(HttpHeaders.CONTENT_DISPOSITION,
                ContentDisposition.attachment().filename("Reporte_Evaluaciones_AulaLogica.pdf").build().toString())
            .header(HttpHeaders.CACHE_CONTROL, "no-store")
            .body(pdf);
    }
}
