package ec.edu.uta.aulalogica.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** Comprueba que el contenido cumple los mínimos de la guía del proyecto (RF-02, RF-03, RF-05, RF-06, RF-07). */
class ContenidoServiceTest {
    private final ContenidoService contenido = new ContenidoService();

    @Test void hayAlMenosSeisModulos() {
        assertTrue(contenido.temas().size() >= 6);
    }
    @Test void cadaLeccionTieneEjemploResuelto() {
        for (int i = 0; i < contenido.temas().size(); i++) {
            assertFalse(contenido.ejemplo(i).enunciado().isBlank());
            assertFalse(contenido.ejemplo(i).pasos().isEmpty());
            assertFalse(contenido.ejemplo(i).salida().isBlank());
        }
    }
    @Test void cadaLeccionTienePracticaRelacionadaValida() {
        for (int i = 0; i < contenido.temas().size(); i++) {
            int p = contenido.practicaRelacionada(i);
            assertTrue(p >= 0 && p < contenido.ejercicios().size());
        }
    }
    @Test void hayAlMenosTresPracticas() {
        assertTrue(contenido.ejercicios().size() >= 3);
    }
    @Test void laEvaluacionGeneralTieneDiezPreguntasOMas() {
        assertTrue(contenido.evaluacion(0).preguntas().size() >= 10);
    }
    @Test void elGlosarioTieneQuinceConceptosOMas() {
        assertTrue(contenido.glosario().size() >= 15);
    }
    @Test void losTerminosDelGlosarioNoSeRepitenNiEstanVacios() {
        long distintos = contenido.glosario().stream().map(t -> t.termino().toLowerCase()).distinct().count();
        assertEquals(contenido.glosario().size(), (int) distintos);
        contenido.glosario().forEach(t -> assertFalse(t.definicion().isBlank()));
    }
    @Test void cadaIntegranteTieneRolYResponsabilidad() {
        assertEquals(ContenidoService.INTEGRANTES.size(), contenido.miembros().size());
        contenido.miembros().forEach(m -> {
            assertFalse(m.rol().isBlank());
            assertFalse(m.responsabilidad().isBlank());
        });
    }
    @Test void hayCreditosDeRecursos() {
        assertFalse(contenido.creditos().isEmpty());
    }
    @Test void existeElTemaDeProgramacionOrientadaAObjetosConSuEvaluacionYPractica() {
        assertTrue(contenido.buscarTemas("programacion orientada").contains(14));
        assertEquals("15. Programación orientada a objetos", contenido.tema(14).titulo());
        assertEquals("poo", contenido.ejercicio(contenido.practicaRelacionada(14)).id());
        assertEquals("Programación orientada a objetos", contenido.evaluacion(5).titulo());
        assertEquals(8, contenido.evaluacion(5).preguntas().size());
    }
    @Test void laRespuestaCorrectaDeCadaPreguntaExiste() {
        contenido.evaluaciones().forEach(ev -> ev.preguntas().forEach(p ->
            assertTrue(p.correcta() >= 0 && p.correcta() < p.opciones().size())));
        contenido.temas().forEach(t -> assertTrue(t.correcta() >= 0 && t.correcta() < t.opciones().size()));
    }
    @Test void elBuscadorEncuentraLeccionesNuevasSinTildes() {
        assertTrue(contenido.buscarTemas("pseudocodigo").contains(11));
        assertTrue(contenido.buscarTemas("validacion").contains(13));
    }
}
