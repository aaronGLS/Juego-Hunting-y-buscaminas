package practica2.model.buscaminas;

import java.util.ArrayDeque;
import java.util.Random;

/**
 * Representa la estructura completa del tablero en el juego Buscaminas.
 * Gestiona una matriz de objetos Casilla y la distribución aleatoria de minas.
 * Implementa el efecto dominó para descubrir automáticamente casillas seguras.
 */
public class Tablero {
    /**
     * Matriz bidimensional que contiene las casillas del tablero.
     */
    private Casilla[][] matrizCasillas;

    /**
     * Número de filas en el tablero.
     */
    private int filas;

    /**
     * Número de columnas en el tablero.
     */
    private int columnas;

    /**
     * Constructor que inicializa un tablero con las dimensiones especificadas.
     * 
     * @param filas    Número de filas del tablero
     * @param columnas Número de columnas del tablero
     */
    public Tablero(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        this.matrizCasillas = new Casilla[filas][columnas];

        // Inicializar todas las casillas
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                matrizCasillas[i][j] = new Casilla();
            }
        }
    }

    /**
     * Inicializa el tablero colocando minas aleatoriamente y
     * calculando el número de minas adyacentes para cada casilla.
     * 
     * @param cantidadMinas Número de minas a colocar en el tablero
     */
    public void inicializarTablero(int cantidadMinas) {
        // Validar que la cantidad de minas sea válida
        int totalCasillas = filas * columnas;
        if (cantidadMinas <= 0 || cantidadMinas >= totalCasillas) {
            cantidadMinas = Math.max(1, totalCasillas / 5); // 20% del tablero por defecto
        }

        // Colocar minas aleatoriamente
        Random random = new Random();
        int minasColocadas = 0;

        while (minasColocadas < cantidadMinas) {
            int fila = random.nextInt(filas);
            int columna = random.nextInt(columnas);

            // Si la casilla no tiene mina, colocar una
            if (!matrizCasillas[fila][columna].contieneMina()) {
                matrizCasillas[fila][columna].setMina(true);
                minasColocadas++;

                // Actualizar el contador de minas adyacentes en las casillas vecinas
                incrementarMinasAdyacentes(fila, columna);
            }
        }
    }

    /**
     * Incrementa el contador de minas adyacentes en las casillas vecinas a una mina.
     * 
     * @param fila    Fila de la casilla que contiene una mina
     * @param columna Columna de la casilla que contiene una mina
     */
    private void incrementarMinasAdyacentes(int fila, int columna) {
        // Recorrer todas las posiciones adyacentes teóricas
        for (int i = fila - 1; i <= fila + 1; i++) {
            for (int j = columna - 1; j <= columna + 1; j++) {
                // Verificar si la posición está dentro de los límites del tablero
                if (esCoordenadaValida(i, j)) {
                    // No actualizar la propia casilla con mina
                    if (i != fila || j != columna) {
                        matrizCasillas[i][j].incrementarMinasAdyacentes();
                    }
                }
            }
        }
    }

    /**
     * Decrementa el contador de minas adyacentes en las casillas vecinas.
     * 
     * @param fila    Fila de la casilla que ya no contiene una mina
     * @param columna Columna de la casilla que ya no contiene una mina
     */
    private void decrementarMinasAdyacentes(int fila, int columna) {
        for (int i = fila - 1; i <= fila + 1; i++) {
            for (int j = columna - 1; j <= columna + 1; j++) {
                if (esCoordenadaValida(i, j)) {
                    if (i != fila || j != columna) {
                        int numMinas = matrizCasillas[i][j].getNumeroMinasAdyacentes();
                        matrizCasillas[i][j].setNumeroMinasAdyacentes(numMinas - 1);
                    }
                }
            }
        }
    }

    /**
     * Recoloca una mina de una casilla a otra.
     * Se usa para garantizar que el primer clic del jugador no sea una mina.
     * 
     * @param filaMina Fila de la mina a mover
     * @param columnaMina Columna de la mina a mover
     */
    public void recolocarMina(int filaMina, int columnaMina) {
        // Buscar una nueva ubicación vacía
        int nuevaFila = -1;
        int nuevaColumna = -1;

        // Encontrar la primera casilla que no sea una mina y no sea la casilla original
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (!matrizCasillas[i][j].contieneMina() && (i != filaMina || j != columnaMina)) {
                    nuevaFila = i;
                    nuevaColumna = j;
                    break;
                }
            }
            if (nuevaFila != -1) {
                break;
            }
        }

        if (nuevaFila != -1) {
            // 1. Quitar la mina de la ubicación original y actualizar vecinos
            matrizCasillas[filaMina][columnaMina].setMina(false);
            decrementarMinasAdyacentes(filaMina, columnaMina);

            // 2. Poner la mina en la nueva ubicación y actualizar vecinos
            matrizCasillas[nuevaFila][nuevaColumna].setMina(true);
            incrementarMinasAdyacentes(nuevaFila, nuevaColumna);
        }
    }

    /**
     * Aplica el efecto dominó para descubrir automáticamente casillas vecinas
     * seguras.
     * Se ejecuta cuando se descubre una casilla sin minas adyacentes (0).
     * 
     * @param fila    Fila de la casilla descubierta
     * @param columna Columna de la casilla descubierta
     * @return Número de casillas descubiertas durante el efecto dominó
     */
    public int aplicarEfectoDomino(int fila, int columna) {
        // Implementación iterativa (evita StackOverflowError en tableros grandes como 100x100)
        boolean[][] visitadas = new boolean[filas][columnas];
        ArrayDeque<Integer> pendientes = new ArrayDeque<>();

        // Empaquetar coordenadas en un int para evitar crear muchos objetos
        pendientes.push(fila * columnas + columna);

        int casillasDescubiertas = 0;

        while (!pendientes.isEmpty()) {
            int key = pendientes.pop();
            int f = key / columnas;
            int c = key % columnas;

            if (!esCoordenadaValida(f, c)) {
                continue;
            }
            if (visitadas[f][c]) {
                continue;
            }
            visitadas[f][c] = true;

            Casilla casilla = matrizCasillas[f][c];

            // Si la casilla está marcada o contiene una mina, no hacemos nada
            if (casilla.estaMarcada() || casilla.contieneMina()) {
                continue;
            }

            // Si la casilla ya está descubierta, no la contamos, pero continuamos con las
            // vecinas únicamente si no tiene minas adyacentes.
            if (!casilla.estaCubierta()) {
                if (casilla.getNumeroMinasAdyacentes() == 0) {
                    encolarVecinas(pendientes, f, c);
                }
                continue;
            }

            // Descubrir la casilla y contarla
            casilla.descubrir();
            casillasDescubiertas++;

            // Si no tiene minas adyacentes, continuar con las casillas vecinas
            if (casilla.getNumeroMinasAdyacentes() == 0) {
                encolarVecinas(pendientes, f, c);
            }
        }

        return casillasDescubiertas;
    }

    private void encolarVecinas(ArrayDeque<Integer> pendientes, int fila, int columna) {
        for (int i = fila - 1; i <= fila + 1; i++) {
            for (int j = columna - 1; j <= columna + 1; j++) {
                // No procesar la misma casilla
                if (i != fila || j != columna) {
                    pendientes.push(i * columnas + j);
                }
            }
        }
    }

    /**
     * Verifica si una coordenada está dentro de los límites del tablero.
     * 
     * @param fila    Fila a verificar
     * @param columna Columna a verificar
     * @return true si la coordenada es válida, false en caso contrario
     */
    public boolean esCoordenadaValida(int fila, int columna) {
        return fila >= 0 && fila < filas && columna >= 0 && columna < columnas;
    }

    /**
     * Obtiene la casilla en la posición especificada.
     * 
     * @param fila    Fila de la casilla
     * @param columna Columna de la casilla
     * @return La casilla en la posición especificada
     */
    public Casilla getCasilla(int fila, int columna) {
        return matrizCasillas[fila][columna];
    }

    /**
     * Obtiene la matriz completa de casillas.
     * 
     * @return La matriz de casillas
     */
    public Casilla[][] getMatrizCasillas() {
        return matrizCasillas;
    }

    /**
     * Obtiene el número de filas del tablero.
     * 
     * @return Número de filas
     */
    public int getFilas() {
        return filas;
    }

    /**
     * Obtiene el número de columnas del tablero.
     * 
     * @return Número de columnas
     */
    public int getColumnas() {
        return columnas;
    }

    /**
     * Cuenta el número total de casillas que contienen minas.
     * 
     * @return Cantidad de minas en el tablero
     */
    public int contarMinas() {
        int contador = 0;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (matrizCasillas[i][j].contieneMina()) {
                    contador++;
                }
            }
        }
        return contador;
    }

    /**
     * Cuenta el número de casillas que han sido marcadas correctamente por el jugador.
     * Una casilla está marcada correctamente si ha sido marcada y contiene una mina.
     * 
     * @return Cantidad de casillas marcadas correctamente (que contienen minas)
     */
    public int contarCasillasMarcadasCorrectas() {
        int contador = 0;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (matrizCasillas[i][j].estaMarcada() && matrizCasillas[i][j].contieneMina()) {
                    contador++;
                }
            }
        }
        return contador;
    }

    /**
     * Cuenta el número de casillas que han sido marcadas por el jugador.
     * 
     * @return Cantidad de casillas marcadas
     */
    public int contarCasillasMarcadas() {
        int contador = 0;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (matrizCasillas[i][j].estaMarcada()) {
                    contador++;
                }
            }
        }
        return contador;
    }

    /**
     * Cuenta el número de casillas que han sido descubiertas por el jugador.
     * 
     * @return Cantidad de casillas descubiertas
     */
    public int contarCasillasDescubiertas() {
        int contador = 0;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (!matrizCasillas[i][j].estaCubierta()) {
                    contador++;
                }
            }
        }
        return contador;
    }
}