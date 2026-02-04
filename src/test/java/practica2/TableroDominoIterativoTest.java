package practica2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import practica2.model.buscaminas.Tablero;

public class TableroDominoIterativoTest {

    @Test
    public void aplicarEfectoDomino_100x100_noDebeCausarStackOverflow_yDebeDescubrirTodo() {
        Tablero tablero = new Tablero(100, 100);

        // Sin minas (tablero no inicializado con minas), todas las casillas tienen 0 adyacentes,
        // así que el dominó debe descubrir todo el tablero.
        int descubiertas = tablero.aplicarEfectoDomino(0, 0);
        assertEquals(100 * 100, descubiertas);

        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                assertFalse(tablero.getCasilla(i, j).estaCubierta());
            }
        }
    }
}

