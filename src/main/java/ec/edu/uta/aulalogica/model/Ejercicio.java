package ec.edu.uta.aulalogica.model;

/** Práctica en Java: código mostrado, rango válido y si exige entero. */
public record Ejercicio(String id, String nombre, String etiqueta, String codigo,
                        int min, int max, boolean entero) {

    /** Acceso estilo JavaBean para las plantillas Thymeleaf. */
    public String getId() { return id; }

    /** Acceso estilo JavaBean para las plantillas Thymeleaf. */
    public String getNombre() { return nombre; }

    /** Acceso estilo JavaBean para las plantillas Thymeleaf. */
    public String getEtiqueta() { return etiqueta; }

    /** Acceso estilo JavaBean para las plantillas Thymeleaf. */
    public String getCodigo() { return codigo; }

    /** Acceso estilo JavaBean para las plantillas Thymeleaf. */
    public int getMin() { return min; }

    /** Acceso estilo JavaBean para las plantillas Thymeleaf. */
    public int getMax() { return max; }

    /** Acceso estilo JavaBean para las plantillas Thymeleaf. */
    public boolean isEntero() { return entero; }
}
