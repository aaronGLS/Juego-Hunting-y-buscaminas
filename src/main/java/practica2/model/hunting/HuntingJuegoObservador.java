package practica2.model.hunting;

/**
 * Observador tipado para cambios del modelo en el juego Hunting.
 * Permite desacoplar UI/controladores del modelo sin usar Object[]/casts.
 */
public interface HuntingJuegoObservador {
    void actualizar(JuegoHunting juego);
}

