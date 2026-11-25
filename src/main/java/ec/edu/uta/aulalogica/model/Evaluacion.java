package ec.edu.uta.aulalogica.model;

import java.util.List;

public record Evaluacion(String titulo, List<Pregunta> preguntas) {

    /** Acceso estilo JavaBean para las plantillas Thymeleaf. */
    public String getTitulo() { return titulo; }

    /** Acceso estilo JavaBean para las plantillas Thymeleaf. */
    public List<Pregunta> getPreguntas() { return preguntas; }
}
