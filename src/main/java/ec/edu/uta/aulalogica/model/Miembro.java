package ec.edu.uta.aulalogica.model;

/** Integrante del equipo con su rol y responsabilidad principal (RF-07). */
public record Miembro(String nombre, String rol, String responsabilidad) {

    /** Acceso estilo JavaBean para las plantillas Thymeleaf. */
    public String getNombre() { return nombre; }

    /** Acceso estilo JavaBean para las plantillas Thymeleaf. */
    public String getRol() { return rol; }

    /** Acceso estilo JavaBean para las plantillas Thymeleaf. */
    public String getResponsabilidad() { return responsabilidad; }
}
