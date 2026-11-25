package ec.edu.uta.aulalogica.service;

import ec.edu.uta.aulalogica.model.Usuario;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

/** Registro e inicio de sesión con usuarios en memoria (sin base de datos). */
@Service
public class UsuarioService {
    private final Map<String, Usuario> usuarios = new ConcurrentHashMap<>();

    /** @return mensaje de error o null si se registró. */
    public String validarRegistro(String nombre, String correo, String clave) {
        if (vacio(nombre) || vacio(correo) || vacio(clave)) return "Completa nombre, correo y contraseña.";
        String e = correo.trim().toLowerCase();
        if (!correoValido(e)) return "Escribe un correo válido, por ejemplo nombre@uta.edu.ec.";
        if (clave.length() < 6) return "La contraseña necesita al menos 6 caracteres.";
        if (usuarios.containsKey(e)) return "Ese correo ya está registrado. Pulsa “Iniciar sesión”.";
        return null;
    }

    public Usuario registrar(String nombre, String correo, String clave) {
        String e = correo.trim().toLowerCase();
        Usuario u = new Usuario(nombre.trim(), e, clave, false);
        usuarios.put(e, u);
        return u;
    }

    /** @return mensaje de error o null si los datos son válidos. */
    public String validarIngreso(String correo, String clave) {
        if (vacio(correo) || vacio(clave)) return "Escribe tu correo y contraseña.";
        String e = correo.trim().toLowerCase();
        if (!correoValido(e)) return "Escribe un correo válido, por ejemplo nombre@uta.edu.ec.";
        Usuario u = usuarios.get(e);
        if (u == null || !u.getClave().equals(clave)) return "Correo o contraseña incorrectos.";
        return null;
    }

    public Usuario ingresar(String correo) {
        return usuarios.get(correo.trim().toLowerCase());
    }

    public Usuario invitado() {
        return new Usuario("Invitado", "invitado@sin-guardar", "", true);
    }

    private static boolean vacio(String s) { return s == null || s.trim().isEmpty(); }

    private static boolean correoValido(String e) {
        return e.indexOf('@') >= 1 && e.indexOf('.') >= 0;
    }
}
