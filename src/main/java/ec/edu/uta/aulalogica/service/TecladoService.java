package ec.edu.uta.aulalogica.service;

import org.springframework.stereotype.Service;

/** Reglas de puntaje de Mecanografía (10 por acierto, -5 por error, 90 % para pasar). */
@Service
public class TecladoService {
    public static final int PRECISION_MINIMA = 90;

    public int puntaje(int aciertos, int errores) {
        return Math.max(0, aciertos * 10 - errores * 5);
    }

    public int precision(int aciertos, int errores) {
        int total = aciertos + errores;
        return total == 0 ? 100 : (int) Math.round(aciertos * 100.0 / total);
    }

    public boolean supera(int precision) {
        return precision >= PRECISION_MINIMA;
    }
}
