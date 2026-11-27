package ec.edu.uta.aulalogica.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ec.edu.uta.aulalogica.model.Resultado;
import org.junit.jupiter.api.Test;

/** Casos normales, límites e inválidos de las ocho prácticas (ver docs/pruebas.md). */
class PracticaServiceTest {
    private final ContenidoService contenido = new ContenidoService();
    private final PracticaService servicio = new PracticaService();

    private Resultado probar(int practica, String dato) {
        return servicio.procesar(contenido.ejercicio(practica), dato);
    }

    @Test void seleccionNormalAdulto() {
        assertEquals("Mayor de edad: la condición edad >= 18 es verdadera.", probar(0, "20").mensaje());
    }
    @Test void seleccionLimiteInferior() {
        assertFalse(probar(0, "0").error());
    }
    @Test void seleccionLimiteSuperior() {
        assertFalse(probar(0, "120").error());
    }
    @Test void seleccionFueraDeRango() {
        assertTrue(probar(0, "121").error());
    }
    @Test void seleccionVacio() {
        assertEquals("El dato está vacío. Escribe un número para continuar.", probar(0, "").mensaje());
    }
    @Test void seleccionTexto() {
        assertEquals("“abc” no es un número. Escribe solo dígitos.", probar(0, "abc").mensaje());
    }
    @Test void sumaNormal() {
        assertEquals("La suma de 1 hasta 10 es 55 (el for dio 10 vueltas).", probar(1, "10").mensaje());
    }
    @Test void sumaLimiteSuperior() {
        assertEquals("La suma de 1 hasta 100 es 5050 (el for dio 100 vueltas).", probar(1, "100").mensaje());
    }
    @Test void sumaFueraDeRango() {
        assertEquals("Fuera de rango: usa un valor entre 1 y 100.", probar(1, "0").mensaje());
    }
    @Test void validacionAprobado() {
        assertEquals("Aprobado: 7 es mayor o igual que 7.", probar(2, "7").mensaje());
    }
    @Test void validacionReprobado() {
        assertEquals("Reprobado: 6.9 es menor que 7.", probar(2, "6.9").mensaje());
    }
    @Test void validacionFueraDeRango() {
        assertTrue(probar(2, "11").error());
    }
    @Test void parNormal() {
        assertEquals("4 es par: el residuo de dividir entre 2 es 0.", probar(3, "4").mensaje());
    }
    @Test void imparNormal() {
        assertEquals("7 es impar: el residuo de dividir entre 2 es 1.", probar(3, "7").mensaje());
    }
    @Test void parImparRechazaDecimales() {
        assertEquals("Escribe un número entero, sin decimales.", probar(3, "3.2").mensaje());
    }
    @Test void tablaTieneDiezFilas() {
        assertEquals(10, probar(4, "7").mensaje().split("\n").length);
    }
    @Test void factorialCeroEsUno() {
        assertEquals("El factorial de 0 es 1 (multiplicó de 2 hasta 0).", probar(5, "0").mensaje());
    }
    @Test void factorialLimiteSuperior() {
        assertEquals("El factorial de 12 es 479001600 (multiplicó de 2 hasta 12).", probar(5, "12").mensaje());
    }
    @Test void cuentaRegresiva() {
        assertEquals("5 4 3 2 1 ¡Fin!", probar(6, "5").mensaje());
    }
    @Test void pooDepositoNormal() {
        assertEquals("Se creó el objeto Cuenta con saldo inicial 100. Al llamar depositar(50) el saldo quedó en 150 (saldo es private: solo cambia mediante el método).", probar(7, "50").mensaje());
    }
    @Test void pooLimiteSuperior() {
        assertTrue(probar(7, "1000").mensaje().contains("1100"));
    }
    @Test void pooMontoCeroNoCambiaElSaldo() {
        assertEquals("depositar(0) no cambió nada: el método solo acepta montos mayores que 0, así que el saldo sigue en 100 (encapsulamiento).", probar(7, "0").mensaje());
    }
    @Test void pooFueraDeRangoYDecimales() {
        assertTrue(probar(7, "1001").error());
        assertTrue(probar(7, "-5").error());
        assertEquals("Escribe un número entero, sin decimales.", probar(7, "2.5").mensaje());
    }
}
