package practica2;

import org.junit.Test;

import practica2.controller.ControladorBuscaminas;
import practica2.controller.BuscaminasObservador;
import practica2.model.core.TemporizadorObservador;
import practica2.model.core.TemporizadorPartida;
import practica2.model.persistencia.PersistenciaReportes;

public class ObservadoresCapacidadTest {

    @Test
    public void controladorBuscaminas_noDebeCrashear_alAgregarMuchosObservadores() {
        ControladorBuscaminas controlador = new ControladorBuscaminas(new PersistenciaReportes());

        for (int i = 0; i < 50; i++) {
            final int idx = i;
            controlador.addObservador(new BuscaminasObservador() {
                @Override
                public void actualizar(ControladorBuscaminas c) {
                    // No-op
                    if (idx < 0) {
                        throw new AssertionError("Inalcanzable");
                    }
                }
            });
        }
    }

    @Test
    public void temporizadorPartida_noDebeCrashear_alRegistrarMuchosObservadores() {
        TemporizadorPartida temporizador = new TemporizadorPartida();

        for (int i = 0; i < 50; i++) {
            temporizador.registrarObservador(new TemporizadorObservador() {
                @Override
                public void tiempoActualizado(int segundos) {
                    // No-op
                }
            });
        }
    }
}
