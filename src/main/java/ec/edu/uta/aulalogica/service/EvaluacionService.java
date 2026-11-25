package ec.edu.uta.aulalogica.service;

import ec.edu.uta.aulalogica.model.Evaluacion;
import java.util.List;
import org.springframework.stereotype.Service;

/** Calcula puntajes de las evaluaciones. */
@Service
public class EvaluacionService {

    /** Cuenta aciertos; una respuesta null (o -1) significa "sin responder". */
    public int puntaje(Evaluacion ev, List<Integer> respuestas) {
        int s = 0;
        for (int i = 0; i < ev.preguntas().size(); i++) {
            Integer r = i < respuestas.size() ? respuestas.get(i) : null;
            if (r != null && r == ev.preguntas().get(i).correcta()) s++;
        }
        return s;
    }

    public double sobreDiez(int aciertos, int total) {
        return Math.round(aciertos * 100.0 / total) / 10.0;
    }
}
