package ec.edu.uta.aulalogica.service;

import ec.edu.uta.aulalogica.model.Evaluacion;
import ec.edu.uta.aulalogica.model.FilaReporte;
import ec.edu.uta.aulalogica.model.Usuario;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

/** Arma el reporte PDF de evaluaciones de una cuenta (datos del usuario, promedio, mejor puntaje e historial). */
@Service
public class ReporteService {
    /** Zona horaria de las fechas del reporte (Ecuador). */
    public static final ZoneId ZONA = ZoneId.of("America/Guayaquil");

    private static final String[] MESES = {"ene", "feb", "mar", "abr", "may", "jun", "jul", "ago", "sep", "oct", "nov", "dic"};

    // Paleta del reporte (la misma de la muestra entregada)
    private static final int FONDO = 0xF4F8FD, MARINO = 0x0F1D36, BARRA = 0x101E38, HERO = 0x0A1A33;
    private static final int AZUL = 0x1A63E8, AZUL_CLARO = 0xE8F0FE, GRIS = 0x6B7A90, LINEA = 0xDDE3EE;
    private static final int VERDE = 0x17A56B, VERDE_CLARO = 0xE3F6EE, AMBAR = 0xD98A00, AMBAR_CLARO = 0xFFF2DC;
    private static final int BLANCO = 0xFFFFFF;

    /** Cifras de la parte superior del reporte; promedio es -1 si aún no hay evaluaciones. */
    public record Resumen(int completadas, int total, int promedio, int mejor, int pendientes) { }

    /** Una fila por evaluación del curso: las hechas con su mejor resultado y las demás como pendientes. */
    public List<FilaReporte> filas(Usuario u, List<Evaluacion> evaluaciones) {
        List<FilaReporte> filas = new ArrayList<>();
        for (int i = 0; i < evaluaciones.size(); i++) {
            Evaluacion ev = evaluaciones.get(i);
            int total = ev.preguntas().size();
            Integer aciertos = u.getNotas().get(i);
            if (aciertos == null) {
                filas.add(new FilaReporte(i + 1, ev.titulo(), total, false, "-", 0));
            } else {
                LocalDate f = u.getFechas().get(i);
                filas.add(new FilaReporte(i + 1, ev.titulo(), total, true,
                    f == null ? "-" : fechaCorta(f), total == 0 ? 0 : aciertos * 100 / total));
            }
        }
        return filas;
    }

    public Resumen resumen(List<FilaReporte> filas) {
        int hechas = 0, suma = 0, mejor = 0;
        for (FilaReporte f : filas) {
            if (!f.completada()) continue;
            hechas++;
            suma += f.porcentaje();
            mejor = Math.max(mejor, f.porcentaje());
        }
        int promedio = hechas == 0 ? -1 : (int) Math.round(suma / (double) hechas);
        return new Resumen(hechas, filas.size(), promedio, mejor, filas.size() - hechas);
    }

    /** "20 nov 2026" */
    public static String fechaCorta(LocalDate d) {
        return d.getDayOfMonth() + " " + MESES[d.getMonthValue() - 1] + " " + d.getYear();
    }

    /** "25 NOV 2026" */
    public static String fechaMayusculas(LocalDate d) {
        return fechaCorta(d).toUpperCase();
    }

