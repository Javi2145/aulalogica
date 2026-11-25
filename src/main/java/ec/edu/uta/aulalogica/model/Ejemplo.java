package ec.edu.uta.aulalogica.model;

import java.util.List;

/** Ejemplo resuelto de una lección: enunciado, pasos, código y salida (RF-03). */
public record Ejemplo(String enunciado, List<String> pasos, String codigo, String salida) {

    /** Acceso estilo JavaBean para las plantillas Thymeleaf. */
    public String getEnunciado() { return enunciado; }

    /** Acceso estilo JavaBean para las plantillas Thymeleaf. */
    public List<String> getPasos() { return pasos; }

    /** Acceso estilo JavaBean para las plantillas Thymeleaf. */
    public String getCodigo() { return codigo; }

    /** Acceso estilo JavaBean para las plantillas Thymeleaf. */
    public String getSalida() { return salida; }
}
