package ec.edu.uta.aulalogica.model;

/** Concepto del glosario (RF-06). */
public record Termino(String termino, String definicion) {

    /** Acceso estilo JavaBean para las plantillas Thymeleaf. */
    public String getTermino() { return termino; }

    /** Acceso estilo JavaBean para las plantillas Thymeleaf. */
    public String getDefinicion() { return definicion; }
}