    /** Genera el PDF (una página A4) con los datos actuales de la cuenta. */
    public byte[] generar(Usuario u, List<Evaluacion> evaluaciones, LocalDate hoy) {
        List<FilaReporte> filas = filas(u, evaluaciones);
        Resumen r = resumen(filas);
        PdfBasico p = new PdfBasico();
        String nombre = u.getNombre() == null || u.getNombre().isBlank() ? "Estudiante" : u.getNombre().trim();

        // Fondo, barra superior y portada oscura
        p.rect(0, 0, PdfBasico.ANCHO, PdfBasico.ALTO, FONDO);
        p.rect(0, 0, PdfBasico.ANCHO, 270, HERO);
        p.poligono(0x12315C, 715, 48, 924, 48, 924, 268, 868, 268, 868, 180, 820, 160, 878, 160, 878, 73, 740, 73);
        p.poligono(0x1A4A8A, 868, 180, 924, 232, 924, 268, 868, 268);
        p.rect(0, 0, PdfBasico.ANCHO, 48, BARRA);
        p.rect(0, 268, PdfBasico.ANCHO, 8, AZUL);
        logo(p);
        p.circulo(799, 24, 18, 0xA9CCF8);
        persona(p, 799, 24, 0.55, 0x0F2A57);
        p.textoDerecha(PdfBasico.recortar(nombre, 170, 13, false), 889, 29, 13, false, BLANCO, 0);
        p.poligono(BLANCO, 896, 23, 908, 23, 902, 30);
        p.texto("Evaluaciones", 70, 207, 36, true, BLANCO);
        p.texto("Resumen de resultados y desempeño", 70, 238, 15, false, 0xA9B8D0);
        p.textoDerecha(fechaMayusculas(hoy), 854, 233, 13, false, 0xD6E0F0, 0);

        // Tarjeta de la cuenta
        p.rectRedondeado(62, 263, 800, 105, 20, 0xE9F0FA);
        p.rectRedondeado(62, 260, 800, 105, 20, BLANCO);
        p.circulo(104, 312, 32, AZUL_CLARO);
        persona(p, 104, 312, 1.0, AZUL);
        p.texto(PdfBasico.recortar(nombre, 420, 18, true), 158, 299, 18, true, MARINO);
        p.texto("Estudiante · AulaLógica", 158, 321, 13, false, GRIS);
        String correo = u.isInvitado() ? "Sesión de invitado (no se guarda)" : u.getCorreo();
        p.texto(PdfBasico.recortar(correo, 440, 11.5, false), 158, 341, 11.5, false, 0x8A97AB);
        p.linea(629, 282, 629, 344, 1, LINEA);
        p.texto("EVALUACIONES", 655, 299, 11, false, GRIS, 0.9);
        p.texto(r.completadas() + " de " + r.total() + " completadas", 655, 331, 17, true, AZUL);

        // Tu rendimiento
        p.texto("Tu rendimiento", 70, 423, 18, true, MARINO);
        p.textoDerecha("Datos de tu cuenta", 854, 423, 12, false, GRIS, 0);
        tarjeta(p, 70, "PROMEDIO", AZUL_CLARO, AZUL, r.promedio() < 0 ? "-" : String.valueOf(r.promedio()), "/ 100");
        tarjeta(p, 337, "MEJOR PUNTAJE", VERDE_CLARO, VERDE, r.completadas() == 0 ? "-" : String.valueOf(r.mejor()), "/ 100");
        tarjeta(p, 604, "POR COMPLETAR", AMBAR_CLARO, AMBAR, String.valueOf(r.pendientes()),
            r.pendientes() == 1 ? "evaluación" : "evaluaciones");

        // Historial
        p.texto("Historial de evaluaciones", 70, 612, 18, true, MARINO);
        p.textoDerecha("Resultados recientes", 854, 612, 12, false, GRIS, 0);
        p.rectRedondeado(70, 639, 784, 44, 12, MARINO);
        p.texto("EVALUACIÓN", 92, 666, 10.5, true, BLANCO, 0.9);
        p.texto("FECHA", 405, 666, 10.5, true, BLANCO, 0.9);
        p.texto("RESULTADO", 558, 666, 10.5, true, BLANCO, 0.9);
        p.textoDerecha("PUNTAJE", 831, 666, 10.5, true, BLANCO, 0.9);
        double paso = filas.isEmpty() ? 70 : Math.min(70, 500.0 / filas.size());
        double alto = paso - 6;
        for (int i = 0; i < filas.size(); i++) {
            fila(p, filas.get(i), 689 + i * paso, alto);
        }

        // Pie
        p.linea(70, 1220, 854, 1220, 1, LINEA);
        p.texto("AulaLógica", 70, 1252, 11.5, true, MARINO);
        p.texto("Reporte generado desde el panel de evaluaciones", 180, 1252, 11, false, GRIS);
        p.textoDerecha("PÁGINA 1 / 1", 854, 1252, 10.5, false, GRIS, 0.6);
        return p.generar("Reporte de evaluaciones - AulaLogica - " + nombre);
    }

