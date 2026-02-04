package practica2.model.validacion;

import practica2.model.buscaminas.ConfiguracionBuscaminas;

public class ValidadorConfiguracionBuscaminas implements Validador<ConfiguracionBuscaminas> {
    @Override
    public String validar(ConfiguracionBuscaminas configuracion) {
        if (configuracion == null) {
            return "La configuración no puede ser nula";
        }

        if (configuracion.getNombreJugador() == null || configuracion.getNombreJugador().trim().isEmpty()) {
            return "El nombre del jugador es obligatorio";
        }

        if (configuracion.getFilas() < 5 || configuracion.getColumnas() < 5) {
            return "El tablero debe tener al menos 5 filas y 5 columnas";
        }

        int casillas = configuracion.getFilas() * configuracion.getColumnas();
        int maxMinas = casillas - 1;

        if (configuracion.getCantidadMinas() <= 0) {
            return "La cantidad de minas debe ser mayor que cero";
        }

        if (configuracion.getCantidadMinas() >= casillas) {
            return "La cantidad de minas debe ser menor que el número de casillas";
        }

        if (configuracion.getCantidadMinas() > maxMinas) {
            return "La cantidad de minas no puede ser mayor a " + maxMinas;
        }

        return null;
    }
}