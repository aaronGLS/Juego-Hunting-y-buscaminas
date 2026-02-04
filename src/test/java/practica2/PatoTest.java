package practica2;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import practica2.model.hunting.Pato;

public class PatoTest {

    @Test
    public void moverAleatorio_noDebeFallar_conAreaMasPequenaQueElPato() {
        Pato pato = new Pato(120, 120, 50, 50);

        for (int i = 0; i < 100; i++) {
            pato.moverAleatorio();
            assertEquals(0, pato.getPosicionX());
            assertEquals(0, pato.getPosicionY());
        }
    }
}

