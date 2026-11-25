<p align="center">
  <img src="banner.svg" alt="banner" width="100%">
</p>

# AulaLógica Web

Aplicativo didáctico de **Fundamentos de Programación en Java**.

🌐 **Sitio en línea:** https://aulalogica.onrender.com

> ⚠️ **Importante**
>
> - El plan gratuito de Render tarda ~1 min en despertar.
> - El progreso se pierde si el servidor está inactivo.
> - Se debe ingresar desde un computador o laptop.

## 🚧 Limitaciones y mejoras futuras

- Usuarios y progreso viven en memoria: se pierden al detener el servidor.
- Las contraseñas no se cifran (el inicio de sesión es opcional según la guía).
- Mejora futura: persistencia con H2 y estadísticas por usuario.
  
## 🎯 Reto

*¿Cómo enseñar a otro estudiante los fundamentos de programación mediante una aplicación web sencilla, clara e interactiva desarrollada en Java?*

## 🛠️ Tecnologías

- Java 21 (OpenJDK LTS)
- Spring Boot 3.3 · Spring Web · Thymeleaf · Validation
- Maven
- HTML5 / CSS3
- Three.js (CDN), solo para la animación 3D de la portada
- Visual Studio Code · GitHub

No usa base de datos: los datos son clases y listas en memoria.

## 🧭 Recorrido de demostración

1. **Portada 3D:** usar la rueda del mouse para entrar y pulsar **Inicio**.
2. **Registrarme** (o *Entrar como invitado*) → menú **Contenidos** (14 lecciones con ejemplo resuelto y pregunta).
3. **Práctica en Java:** 7 formularios (selección, ciclos, validación…) procesados por Java.
4. **Mecanografía:** 14 niveles con teclado virtual; se desbloquean con 90 % de precisión.
5. **Evaluación:** 5 evaluaciones con nota y retroalimentación por pregunta.
6. **Glosario** (22 conceptos), **Progreso**, **Perfil**, **Configuración** y **Equipo** (roles y créditos).

## 🏗️ Arquitectura (MVC por capas)

```
Navegador → Controlador → Servicio → Modelo/datos → Thymeleaf
```

| Capa | Responsabilidad |
|---|---|
| **Controlador** | Rutas web y recepción de formularios |
| **Servicio** | Validación, cálculos, puntajes y reglas didácticas |
| **Modelo** | Tema, Ejercicio, Pregunta, Evaluacion, Resultado, Usuario… |
| **Vista (Thymeleaf)** | Páginas HTML con CSS y JavaScript de interfaz |

Detalle en [`docs/arquitectura.md`](docs/arquitectura.md).

## 📁 Estructura del repositorio

```
aulalogica/
│ ├── 🖼️ banner.svg
│ ├── 📄 README.md
│ ├── 📄 pom.xml
│ ├── 🐳 Dockerfile
│ ├── 📄 .dockerignore
│ ├── 📄 .gitignore
│ │
│ ├── 📁 docs/
│ │ ├── 📄 requisitos.md
│ │ ├── 📄 arquitectura.md
│ │ ├── 📄 bosquejos.md
│ │ ├── 📄 evidencias-manuales.md
│ │ ├── 📄 pruebas.md
│ │ ├── 📄 contribuciones.md
│ │ ├── 📄 aceptacion.md
│ │ ├── 📁 bosquejos/
│ │ │ ├── 🖼️ bosquejo1_inicio.svg
│ │ │ ├── 🖼️ bosquejo2_temas.svg
│ │ │ ├── 🖼️ bosquejo3_leccion.svg
│ │ │ ├── 🖼️ bosquejo4_practica.svg
│ │ │ └── 🖼️ bosquejo5_evaluacion.svg
│ │ └── 📁 diagramas/
│ │   ├── 🖼️ arquitectura_mvc.svg
│ │   ├── 🖼️ flujo_seleccion.svg
│ │   ├── 🖼️ flujo_suma.svg
│ │   └── 🖼️ flujo_validacion.svg
│ │
│ └── 📁 src/
│   ├── 📁 main/
│   │ ├── 📁 java/ec/edu/uta/aulalogica/
│   │ │ ├── 📄 AulaLogicaApplication.java
│   │ │ ├── 📁 controller/   → rutas web y formularios
│   │ │ ├── 📁 service/      → reglas, validación y puntajes
│   │ │ └── 📁 model/        → clases de datos
│   │ └── 📁 resources/
│   │   ├── 📄 application.properties
│   │   ├── 📁 templates/    → páginas Thymeleaf
│   │   └── 📁 static/
│   │     ├── 📁 css/
│   │     ├── 📁 js/
│   │     └── 📁 img/
│   └── 📁 test/
│     └── 📁 java/ec/edu/uta/aulalogica/service/
│       ├── 📄 ContenidoServiceTest.java
│       ├── 📄 PracticaServiceTest.java
│       └── 📄 ReporteServiceTest.java
```

## 📚 Documentación

| Documento | Contenido |
|---|---|
| [`docs/requisitos.md`](docs/requisitos.md) | Análisis y requisitos |
| [`docs/bosquejos.md`](docs/bosquejos.md) | Bosquejos de las pantallas |
| [`docs/evidencias-manuales.md`](docs/evidencias-manuales.md) | Evidencias manuales |
| [`docs/pruebas.md`](docs/pruebas.md) | Casos de prueba |
| [`docs/contribuciones.md`](docs/contribuciones.md) | Contribuciones |
| [`docs/aceptacion.md`](docs/aceptacion.md) | Lista de aceptación |

## 🧪 Pruebas

Casos normales, límites e inválidos documentados en [`docs/pruebas.md`](docs/pruebas.md).
Pruebas automatizadas en `PracticaServiceTest.java`, `ContenidoServiceTest.java` y `ReporteServiceTest.java`.

## Autores
>
> - Kevin Garcés
> - Cristian Gómez
> - Esteban Zambrano
> - Erick Cordonez
> 
