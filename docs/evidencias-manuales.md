# Evidencias manuales (primero a mano, después en código)

La guía pide resolver la lógica **en papel** antes de programar y guardar las hojas fotografiadas o escaneadas en esta carpeta (`docs/`). Este documento es el **modelo de contenido** de cada hoja: copien a mano el análisis, el pseudocódigo, el diagrama y la prueba de escritorio, fotografíenlas y súbanlas con nombres como `manual-seleccion-1.jpg`.

Los diagramas de flujo digitales de referencia están en `docs/diagramas/` (`flujo_seleccion`, `flujo_suma`, `flujo_validacion`).

---

## Práctica 1 · Selección (edad)

**1. Análisis.** Problema: decir si una persona es mayor de edad. Usuario: estudiante principiante. Objetivo: entender el `if / else`. Entrada: `edad` (0 a 120). Proceso: comparar `edad >= 18`. Salida: mensaje. Restricción: dato no vacío, numérico y dentro del rango.

**2. Pseudocódigo.**

```
INICIO
  LEER edad
  SI edad < 0 O edad > 120 ENTONCES
    ESCRIBIR "Fuera de rango"
  SINO
    SI edad >= 18 ENTONCES
      ESCRIBIR "Mayor de edad"
    SINO
      ESCRIBIR "Menor de edad"
    FIN SI
  FIN SI
FIN
```

**3. Decisiones.** Diagrama de flujo: `docs/diagramas/flujo_seleccion.png`. Tabla de decisión:

| Condición / acción | R1 | R2 | R3 |
|---|---|---|---|
| ¿edad < 0 o edad > 120? | Sí | No | No |
| ¿edad >= 18? | — | Sí | No |
| Fuera de rango | X | | |
| Mayor de edad | | X | |
| Menor de edad | | | X |

**4. Prueba de escritorio.**

| Tipo | edad | ¿fuera de rango? | ¿edad >= 18? | Salida esperada |
|---|---|---|---|---|
| Normal | 20 | No | Sí | Mayor de edad |
| Límite | 0 | No | No | Menor de edad |
| Inválido | 121 | Sí | — | Fuera de rango |

**5. Paso a código.** Clase `PracticaService`, método `procesar` (valida) y `calcular` (caso `"seleccion"`).

**6. Comparación.** Esperado vs. obtenido en `docs/pruebas.md` (CP-01 a CP-06).

---

## Práctica 2 · Ciclo for (suma de 1 hasta n)

**1. Análisis.** Problema: sumar los números de 1 hasta `n`. Entrada: `n` (1 a 100). Proceso: acumulador `suma += i` en un ciclo. Salida: la suma. Restricción: `n` entero dentro del rango.

**2. Pseudocódigo.**

```
INICIO
  LEER n
  SI n < 1 O n > 100 ENTONCES
    ESCRIBIR "Fuera de rango"
  SINO
    suma <- 0
    PARA i <- 1 HASTA n HACER
      suma <- suma + i
    FIN PARA
    ESCRIBIR suma
  FIN SI
FIN
```

**3. Decisiones.** Diagrama: `docs/diagramas/flujo_suma.png`.

**4. Prueba de escritorio (n = 3, caso normal).**

| Paso | i | ¿i <= n? | suma |
|---|---|---|---|
| Inicio | — | — | 0 |
| Vuelta 1 | 1 | Sí | 1 |
| Vuelta 2 | 2 | Sí | 3 |
| Vuelta 3 | 3 | Sí | 6 |
| Fin del ciclo | 4 | No | 6 |

Otros casos: límite `n = 1` → 1; límite `n = 100` → 5050; inválido `n = 0` → «Fuera de rango».

**5. Paso a código.** `PracticaService.calcular`, caso `"suma"`.

**6. Comparación.** `docs/pruebas.md` (CP-07 a CP-09).

---

## Práctica 3 · Validación (nota)

**1. Análisis.** Problema: decir si una nota aprueba. Entrada: `nota` (0 a 10). Proceso: validar el rango y comparar `nota >= 7`. Salida: «Aprobado», «Reprobado» o «Fuera de rango».

**2. Pseudocódigo.**

```
INICIO
  LEER nota
  SI nota < 0 O nota > 10 ENTONCES
    ESCRIBIR "Fuera de rango"
  SINO SI nota >= 7 ENTONCES
    ESCRIBIR "Aprobado"
  SINO
    ESCRIBIR "Reprobado"
  FIN SI
FIN
```

**3. Decisiones.** Diagrama: `docs/diagramas/flujo_validacion.png`. Tabla de decisión:

| Condición / acción | R1 | R2 | R3 |
|---|---|---|---|
| ¿nota < 0 o nota > 10? | Sí | No | No |
| ¿nota >= 7? | — | Sí | No |
| Fuera de rango | X | | |
| Aprobado | | X | |
| Reprobado | | | X |

**4. Prueba de escritorio.**

| Tipo | nota | ¿fuera de rango? | ¿nota >= 7? | Salida esperada |
|---|---|---|---|---|
| Normal | 8 | No | Sí | Aprobado |
| Límite inferior de aprobado | 7 | No | Sí | Aprobado |
| Límite superior | 10 | No | Sí | Aprobado |
| Inválido | 11 | Sí | — | Fuera de rango |

**5. Paso a código.** `PracticaService.calcular`, caso `"validacion"`.

**6. Comparación.** `docs/pruebas.md` (CP-10 a CP-12).

---

## Bosquejo mínimo

Cinco pantallas de baja fidelidad: `docs/bosquejos/` (inicio, listado de temas, lección, práctica, evaluación y resultados). Dibújenlas también a mano y súbanlas junto con estas hojas.
