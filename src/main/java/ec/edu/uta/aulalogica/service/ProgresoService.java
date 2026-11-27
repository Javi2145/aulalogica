package ec.edu.uta.aulalogica.service;

import ec.edu.uta.aulalogica.model.Progreso;
import ec.edu.uta.aulalogica.model.Usuario;
import org.springframework.stereotype.Service;

@Service
public class ProgresoService {
    private final ContenidoService contenido;

    public ProgresoService(ContenidoService contenido) {
        this.contenido = contenido;
    }

    public Progreso resumen(Usuario u) {
        int niveles = 0, mejor = 0;
        for (Usuario.Intento t : u.getTeclado().values()) {
            if (t.isSuperado()) niveles++;
            mejor = Math.max(mejor, t.getPuntaje());
        }
        return new Progreso(u.getLeccionesHechas().size(), contenido.temas().size(),
            niveles, contenido.niveles().size(),
            u.getNotas().size(), contenido.evaluaciones().size(), mejor);
    }
}
