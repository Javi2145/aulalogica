package ec.edu.uta.aulalogica.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ec.edu.uta.aulalogica.model.FilaReporte;
import ec.edu.uta.aulalogica.model.Usuario;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;

/** Reporte PDF de evaluaciones: cifras (completadas, promedio, mejor) y archivo generado. */
class ReporteServiceTest {
    private final ContenidoService contenido = new ContenidoService();
    private final ReporteService servicio = new ReporteService();

    private Usuario usuarioConNotas() {
        Usuario u = new Usuario("Javi Garcés", "javi@uta.edu.ec", "123456", false);
        u.getNotas().put(0, 9);   // 9 de 10 = 90 %
        u.getNotas().put(1, 4);   // 4 de 5 = 80 %
        u.getNotas().put(2, 4);   // 4 de 5 = 80 %
        u.getFechas().put(0, LocalDate.of(2026, 11, 20));
        return u;
    }

    @Test void hayUnaFilaPorEvaluacion() {
        List<FilaReporte> filas = servicio.filas(usuarioConNotas(), contenido.evaluaciones());
        assertEquals(contenido.evaluaciones().size(), filas.size());
        assertTrue(filas.get(0).completada());
        assertEquals(90, filas.get(0).porcentaje());
        assertEquals("20 nov 2026", filas.get(0).fecha());
        assertEquals(false, filas.get(5).completada());
    }
    @Test void resumenCuentaCompletadasPromedioYMejor() {
        ReporteService.Resumen r = servicio.resumen(servicio.filas(usuarioConNotas(), contenido.evaluaciones()));
        assertEquals(3, r.completadas());
        assertEquals(6, r.total());
        assertEquals(3, r.pendientes());
        assertEquals(90, r.mejor());
        assertEquals(83, r.promedio());
    }
    @Test void sinEvaluacionesElPromedioEsMenosUno() {
        Usuario u = new Usuario("Ana", "ana@uta.edu.ec", "123456", false);
        ReporteService.Resumen r = servicio.resumen(servicio.filas(u, contenido.evaluaciones()));
        assertEquals(0, r.completadas());
        assertEquals(-1, r.promedio());
    }
    @Test void fechasEnEspanol() {
        assertEquals("25 nov 2026", ReporteService.fechaCorta(LocalDate.of(2026, 11, 25)));
        assertEquals("5 ene 2027", ReporteService.fechaCorta(LocalDate.of(2027, 1, 5)));
        assertEquals("25 NOV 2026", ReporteService.fechaMayusculas(LocalDate.of(2026, 11, 25)));
    }
    @Test void elPdfEsValidoYTraeLosDatosDeLaCuenta() {
        byte[] pdf = servicio.generar(usuarioConNotas(), contenido.evaluaciones(), LocalDate.of(2026, 11, 25));
        String txt = new String(pdf, StandardCharsets.ISO_8859_1);
        assertTrue(txt.startsWith("%PDF-1.4"));
        assertTrue(txt.trim().endsWith("%%EOF"));
        assertTrue(txt.contains("(Evaluaciones)"));
        assertTrue(txt.contains("(Javi Garc\\351s)"));
        assertTrue(txt.contains("(3 de 6 completadas)"));
        assertTrue(txt.contains("(25 NOV 2026)"));
    }
    @Test void elTextoConParentesisSeEscapa() {
        Usuario u = new Usuario("Ana (UTA)", "ana@uta.edu.ec", "123456", false);
        String txt = new String(servicio.generar(u, contenido.evaluaciones(), LocalDate.of(2026, 11, 25)), StandardCharsets.ISO_8859_1);
        assertTrue(txt.contains("Ana \\(UTA\\)"));
    }
    @Test void elInvitadoTambienGeneraReporte() {
        Usuario u = new Usuario("Invitado", "invitado@sin-guardar", "", true);
        byte[] pdf = servicio.generar(u, contenido.evaluaciones(), LocalDate.of(2026, 11, 25));
        assertTrue(pdf.length > 1000);
    }
}
