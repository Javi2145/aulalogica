# Arquitectura de AulaLógica Web

## Flujo de una práctica

El navegador envía el dato (`POST /practica/{n}`) → `PracticaController` lo recibe →
`PracticaService` lo valida y calcula → se crea un `Resultado` (modelo) →
la plantilla `practica.html` (Thymeleaf) muestra la retroalimentación.

## Capas y clases

| Capa | Clases | Qué hace |
|---|---|---|
| Vista | `templates/*.html`, `static/css/estilos.css` | Muestra y captura datos |
| Controlador | `AccesoController`, `ContenidoController`, `PracticaController`, `TecladoController`, `EvaluacionController`, `ReporteController`, `PerfilController`, `DatosGlobales`, `AccesoInterceptor` | Rutas, sesión, qué vista responder |
| Servicio | `ContenidoService`, `PracticaService`, `EvaluacionService`, `TecladoService`, `UsuarioService`, `ProgresoService`, `ReporteService`, `PdfBasico` | Reglas, validación, cálculos, puntajes |
| Modelo | `Tema`, `Ejercicio`, `Pregunta`, `Evaluacion`, `Resultado`, `Usuario`, `SesionEvaluacion`, `FilaReporte`, `Progreso`, `Revision`, `ResultadoTeclado` | Datos |

## Mapa de navegación

```
/ (portada 3D) → /acceso → /contenidos ─┬→ /leccion/{n}
                                         ├→ /practica/{n}
                                         ├→ /mecanografia → /mecanografia/{n}
                                         ├→ /evaluaciones → /evaluacion/{n}/pregunta/{q} → /evaluacion/{n}/resultado → /reporte/evaluaciones (PDF)
                                         ├→ /progreso
                                         └→ /perfil · /configuracion · /equipo
```

## JavaScript de interfaz (opcional según la guía)

`intro3d.js` (animación de portada), `ui.js` (menú lateral, filtro en vivo del buscador,
confirmación al finalizar) y `teclado.js` (captura de teclas y teclado virtual).
Ninguno valida datos ni decide resultados: puntaje y desbloqueo de Mecanografía se calculan
en Java (`POST /mecanografia/{n}/resultado`).
