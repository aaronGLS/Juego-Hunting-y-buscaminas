package practica2;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import practica2.model.buscaminas.ConfiguracionBuscaminas;
import practica2.model.buscaminas.JuegoBuscaminas;
import practica2.model.core.EstadoPartida;

public class JuegoBuscaminasPausaTest {

    @Test
    public void pausada_noSeConsideraFinalizada_ySePuedeReanudar() {
        ConfiguracionBuscaminas config = new ConfiguracionBuscaminas(5, 5, 3, "Jugador");
        JuegoBuscaminas juego = new JuegoBuscaminas(config);

        juego.iniciarPartida();
        assertEquals(EstadoPartida.EN_CURSO, juego.getEstadoPartida());
        assertFalse(juego.esJuegoFinalizado());

        juego.pausarPartida();
        assertEquals(EstadoPartida.PAUSADA, juego.getEstadoPartida());
        assertFalse(juego.esJuegoFinalizado());

        juego.reanudarPartida();
        assertEquals(EstadoPartida.EN_CURSO, juego.getEstadoPartida());
        assertFalse(juego.esJuegoFinalizado());

        juego.finalizarPartida();
        assertEquals(EstadoPartida.FINALIZADA, juego.getEstadoPartida());
        assertTrue(juego.esJuegoFinalizado());
    }
}
