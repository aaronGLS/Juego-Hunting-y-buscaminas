package practica2;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import practica2.model.core.ResultadoPartida;

public class ResultadoPartidaTest {

    @Test
    public void desdeTexto_y_aTexto_sonConsistentes() {
        assertEquals(ResultadoPartida.GANADA, ResultadoPartida.desdeTexto("Ganada"));
        assertEquals(ResultadoPartida.PERDIDA, ResultadoPartida.desdeTexto("Perdida"));
        assertEquals("Ganada", ResultadoPartida.GANADA.aTexto());
        assertEquals("Perdida", ResultadoPartida.PERDIDA.aTexto());
    }
}

