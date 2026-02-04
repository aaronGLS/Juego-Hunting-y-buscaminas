package practica2.controller.comando;

import practica2.model.excepciones.ExcepcionJuego;

public interface Comando {
    void ejecutar() throws ExcepcionJuego;
}
