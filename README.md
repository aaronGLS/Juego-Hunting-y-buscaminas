# Juegos Code n’Bugs (Buscaminas + Hunting) — Java (Swing)

Este proyecto lo desarrollé como una aplicación de escritorio en **Java (Swing)** que incluye **dos minijuegos**:

- **Buscaminas**: el clásico de descubrir casillas y marcar minas.
- **Hunting**: un minijuego de reflejos donde haces clic para “disparar” al pato antes de que desaparezca.

Además, el sistema guarda **reportes** (resultados) para que puedas ver tu historial de partidas.

---

## ¿Qué necesitas para correrlo?

- **JDK 21** (es importante: el proyecto está configurado para Java 21).
- **Maven** (para compilar y ejecutar desde consola).
- Opcional: un IDE como **NetBeans**, IntelliJ o VS Code.

---

## Cómo ejecutarlo (rápido)

### Opción A: desde consola con Maven

1) En la carpeta del proyecto, compila:

```bash
mvn clean package
```

2) Ejecuta el `.jar`:

```bash
java -jar target/Emily.jar
```

### Opción B: desde el IDE

- El punto de entrada principal es: `src/main/java/practica2/App.java`
- Ejecuta la clase `practica2.App`.

> Nota: la app se abre en una ventana principal llamada **MarcoPrincipal** (menú + navegación).

---

## Cómo usar el sistema (explicación sencilla)

Cuando abres la aplicación vas a ver un **menú principal** con botones:

- **Iniciar Buscaminas**
- **Iniciar Hunting**
- **Ver Reportes**

Si en algún momento quieres volver, en casi todas las pantallas hay un botón para **regresar al menú**.

### 1) Buscaminas (cómo jugar)

1) Entra a **Iniciar Buscaminas**.
2) Configura:
   - **Nombre del jugador**
   - **Filas**
   - **Columnas**
   - **Cantidad de minas**
3) Empieza la partida.

Controles dentro del tablero:

- **Clic izquierdo**: descubre una casilla.
- **Clic derecho**: marca/desmarca una casilla (bandera).

Qué ves en pantalla (en el panel superior):

- **Nombre del jugador**
- **Tiempo** (segundos)
- **Contador de minas** (una guía visual para saber cuántas minas “te faltan” según lo marcado)

Comportamiento importante:

- El **primer clic** está protegido para que **no caigas en mina** en el primer movimiento (si cae en mina, se recoloca).
- Si descubres una casilla sin mina y **con 0 minas adyacentes**, se activa un **efecto dominó** que descubre casillas seguras conectadas.

Cómo se gana / cómo se pierde:

- **Ganas** cuando se descubren **todas las casillas que NO tienen mina**.
- **Pierdes** al descubrir una casilla con mina.

Botones/acciones típicas del panel:

- **Pausar**: pausa/reanuda la partida (y el temporizador).
- **Salir al menú**: vuelve al menú principal.
- **Salir del juego**: termina la partida (según el flujo del panel).

Al terminar la partida:

- Si pierdes, se revelan las minas.
- Se genera un **reporte** y se guarda automáticamente.

### 2) Hunting (cómo jugar)

1) Entra a **Iniciar Hunting**.
2) Configura:
   - **Nombre del jugador**
   - **Velocidad inicial (ms)**: cuánto tiempo aparece el pato.
   - **Aciertos para reducir**: cada cuántos aciertos el pato se vuelve más rápido.
   - **Reducción por acierto (ms)**: cuánto se reduce la velocidad al cumplir el umbral.
3) Inicia.

Cómo se juega:

- Haces clic en el área del juego para **disparar**.
- Si el clic cae dentro del pato, cuenta como **acierto** (si el pato sigue visible).
- Si fallas varias veces seguidas, el juego termina.

Regla clave:

- La partida termina al llegar a **5 fallos consecutivos** (por requisito).

Qué ves en pantalla:

- Nombre del jugador
- Aciertos (hits)
- Fallos (misses)
- Tiempo transcurrido

Tips rápidos:

- Si notas que “cada vez está más difícil”, es porque con la configuración se puede hacer que el pato se muestre menos tiempo conforme avanzas.

Cuando termina:

- Se muestra un resumen con aciertos y tiempo.
- Se guarda el **reporte** automáticamente.
- Puedes volver al menú o jugar de nuevo.

### 3) Reportes (historial)

En **Ver Reportes** puedes ver el historial por pestañas:

- Buscaminas **ganadas**
- Buscaminas **perdidas**
- Hunting

Cada vez que entras a este panel, el sistema vuelve a cargar la información desde los archivos.

---

## ¿Dónde se guardan los reportes?

Los reportes se guardan en la carpeta:

- `reportes/`

Ahí vas a encontrar archivos de texto (sin extensión) como:

