package ec.edu.uta.aulalogica.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/** Usuario en memoria con su progreso. */
public class Usuario implements Serializable {
    private String nombre;
    private final String correo;
    private String clave;
    private final boolean invitado;
    private final Set<Integer> leccionesHechas = new HashSet<>();
    private final Map<Integer, Intento> teclado = new HashMap<>();
    private final Map<Integer, Integer> notas = new HashMap<>();

    public Usuario(String nombre, String correo, String clave, boolean invitado) {
        this.nombre = nombre;
        this.correo = correo;
        this.clave = clave;
        this.invitado = invitado;
    }

    /** Mejor intento de un nivel de mecanografía. */
    public static class Intento implements Serializable {
        public int puntaje;
        public int precision;
        public boolean superado;
        public Intento(int puntaje, int precision, boolean superado) {
            this.puntaje = puntaje; this.precision = precision; this.superado = superado;
        }
        public int getPuntaje() { return puntaje; }
        public int getPrecision() { return precision; }
        public boolean isSuperado() { return superado; }
    }

    public boolean leccionHecha(int i) { return leccionesHechas.contains(i); }

    /** El nivel 1 siempre está abierto; los demás exigen superar el anterior. */
    public boolean nivelAbierto(int i) {
        if (i == 0) return true;
        Intento previo = teclado.get(i - 1);
        return previo != null && previo.superado;
    }

    public boolean nivelSuperado(int i) {
        Intento t = teclado.get(i);
        return t != null && t.superado;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCorreo() { return correo; }
    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }
    public boolean isInvitado() { return invitado; }
    public Set<Integer> getLeccionesHechas() { return leccionesHechas; }
    public Map<Integer, Intento> getTeclado() { return teclado; }
    public Map<Integer, Integer> getNotas() { return notas; }
}
