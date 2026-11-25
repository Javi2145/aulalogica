package ec.edu.uta.aulalogica.model;

/** Resultado de procesar una práctica: mensaje y si fue error de validación. */
public record Resultado(boolean error, String mensaje) {
    public static Resultado ok(String m) { return new Resultado(false, m); }
    public static Resultado error(String m) { return new Resultado(true, m); }

    /** Acceso estilo JavaBean para las plantillas Thymeleaf. */
    public boolean isError() { return error; }

    /** Acceso estilo JavaBean para las plantillas Thymeleaf. */
    public String getMensaje() { return mensaje; }
}
