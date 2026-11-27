package ec.edu.uta.aulalogica.model;

/** Resumen del avance del usuario. */
public record Progreso(int lecciones, int totalLecciones, int niveles, int totalNiveles,
                       int evaluaciones, int totalEvaluaciones, int mejorTeclado) {
    public int pct(int x, int y) { return y == 0 ? 0 : x * 100 / y; }

    /** Acceso estilo JavaBean para las plantillas Thymeleaf. */
    public int getLecciones() { return lecciones; }

    /** Acceso estilo JavaBean para las plantillas Thymeleaf. */
    public int getTotalLecciones() { return totalLecciones; }

    /** Acceso estilo JavaBean para las plantillas Thymeleaf. */
    public int getNiveles() { return niveles; }

    /** Acceso estilo JavaBean para las plantillas Thymeleaf. */
    public int getTotalNiveles() { return totalNiveles; }

    /** Acceso estilo JavaBean para las plantillas Thymeleaf. */
    public int getEvaluaciones() { return evaluaciones; }

    /** Acceso estilo JavaBean para las plantillas Thymeleaf. */
    public int getTotalEvaluaciones() { return totalEvaluaciones; }

    /** Acceso estilo JavaBean para las plantillas Thymeleaf. */
    public int getMejorTeclado() { return mejorTeclado; }
}
