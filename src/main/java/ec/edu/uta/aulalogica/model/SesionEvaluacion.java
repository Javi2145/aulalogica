package ec.edu.uta.aulalogica.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** Intento de evaluación en curso: guarda las respuestas elegidas en la sesión. */
public class SesionEvaluacion implements Serializable {
    private final int evaluacion;
    private final List<Integer> respuestas = new ArrayList<>();

    public SesionEvaluacion(int evaluacion, int totalPreguntas) {
        this.evaluacion = evaluacion;
        for (int i = 0; i < totalPreguntas; i++) respuestas.add(null);
    }

    public int getEvaluacion() { return evaluacion; }
    public List<Integer> getRespuestas() { return respuestas; }

    public void responder(int pregunta, int opcion) {
        if (pregunta >= 0 && pregunta < respuestas.size()) respuestas.set(pregunta, opcion);
    }

    public int contestadas() {
        int n = 0;
        for (Integer r : respuestas) if (r != null) n++;
        return n;
    }
}
