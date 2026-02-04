package practica2;

import practica2.controller.ControladorBuscaminas;
import practica2.controller.ControladorHunting;
import practica2.model.persistencia.PersistenciaReportes;
import practica2.model.persistencia.RepositorioReportes;

/**
 * Composition root: construye dependencias concretas y las inyecta.
 * Mantiene la lógica de wiring fuera de la capa view.
 */
public final class AppCompositionRoot {

    public record Dependencias(
            RepositorioReportes repositorioReportes,
            ControladorBuscaminas controladorBuscaminas,
            ControladorHunting controladorHunting) {
    }

    private AppCompositionRoot() {
    }

    public static Dependencias crearDependencias() {
        RepositorioReportes repo = new PersistenciaReportes();

        // Tamaño del área de juego Hunting (mantener coherencia con el UI actual)
        int anchoJuego = AppConfig.HUNTING_AREA_ANCHO;
        int altoJuego = AppConfig.HUNTING_AREA_ALTO;

        ControladorBuscaminas buscaminas = new ControladorBuscaminas(repo);
        ControladorHunting hunting = new ControladorHunting(repo, anchoJuego, altoJuego);

        return new Dependencias(repo, buscaminas, hunting);
    }
}
