package ec.edu.uta.aulalogica.model;

import java.util.List;

/** Pregunta de una evaluación con su retroalimentación. */
public record Pregunta(String enunciado, List<String> opciones, int correcta, String retroalimentacion) {

    /** Acceso estilo JavaBean para las plantillas Thymeleaf. */
    public String getEnunciado() { return enunciado; }

    /** Acceso estilo JavaBean para las plantillas Thymeleaf. */
    public List<String> getOpciones() { return opciones; }

    /** Acceso estilo JavaBean para las plantillas Thymeleaf. */
    public int getCorrecta() { return correcta; }

    /** Acceso estilo JavaBean para las plantillas Thymeleaf. */
    public String getRetroalimentacion() { return retroalimentacion; }
}
