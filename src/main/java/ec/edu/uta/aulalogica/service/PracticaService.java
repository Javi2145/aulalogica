package ec.edu.uta.aulalogica.service;

import ec.edu.uta.aulalogica.model.Ejercicio;
import ec.edu.uta.aulalogica.model.Resultado;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

/** Valida el dato y ejecuta en Java la lógica de cada práctica. */
@Service
public class PracticaService {

    public Resultado procesar(Ejercicio e, String crudo) {
        String raw = crudo == null ? "" : crudo.trim();
        if (raw.isEmpty()) {
            return Resultado.error("El dato está vacío. Escribe un número para continuar.");
        }
        BigDecimal valor;
        try {
            valor = new BigDecimal(raw);
        } catch (NumberFormatException ex) {
            return Resultado.error("“" + raw + "” no es un número. Escribe solo dígitos.");
        }
        if (e.entero() && valor.stripTrailingZeros().scale() > 0) {
            return Resultado.error("Escribe un número entero, sin decimales.");
        }
        if (valor.compareTo(BigDecimal.valueOf(e.min())) < 0 || valor.compareTo(BigDecimal.valueOf(e.max())) > 0) {
            return Resultado.error("Fuera de rango: usa un valor entre " + e.min() + " y " + e.max() + ".");
        }
        return Resultado.ok(calcular(e.id(), valor));
    }

    private String calcular(String id, BigDecimal valor) {
        double v = valor.doubleValue();
        int n = valor.intValue();
        String txt = valor.stripTrailingZeros().toPlainString();
        switch (id) {
            case "seleccion":
                if (v >= 18) return "Mayor de edad: la condición edad >= 18 es verdadera.";
                return "Menor de edad: la condición edad >= 18 es falsa y se ejecuta el else.";
            case "suma": {
                long suma = 0;
                for (int i = 1; i <= n; i++) suma += i;
                return "La suma de 1 hasta " + txt + " es " + suma + " (el for dio " + txt + " vueltas).";
            }
            case "validacion":
                if (v >= 7) return "Aprobado: " + txt + " es mayor o igual que 7.";
                return "Reprobado: " + txt + " es menor que 7.";
            case "parimpar":
                if (n % 2 == 0) return n + " es par: el residuo de dividir entre 2 es 0.";
                return n + " es impar: el residuo de dividir entre 2 es 1.";
            case "tabla": {
                List<String> filas = new ArrayList<>();
                for (int i = 1; i <= 10; i++) filas.add(n + " x " + i + " = " + (n * i));
                return String.join("\n", filas);
            }
            case "factorial": {
                long fact = 1;
                for (int i = 2; i <= n; i++) fact *= i;
                return "El factorial de " + n + " es " + fact + " (multiplicó de 2 hasta " + n + ").";
            }
            case "regresiva": {
                StringBuilder sb = new StringBuilder();
                for (int i = n; i > 0; i--) sb.append(i).append(' ');
                return sb + "¡Fin!";
            }
            case "poo": {
                Cuenta c = new Cuenta();
                c.depositar(n);
                if (n > 0) {
                    return "Se creó el objeto Cuenta con saldo inicial 100. Al llamar depositar(" + n + ") el saldo quedó en "
                        + c.getSaldo() + " (saldo es private: solo cambia mediante el método).";
                }
                return "depositar(0) no cambió nada: el método solo acepta montos mayores que 0, así que el saldo sigue en "
                    + c.getSaldo() + " (encapsulamiento).";
            }
            default:
                return "Práctica no disponible.";
        }
    }

    /** Clase de apoyo de la práctica "Objetos (POO)": el saldo es privado y solo cambia con depositar. */
    private static class Cuenta {
        private int saldo = 100;

        void depositar(int monto) {
            if (monto > 0) saldo += monto;
        }

        int getSaldo() {
            return saldo;
        }
    }
}
