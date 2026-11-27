# Análisis: problema, usuario y requisitos

## Ficha del problema

| Campo | Descripción |
|---|---|
| Problema | Quien empieza en programación suele memorizar sintaxis sin entender la lógica, y no tiene dónde practicar con una respuesta inmediata que explique el resultado. |
| Usuario objetivo | Estudiante de primer semestre de FISEI (UTA) que inicia Algoritmos y Lógica de Programación y no ha programado antes. |
| Propósito (30 segundos) | Enseñar fundamentos de programación en Java con lecciones breves, ejemplos resueltos, prácticas que ejecuta Java y evaluaciones con retroalimentación. |
| Valor didáctico | Cada práctica muestra el código, procesa el dato en Java y explica por qué se ejecutó una rama del programa. |
| Restricciones | Ejecución local (`http://localhost:8080`), datos en memoria, sin base de datos, Java 21 + Spring Boot + Thymeleaf. |

## Objetivo general

Diseñar, construir, probar y presentar colaborativamente un aplicativo web didáctico en Java que integre los conocimientos del periodo académico y documente las etapas básicas del ciclo de vida del software.

## Requisitos funcionales y trazabilidad

| ID | Función | Dónde está en el proyecto | Estado |
|---|---|---|---|
| RF-01 | Inicio con propósito, recorrido y navegación | `index.html` (portada con descripción), `contenidos.html` (recorrido sugerido), menú lateral y barra superior en `fragments/base.html` | Hecho |
| RF-02 | Seis módulos | `ContenidoService.TEMAS`: 15 lecciones. Algoritmos/EPS → 11; pseudocódigo/diagramas → 12; Java básico → 13 (y 1, 7, 10); condicionales → 3; ciclos → 4, 5, 9; validación y pruebas → 14; programación orientada a objetos → 15 (y 10) | Hecho |
| RF-03 | Lección didáctica | `leccion.html`: explicación breve, ejemplo resuelto, práctica relacionada y pregunta con retroalimentación | Hecho |
| RF-04 | Tres prácticas procesadas con Java | `PracticaService` (8 prácticas: selección, ciclos, validación y objetos incluidas) | Hecho |
| RF-05 | Evaluación de 10 preguntas o más | `EvaluacionController` + `EvaluacionService`; hay 10 evaluaciones de 10 preguntas cada una, con puntaje y retroalimentación | Hecho |
| RF-06 | Glosario de 15 conceptos o más | `GlosarioController`, `glosario.html`, 27 términos en `ContenidoService.GLOSARIO` | Hecho |
| RF-07 | Equipo, roles, contribuciones y créditos | `equipo.html` (integrantes, roles, responsabilidades, créditos); contribuciones en `docs/contribuciones.md` | Hecho |
| RF-08 | Validación | `PracticaService.procesar` (vacío, texto, decimales, rango) y `AccesoController` (correo y contraseña) | Hecho |
| RF-09 | Accesibilidad | Contraste alto sobre fondo negro, `lang="es"`, enlace «Saltar al contenido», etiquetas en formularios, `aria-live` en la retroalimentación, `:focus-visible`, `prefers-reduced-motion` y navegación completa por teclado | Hecho |
| RF-10 | Documentación | `README.md`, `docs/` (requisitos, arquitectura, bosquejos, evidencias manuales, pruebas, contribuciones, aceptación) | Hecho |

## Historias de usuario y criterios de aceptación

| ID | Historia | Criterios de aceptación |
|---|---|---|
| HU-01 | Como estudiante que inicia programación, quiero resolver un ejercicio de condicionales y recibir una explicación del resultado, para comprender por qué se ejecutó una rama del programa. | Dato vacío → pide completarlo. Dato fuera de rango → explica el error. Dato válido → Java calcula y la página explica la decisión. |
| HU-02 | Como estudiante, quiero leer una lección con un ejemplo resuelto, para ver cómo se aplica el concepto antes de practicar. | La lección muestra enunciado, pasos, código y salida; ofrece un botón a la práctica relacionada. |
| HU-03 | Como estudiante, quiero responder una evaluación y ver mi nota con la corrección de cada pregunta, para saber qué debo repasar. | Muestra nota sobre 10, marca aciertos y errores y explica la respuesta correcta. |
| HU-04 | Como estudiante, quiero consultar un glosario, para recordar el significado de los términos. | Lista al menos 15 conceptos con definición breve. |
| HU-05 | Como estudiante, quiero ver mi progreso, para saber cuánto he avanzado. | Muestra lecciones, niveles de mecanografía y evaluaciones completados. |
| HU-06 | Como docente, quiero ver quién hizo cada parte y qué recursos se usaron, para evaluar la colaboración. | La página Equipo lista roles y créditos; el historial de GitHub conserva los aportes. |

## Contenidos priorizados

1. Obligatorio (producto mínimo): módulos, lección con ejemplo, tres prácticas, evaluación, glosario, validación, equipo, documentación.
2. Adicional ya construido: mecanografía por niveles, progreso, perfil y configuración.
3. Fuera de alcance: base de datos, API externa, despliegue en la nube, pagos.

## Análisis EPS de las prácticas obligatorias

| Práctica | Entradas | Procesos | Salidas | Restricciones |
|---|---|---|---|---|
| Selección | `edad` (entero, 0 a 120) | Comparar `edad >= 18` | «Mayor de edad» o «Menor de edad» con la explicación | Vacío, texto o fuera de rango muestran un mensaje |
| Suma (ciclo for) | `n` (entero, 1 a 100) | Acumular `suma += i` de 1 hasta `n` | La suma y el número de vueltas | `n` fuera de 1–100 se rechaza |
| Validación | `nota` (0 a 10) | Comprobar rango y comparar `nota >= 7` | «Aprobado», «Reprobado» o «Fuera de rango» | Vacío o texto se rechaza |
