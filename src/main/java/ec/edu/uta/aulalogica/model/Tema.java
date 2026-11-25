package ec.edu.uta.aulalogica.model;

import java.text.Normalizer;
import java.util.List;

/** Lección del módulo "Contenidos": explicación breve y una pregunta de comprobación. */
public record Tema(String titulo, String subtitulo, String explicacion,
                   String pregunta, List<String> opciones, int correcta) {

    /** Texto en minúsculas y sin tildes, usado por el buscador. */
    public String clave() {
        return normalizar(titulo + " " + subtitulo);
    }

    public static String normalizar(String s) {
        return Normalizer.normalize(s.toLowerCase(), Normalizer.Form.NFD).replaceAll("\\p{M}", "");
    }

    /** Acceso estilo JavaBean para las plantillas Thymeleaf. */
    public String getTitulo() { return titulo; }

    /** Acceso estilo JavaBean para las plantillas Thymeleaf. */
    public String getSubtitulo() { return subtitulo; }

    /** Acceso estilo JavaBean para las plantillas Thymeleaf. */
    public String getExplicacion() { return explicacion; }

    /** Acceso estilo JavaBean para las plantillas Thymeleaf. */
    public String getPregunta() { return pregunta; }

    /** Acceso estilo JavaBean para las plantillas Thymeleaf. */
    public List<String> getOpciones() { return opciones; }

    /** Acceso estilo JavaBean para las plantillas Thymeleaf. */
    public int getCorrecta() { return correcta; }
}
