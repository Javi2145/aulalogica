# Casos de prueba

Resultados obtenidos al ejecutar `PracticaService` en Java (automatizados en `PracticaServiceTest`; el reporte PDF en `ReporteServiceTest`).
Las columnas «Pasa» deben volver a verificarse en el navegador (http://localhost:8080) y adjuntarse con capturas.

| Caso | Requisito | Práctica | Entrada | Esperado | Obtenido | ¿Pasa? |
|---|---|---|---|---|---|---|
| CP-01 | RF-04 | Selección | Edad = 20 | Mayor de edad | Mayor de edad: la condición edad >= 18 es verdadera. | Sí |
| CP-02 | RF-04 | Selección | Edad = 0 (límite inferior) | Se acepta | Menor de edad: la condición edad >= 18 es falsa y se ejecuta el else. | Sí |
| CP-03 | RF-04 | Selección | Edad = 120 (límite superior) | Se acepta | Mayor de edad: la condición edad >= 18 es verdadera. | Sí |
| CP-04 | RF-08 | Selección | Edad = 121 | Error de rango | Fuera de rango: usa un valor entre 0 y 120. | Sí |
| CP-05 | RF-08 | Selección | Edad vacía | Mensaje y conserva la página | El dato está vacío. Escribe un número para continuar. | Sí |
| CP-06 | RF-08 | Selección | Edad = abc | Mensaje de texto inválido | “abc” no es un número. Escribe solo dígitos. | Sí |
| CP-07 | RF-04 | Suma (for) | n = 10 | 55 | La suma de 1 hasta 10 es 55 (el for dio 10 vueltas). | Sí |
| CP-08 | RF-04 | Suma (for) | n = 100 (límite superior) | 5050 | La suma de 1 hasta 100 es 5050 (el for dio 100 vueltas). | Sí |
| CP-09 | RF-08 | Suma (for) | n = 0 | Error de rango | Fuera de rango: usa un valor entre 1 y 100. | Sí |
| CP-10 | RF-04 | Validación | Nota = 7 (límite aprobado) | Aprobado | Aprobado: 7 es mayor o igual que 7. | Sí |
| CP-11 | RF-04 | Validación | Nota = 6.9 | Reprobado | Reprobado: 6.9 es menor que 7. | Sí |
| CP-12 | RF-08 | Validación | Nota = 11 | Error de rango | Fuera de rango: usa un valor entre 0 y 10. | Sí |
| CP-13 | RF-04 | Par o impar | n = 4 | Par | 4 es par: el residuo de dividir entre 2 es 0. | Sí |
| CP-14 | RF-04 | Par o impar | n = 7 | Impar | 7 es impar: el residuo de dividir entre 2 es 1. | Sí |
| CP-15 | RF-08 | Par o impar | n = 3.2 | Exige entero | Escribe un número entero, sin decimales. | Sí |
| CP-16 | RF-04 | Factorial | n = 0 (límite inferior) | 1 | El factorial de 0 es 1 (multiplicó de 2 hasta 0). | Sí |
| CP-17 | RF-04 | Factorial | n = 12 (límite superior) | 479001600 | El factorial de 12 es 479001600 (multiplicó de 2 hasta 12). | Sí |
| CP-18 | RF-04 | Cuenta regresiva | n = 5 | 5 4 3 2 1 ¡Fin! | 5 4 3 2 1 ¡Fin! | Sí |
| CP-23 | RF-04 | Objetos (POO) | Monto = 50 | Saldo 150 | Se creó el objeto Cuenta con saldo inicial 100. Al llamar depositar(50) el saldo quedó en 150 (saldo es private: solo cambia mediante el método). | Sí |
| CP-24 | RF-04 | Objetos (POO) | Monto = 0 (límite inferior) | Saldo sin cambios | depositar(0) no cambió nada: el método solo acepta montos mayores que 0, así que el saldo sigue en 100 (encapsulamiento). | Sí |
| CP-25 | RF-08 | Objetos (POO) | Monto = 1001 | Error de rango | Fuera de rango: usa un valor entre 0 y 1000. | Sí |

## Mecanografía y evaluaciones

| Caso | Entrada | Esperado | Estado |
|---|---|---|---|
| CP-19 | 20 aciertos, 2 errores | 190 puntos, 91 %, nivel superado | Verificado en Java |
| CP-20 | Evaluación general con 9 de 10 correctas | Nota 9.0 sobre 10 | Verificado en Java |
| CP-21 | Registro con correo `ana` | «Escribe un correo válido…» | Verificado en Java |
| CP-22 | Registro con contraseña de 3 caracteres | «La contraseña necesita al menos 6 caracteres.» | Verificado en Java |
| CP-26 | Reporte PDF con 3 de 6 evaluaciones hechas (90 %, 80 % y 80 %) | Promedio 83, mejor 90, 3 por completar | Verificado en Java |
| CP-27 | Reporte PDF de un usuario sin evaluaciones | Promedio «-», 0 completadas, 6 pendientes | Verificado en Java |