    private static void tarjeta(PdfBasico p, double x, String etiqueta, int fondo, int texto, String valor, String unidad) {
        p.rectRedondeado(x, 448, 250, 110, 20, 0xE9F0FA);
        p.rectRedondeado(x, 445, 250, 110, 20, BLANCO);
        p.rectRedondeado(x + 14, 458, 222, 26, 9, fondo);
        p.textoCentrado(etiqueta, x + 125, 475, 10, true, texto, 0.8);
        p.textoCentrado(valor, x + 112, 525, 32, true, MARINO, 0);
        p.texto(unidad, x + 155, 521, 11, false, GRIS);
    }

    private static void fila(PdfBasico p, FilaReporte f, double y, double alto) {
        double m = y + alto / 2;
        p.rectRedondeado(70, y, 784, alto, 12, BLANCO);
        p.circulo(96, m, 15, AZUL_CLARO);
        p.textoCentrado(String.format("%02d", f.numero()), 96, m + 4, 11, true, AZUL, 0);
        p.texto(PdfBasico.recortar(f.titulo(), 255, 14, true), 123, m - 2, 14, true, MARINO);
        p.texto("Evaluación individual · " + f.preguntas() + " preguntas", 123, m + 17, 11.5, false, GRIS);
        p.texto(f.fecha(), 405, m + 4, 13, false, MARINO);
        if (f.completada()) {
            p.rectRedondeado(546, m - 13, 105, 26, 13, VERDE_CLARO);
            p.textoCentrado("COMPLETADA", 598.5, m + 4, 9.5, true, VERDE, 0.6);
        } else {
            p.rectRedondeado(546, m - 13, 105, 26, 13, AMBAR_CLARO);
            p.textoCentrado("PENDIENTE", 598.5, m + 4, 9.5, true, AMBAR, 0.6);
        }
        p.rectRedondeado(673, m - 4, 75, 8, 4, 0xDDE3F0);
        if (f.completada() && f.porcentaje() > 0) {
            p.rectRedondeado(673, m - 4, Math.max(8, 75.0 * f.porcentaje() / 100), 8, 4, AZUL);
        }
        p.textoDerecha(f.completada() ? f.porcentaje() + "%" : "-", 831, m + 4, 14, true, f.completada() ? MARINO : GRIS, 0);
    }

    /** Logotipo: birrete + "AulaLógica". */
    private static void logo(PdfBasico p) {
        p.poligono(BLANCO, 54, 6, 80, 16, 54, 26, 28, 16);
        p.poligono(BLANCO, 39, 22, 54, 28.5, 69, 22, 69, 31, 54, 37, 39, 31);
        p.linea(77, 17, 77, 30, 2, BLANCO);
        p.circulo(77, 31, 2.2, BLANCO);
        p.texto("Aula", 90, 32, 25, true, BLANCO);
        p.texto("Lógica", 90 + PdfBasico.ancho("Aula", 25, true, 0), 32, 25, true, 0x2B7BFF);
    }

    /** Silueta de persona (cabeza y hombros) centrada en cx, cy. */
    private static void persona(PdfBasico p, double cx, double cy, double e, int color) {
        p.circulo(cx, cy - 8 * e, 9.5 * e, color);
        p.elipse(cx, cy + 12 * e, 13 * e, 9.5 * e, color);
    }
}
