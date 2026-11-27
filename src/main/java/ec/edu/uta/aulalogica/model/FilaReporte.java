package ec.edu.uta.aulalogica.model;

/** Una evaluación dentro del reporte PDF: título, estado, fecha y porcentaje obtenido. */
public record FilaReporte(int numero, String titulo, int preguntas, boolean completada,
                          String fecha, int porcentaje) {

    /** Acceso estilo JavaBean para las plantillas Thymeleaf. */
    public int getNumero() { return numero; }

    /** Acceso estilo JavaBean para las plantillas Thymeleaf. */
    public String getTitulo() { return titulo; }

    /** Acceso estilo JavaBean para las plantillas Thymeleaf. */
    public int getPreguntas() { return preguntas; }

    /** Acceso estilo JavaBean para las plantillas Thymeleaf. */
    public boolean isCompletada() { return completada; }

    /** Acceso estilo JavaBean para las plantillas Thymeleaf. */
    public String getFecha() { return fecha; }

    /** Acceso estilo JavaBean para las plantillas Thymeleaf. */
    public int getPorcentaje() { return porcentaje; }
}