- `reportes/reportes_buscaminas_ganadas`
- `reportes/reportes_buscaminas_perdidas`
- `reportes/reportes_hunting`

Cada línea representa un registro con datos separados por `|`.

Detalles importantes:

- Si un jugador vuelve a jugar con el mismo nombre, el sistema **reemplaza** la línea anterior de ese jugador (lo trato como “último resultado” por nombre).
- Si una línea está corrupta (por ejemplo, un número inválido), se omite para evitar crasheos al cargar reportes.

Si quieres “resetear” el historial, puedes borrar los archivos dentro de `reportes/` (con la app cerrada).

---

## Sección técnica (para quien quiera ver “cómo funciona por dentro”)

### Arquitectura general

Organizo el proyecto por capas, manteniendo una separación clara de responsabilidades:

- `practica2/view/**`: interfaz Swing (paneles, ventanas y estilos).
- `practica2/controller/**`: controladores + comandos (intermediarios entre UI y modelo).
- `practica2/model/**`: reglas del negocio (juegos, tablero, reportes, validaciones).
- `practica2/model/persistencia/**`: lectura/escritura de reportes en archivos.

### Navegación (UI)

- La ventana principal usa un `CardLayout` para “cambiar de pantalla” sin abrir mil ventanas.
- Cada pantalla es un `JPanel` (menú, configuración, juego, reportes).
- La UI tiene partes generadas por NetBeans (ver nota al final).

### Patrones usados (sin sobre-ingeniería)

- **MVC (en la práctica)**:
  - La **View** (Swing) no debería tomar decisiones de negocio.
  - El **Controller** traduce eventos de UI a acciones del modelo.
  - El **Model** contiene reglas (ganar/perder, dominó, fallos máximos, etc.).

- **Observer (tipado)**:
  - Las vistas se registran como observadores para refrescarse cuando cambia el modelo.
  - También hay observadores para el temporizador.

- **Command**:
  - Acciones como iniciar juego, descubrir casilla, marcar casilla, disparar y guardar reporte están encapsuladas en comandos (`practica2/controller/comando/**`).

- **Composition Root (inyección simple de dependencias)**:
  - `practica2/AppCompositionRoot.java` crea implementaciones concretas (por ejemplo `PersistenciaReportes`) y las inyecta en controladores.
  - Esto ayuda a desacoplar y facilita pruebas/cambios.

### Flujo real de una acción (ejemplos)

**Buscaminas (clic izquierdo):**

1) La vista detecta el clic (mouse listener del botón/casilla).
2) La vista llama al controlador: `ControladorBuscaminas.descubrirCasilla(fila, columna)`.
3) El controlador ejecuta un comando (Command) que delega al modelo.
4) El modelo actualiza el tablero/casillas y, si aplica, el dominó.
5) El controlador notifica a sus observadores.
6) La vista refresca el tablero (iconos, números, minas, etc.).

**Hunting (clic para disparar):**

1) La vista toma la coordenada del clic dentro del área del juego.
2) Llama al controlador: `ControladorHunting.procesarDisparo(x, y)`.
3) El controlador ejecuta un comando y el modelo determina si fue acierto/fallo.
4) El modelo notifica cambios (posición/visibilidad del pato, contadores, etc.).
5) La vista actualiza labels y reubica el pato.

### Concurrencia: EDT + hilos de juego

- La UI Swing corre en el **EDT** (Event Dispatch Thread). El arranque de la app se hace con `SwingUtilities.invokeLater(...)` en `practica2/App.java`.
- El Hunting usa un hilo (vía `ExecutorService`) para el ciclo del pato, y las partidas tienen un temporizador con hilo daemon.
- Para evitar problemas, las actualizaciones de UI se encaminan al EDT (helper `practica2/controller/UiNotifier.java`).

### Persistencia de reportes (archivos)

- Existe una interfaz: `RepositorioReportes` (contrato).
- La implementación actual es `PersistenciaReportes`, que guarda/lee en `reportes/`.
- Si ocurre un error de lectura/escritura, se lanza `ExcepcionPersistencia` y el controlador guarda el mensaje para que la UI lo muestre al usuario.

### Buscaminas: efecto dominó sin recursión

Para tableros grandes (por ejemplo **100x100**), un dominó recursivo puede lanzar `StackOverflowError`.
Por eso el dominó está implementado de forma **iterativa** usando una estructura tipo pila/cola (`ArrayDeque`), evitando desbordar la pila del sistema.

---

## Pruebas

Hay pruebas con **JUnit** (configurado en Maven).

Para ejecutarlas:

```bash
mvn test
```

---

## Nota sobre NetBeans (UI)

En las clases de UI generadas por NetBeans hay secciones marcadas como código generado (por ejemplo `//GEN-BEGIN` / `//GEN-END`).
Esas partes se regeneran automáticamente con el diseñador visual, así que conviene no editarlas manualmente.
