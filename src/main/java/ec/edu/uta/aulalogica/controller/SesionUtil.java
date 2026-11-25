package ec.edu.uta.aulalogica.controller;

import ec.edu.uta.aulalogica.model.Usuario;
import jakarta.servlet.http.HttpSession;

final class SesionUtil {
    static final String CLAVE = "usuario";

    private SesionUtil() { }

    static Usuario usuario(HttpSession s) {
        return (Usuario) s.getAttribute(CLAVE);
    }
}
