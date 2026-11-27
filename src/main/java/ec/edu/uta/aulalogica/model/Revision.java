package ec.edu.uta.aulalogica.model;

/** Línea de revisión de una pregunta al terminar la evaluación. */
public record Revision(int numero, String enunciado, String tuRespuesta, boolean correcta,
                       String respuestaCorrecta, String retroalimentacion) {

    /** Acceso estilo JavaBean para las plantillas Thymeleaf. */
    public int getNumero() { return numero; }

    /** Acceso estilo JavaBean para las plantillas Thymeleaf. */
    public String getEnunciado() { return enunciado; }

    /** Acceso estilo JavaBean para las plantillas Thymeleaf. */
    public String getTuRespuesta() { return tuRespuesta; }

    /** Acceso estilo JavaBean para las plantillas Thymeleaf. */
    public boolean isCorrecta() { return correcta; }

    /** Acceso estilo JavaBean para las plantillas Thymeleaf. */
    public String getRespuestaCorrecta() { return respuestaCorrecta; }

    /** Acceso estilo JavaBean para las plantillas Thymeleaf. */
    public String getRetroalimentacion() { return retroalimentacion; }
}
