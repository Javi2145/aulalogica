package ec.edu.uta.aulalogica.model;

/** Crédito de un recurso de terceros usado en el aplicativo (RF-07). */
public record Credito(String recurso, String uso, String licencia, String enlace) {

    /** Acceso estilo JavaBean para las plantillas Thymeleaf. */
    public String getRecurso() { return recurso; }

    /** Acceso estilo JavaBean para las plantillas Thymeleaf. */
    public String getUso() { return uso; }

    /** Acceso estilo JavaBean para las plantillas Thymeleaf. */
    public String getLicencia() { return licencia; }

    /** Acceso estilo JavaBean para las plantillas Thymeleaf. */
    public String getEnlace() { return enlace; }
}
