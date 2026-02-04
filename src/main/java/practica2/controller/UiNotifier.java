package practica2.controller;

import javax.swing.SwingUtilities;

/**
 * Helper mínimo para ejecutar acciones en el Event Dispatch Thread (EDT).
 */
final class UiNotifier {

    private UiNotifier() {
    }

    static void runOnEdt(Runnable action) {
        if (action == null) {
            return;
        }
        if (SwingUtilities.isEventDispatchThread()) {
            action.run();
        } else {
            SwingUtilities.invokeLater(action);
        }
    }
}