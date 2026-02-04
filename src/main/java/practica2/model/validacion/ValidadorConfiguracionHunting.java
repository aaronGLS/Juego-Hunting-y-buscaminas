package practica2.model.validacion;

import practica2.model.hunting.ConfiguracionHunting;

public class ValidadorConfiguracionHunting implements Validador<ConfiguracionHunting> {
    @Override
    public String validar(ConfiguracionHunting configuracion) {
        if (configuracion == null) {
            return "La configuración no puede ser nula";
        }

        if (configuracion.getNombreJugador() == null || configuracion.getNombreJugador().trim().isEmpty()) {
            return "El nombre del jugador es obligatorio";
        }

        if (configuracion.getTiempoVisibilidadInicial() <= 0) {
            return "El tiempo de visibilidad debe ser mayor que cero";
        }

        if (configuracion.getUmbralAciertos() <= 0) {
            return "El umbral de aciertos debe ser mayor que cero";
        }

        if (configuracion.getReduccionTiempo() <= 0) {
            return "La reducción de tiempo debe ser mayor que cero";
        }

        return null;
    }
}

