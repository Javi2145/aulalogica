package ec.edu.uta.aulalogica.model;

/** Respuesta JSON del servidor al terminar un nivel de mecanografía. */
public record ResultadoTeclado(int puntaje, int precision, boolean supera, boolean haySiguiente, String mensaje) {

    /** Acceso estilo JavaBean para las plantillas Thymeleaf. */
    public int getPuntaje() { return puntaje; }

    /** Acceso estilo JavaBean para las plantillas Thymeleaf. */
    public int getPrecision() { return precision; }

    /** Acceso estilo JavaBean para las plantillas Thymeleaf. */
    public boolean isSupera() { return supera; }

    /** Acceso estilo JavaBean para las plantillas Thymeleaf. */
    public boolean isHaySiguiente() { return haySiguiente; }

    /** Acceso estilo JavaBean para las plantillas Thymeleaf. */
    public String getMensaje() { return mensaje; }
}
