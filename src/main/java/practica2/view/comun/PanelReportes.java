/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package practica2.view.comun;

import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import practica2.controller.ControladorBuscaminas;
import practica2.controller.ControladorHunting;
import practica2.model.persistencia.ReporteBuscaminasRegistro;
import practica2.model.persistencia.ReporteHuntingRegistro;

/**
 *
 * @author aaron
 */
public class PanelReportes extends javax.swing.JPanel {
    private MarcoPrincipal marco;
    private ControladorBuscaminas controladorBuscaminas;
    private ControladorHunting hunting;

    // Constantes de colores - Estilo Gamer
    private static final Color COLOR_NEGRO = UiTheme.NEGRO;
    private static final Color COLOR_MORADO_OSCURO = UiTheme.MORADO_OSCURO;
    private static final Color COLOR_BLANCO = UiTheme.BLANCO;

    // Colores neón
    private static final Color COLOR_AZUL_NEON = UiTheme.AZUL_NEON;
    private static final Color COLOR_VERDE_NEON = UiTheme.VERDE_NEON;
    private static final Color COLOR_ROSA_NEON = UiTheme.ROSA_NEON;
    private static final Color COLOR_ROJO_NEON = UiTheme.ROJO_NEON;

    /**
     * Creates new form PanelReportes
     */
    public PanelReportes() {
        initComponents();
        aplicarEstiloGamer();
    }

    public PanelReportes(MarcoPrincipal marco, ControladorBuscaminas controladorBuscaminas,
            ControladorHunting hunting) {
        initComponents();
        this.marco = marco;
        this.controladorBuscaminas = controladorBuscaminas;
        this.hunting = hunting;
        cargarReportes();
        aplicarEstiloGamer();
    }

    /**
     * Aplica el estilo visual gamer al panel y sus componentes
     */
    private void aplicarEstiloGamer() {
        // Configurar el panel principal
        setBorder(BorderFactory.createLineBorder(COLOR_BLANCO, 2, true));

        // Mejorar el título
        mejorarTitulo();

        // Configurar el estilo del TabbedPane
        configurarTabbedPane();

        // Configurar el estilo de las tablas
        configurarTablas();

        // Configurar el botón de regresar al menú
        configurarBotonRegresarMenu();

        // Centrar componentes
        centrarComponentes();
    }

    /**
     * Carga los reportes de todos los juegos y actualiza las tablas.
     */
    public void cargarReportes() {
        if (controladorBuscaminas != null) {
            controladorBuscaminas.limpiarUltimoErrorReportes();
        }
        if (hunting != null) {
            hunting.limpiarUltimoErrorReportes();
        }

        if (controladorBuscaminas != null) {
            actualizarTablaGanadores(controladorBuscaminas.obtenerReportesGanados());
            actualizarTablaPerdedores(controladorBuscaminas.obtenerReportesPerdidos());
        }

        if (hunting != null) {
            actualizarTablaHunting(hunting.obtenerReportesHunting());
        }

        StringBuilder errores = new StringBuilder();
        if (controladorBuscaminas != null && controladorBuscaminas.getUltimoErrorReportes() != null) {
            errores.append("Buscaminas: ").append(controladorBuscaminas.getUltimoErrorReportes()).append("\n");
        }
        if (hunting != null && hunting.getUltimoErrorReportes() != null) {
            errores.append("Hunting: ").append(hunting.getUltimoErrorReportes()).append("\n");
        }

        if (errores.length() > 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Ocurrió un problema al cargar/actualizar reportes:\n\n" + errores.toString(),
                    "Error de reportes",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Método público para actualizar los reportes al mostrar el panel
     */
    public void actualizarReportes() {
        // Limpiar las tablas antes de cargar nuevos datos
        limpiarTablas();
        // Cargar los reportes actualizados
        cargarReportes();
    }

    /**
     * Configura el botón de regresar al menú con el estilo gamer
     */
    private void configurarBotonRegresarMenu() {
        UiStyles.configurarBotonNeon(regresarMenu, COLOR_NEGRO, COLOR_ROJO_NEON, COLOR_BLANCO);

        javax.swing.ImageIcon icono = UiStyles.cargarIconoEscalado(
                getClass(),
                "/practica2/resources/gamer.png",
                UiTheme.ICONO_BOTON_SMALL_W,
                UiTheme.ICONO_BOTON_SMALL_H);
        if (icono != null) {
            regresarMenu.setIcon(icono);
            regresarMenu.setIconTextGap(10);
            regresarMenu.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
            regresarMenu.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
        }
    }

    /**
     * Mejora la apariencia del título
     */
    private void mejorarTitulo() {
        // Configurar fuente y color del texto
        titulo.setForeground(COLOR_AZUL_NEON);

        // Configurar fondo del título
        titulo.setBackground(COLOR_NEGRO);
        titulo.setOpaque(true);

        // Añadir borde al título con efecto de neón
        Border bordeExterior = BorderFactory.createLineBorder(COLOR_ROSA_NEON, 3, true);
        Border bordeInterior = BorderFactory.createEmptyBorder(15, 15, 15, 15);
        titulo.setBorder(BorderFactory.createCompoundBorder(bordeExterior, bordeInterior));

        // Agregar icono al título
        javax.swing.ImageIcon icono = UiStyles.cargarIconoEscalado(
                getClass(),
                "/practica2/resources/reportes.png",
                UiTheme.ICONO_TITULO_W,
                UiTheme.ICONO_TITULO_H);
        if (icono != null) {
            titulo.setIcon(icono);
            titulo.setIconTextGap(15); // Espacio entre el icono y el texto

            // Ajustar la posición del texto respecto al icono
            titulo.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
            titulo.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
        }
    }

    /**
     * Configura el estilo del TabbedPane
     */
    private void configurarTabbedPane() {
        // Configurar colores del TabbedPane
        tabbedPaneReportes.setBackground(COLOR_NEGRO);
        tabbedPaneReportes.setForeground(COLOR_BLANCO);

        // Personalizar el TabbedPane con borde neón
        tabbedPaneReportes.setBorder(BorderFactory.createLineBorder(COLOR_VERDE_NEON, 2));

        // Configurar colores de las pestañas
        tabbedPaneReportes.setUI(new javax.swing.plaf.basic.BasicTabbedPaneUI() {
            @Override
            protected void installDefaults() {
                super.installDefaults();
                highlight = COLOR_AZUL_NEON;
                lightHighlight = COLOR_VERDE_NEON;
                shadow = COLOR_NEGRO;
                darkShadow = COLOR_MORADO_OSCURO;
                focus = COLOR_ROSA_NEON;

                // Eliminar el borde punteado alrededor de la pestaña seleccionada
                tabInsets = new java.awt.Insets(2, 8, 2, 8);
                selectedTabPadInsets = new java.awt.Insets(2, 8, 2, 8);
                contentBorderInsets = new java.awt.Insets(4, 4, 4, 4);
            }

            // Eliminar el indicador de enfoque (borde punteado)
            @Override
            protected void paintFocusIndicator(Graphics g, int tabPlacement,
                    Rectangle[] rects, int tabIndex,
                    Rectangle iconRect, Rectangle textRect,
                    boolean isSelected) {
                // No hacer nada para eliminar el borde punteado y se vea GOD
            }

            // Personalizar el fondo de las pestañas
            @Override
            protected void paintTabBackground(Graphics g, int tabPlacement,
                    int tabIndex, int x, int y, int w, int h,
                    boolean isSelected) {
                Graphics2D g2d = (Graphics2D) g;
                if (isSelected) {
                    // Fondo con degradado para las pestañas seleccionadas
                    GradientPaint gp = new GradientPaint(
                            x, y, COLOR_MORADO_OSCURO,
                            x, y + h, COLOR_NEGRO);
                    g2d.setPaint(gp);
                } else {
                    g2d.setColor(COLOR_NEGRO);
                }
                g2d.fillRect(x, y, w, h);

                // Añadir un borde neón alrededor de la pestaña seleccionada
                if (isSelected) {
                    g2d.setColor(COLOR_AZUL_NEON);
                    g2d.drawLine(x, y + h - 1, x + w, y + h - 1);
                }
            }

            // Personalizar el área de contenido
            @Override
            protected void paintContentBorderTopEdge(Graphics g, int tabPlacement,
                    int selectedIndex,
                    int x, int y, int w, int h) {
                g.setColor(COLOR_ROSA_NEON);
                g.drawLine(x, y, x + w, y);
            }

            @Override
            protected void paintContentBorder(Graphics g, int tabPlacement,
                    int selectedIndex) {
                // Personalizar el borde del área de contenido
                int width = tabPane.getWidth();
                int height = tabPane.getHeight();

                g.setColor(COLOR_VERDE_NEON);

                switch (tabPlacement) {
                    case TOP:
                        g.drawRect(0, calculateTabAreaHeight(tabPlacement, runCount, maxTabHeight) - 1,
                                width - 1, height - calculateTabAreaHeight(tabPlacement, runCount, maxTabHeight));
                        break;
                    default:
                        g.drawRect(0, 0, width - 1, height - 1);
                }
            }

            @Override
            protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex,
                    int x, int y, int w, int h, boolean isSelected) {
                Graphics2D g2d = (Graphics2D) g;
                if (isSelected) {
                    // Borde de las pestañas seleccionadas
                    g2d.setColor(COLOR_VERDE_NEON);
                } else {
                    // Borde de las pestañas no seleccionadas
                    g2d.setColor(COLOR_MORADO_OSCURO);
                }

                // Dibujar el borde de la pestaña
                g2d.drawLine(x, y, x + w, y); // línea superior
                g2d.drawLine(x, y, x, y + h); // línea izquierda
                g2d.drawLine(x + w - 1, y, x + w - 1, y + h); // línea derecha
            }
        });

        // Configurar colores de fondo de las pestañas y paneles
        panelGanadoresBuscaMinas.setBackground(COLOR_NEGRO);
        panelPerdedoresBuscaMinas.setBackground(COLOR_NEGRO);
        panelHunting.setBackground(COLOR_NEGRO);
    }

    /**
     * Configura el estilo de las tablas
     */
    private void configurarTablas() {
        // Configurar tablas con estilo gamer
        configTabla(tablaGanadores);
        configTabla(tablaPerdedores);
        configTabla(tablaHunting);
    }

    /**
     * Configura una tabla específica con el estilo gamer
     * 
     * @param tabla La tabla a configurar
     */
    private void configTabla(javax.swing.JTable tabla) {
        // Configurar colores de la tabla
        tabla.setBackground(COLOR_NEGRO);
        tabla.setForeground(COLOR_BLANCO);
        tabla.setGridColor(COLOR_AZUL_NEON);
        tabla.setSelectionBackground(COLOR_MORADO_OSCURO);
        tabla.setSelectionForeground(COLOR_VERDE_NEON);

        // Importante: el código generado por NetBeans fija preferredSize (ej. 850x400),
        // lo que limita el alto preferido de la JTable y “corta” el scroll (~8 filas con rowHeight=50).
        // Resetearlo permite que el JScrollPane calcule correctamente el tamaño y muestre toda la lista.
        tabla.setPreferredSize(null);
        tabla.setFillsViewportHeight(true);

        // Configurar el encabezado de la tabla
        tabla.getTableHeader().setBackground(COLOR_MORADO_OSCURO);
        tabla.getTableHeader().setForeground(COLOR_AZUL_NEON);
        tabla.getTableHeader().setFont(new java.awt.Font("Segoe UI Black", 1, 14));

        // Configurar borde con efecto neón
        tabla.setBorder(BorderFactory.createLineBorder(COLOR_ROSA_NEON, 1));

        // Configurar fuente de la tabla
        tabla.setFont(new java.awt.Font("Segoe UI", 0, 14));

        // Hacer que la tabla no sea editable
        tabla.setDefaultEditor(Object.class, null);

        // Configurar color del JScrollPane que contiene la tabla
        javax.swing.JScrollPane scrollPane = (javax.swing.JScrollPane) tabla.getParent().getParent();
        scrollPane.getViewport().setBackground(COLOR_NEGRO);
        scrollPane.setBorder(BorderFactory.createLineBorder(COLOR_AZUL_NEON, 2));
        tabla.getColumnModel().getColumn(3).setCellRenderer((table, value, isSelected, hasFocus, row, column) -> {
            JTextArea textArea = new JTextArea(value != null ? value.toString() : "");
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setOpaque(true);
            textArea.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
            textArea.setForeground(isSelected ? table.getSelectionForeground() : table.getForeground());
            textArea.setFont(table.getFont());
            return textArea;
        });

        tabla.setRowHeight(50); 

        scrollPane.revalidate();
        scrollPane.repaint();

        // centrar el texto de cada columna

    }

    /**
     * Centra todos los componentes en el panel
     */
    private void centrarComponentes() {
        // Centro el título
        titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Centro el tabbedPane
        tabbedPaneReportes.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Centro el botón
        regresarMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    /**
     * Sobreescribe el método paintComponent para dibujar un fondo degradado
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        // Usar un degradado del negro al morado oscuro
        GradientPaint gp = new GradientPaint(0, 0, COLOR_NEGRO, 0, getHeight(), COLOR_MORADO_OSCURO);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }

    /**
     * Actualiza la tabla de ganadores de Buscaminas
     * 
     * @param datos Array de objetos que contiene los datos para mostrar en la tabla
     */
    public void actualizarTablaGanadores(List<ReporteBuscaminasRegistro> datos) {
        javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) tablaGanadores.getModel();
        modelo.setRowCount(0); // Limpia la tabla

        for (ReporteBuscaminasRegistro fila : datos) {
            modelo.addRow(new Object[] {
                    fila.jugador(),
                    fila.casillasDescubiertas(),
                    fila.minasMarcadas(),
                    (int) (fila.duracionMs() / 1000)
            });
        }

        tablaGanadores.revalidate();
        tablaGanadores.repaint();
    }

    /**
     * Actualiza la tabla de perdedores de Buscaminas
     * 
     * @param datos Array de objetos que contiene los datos para mostrar en la tabla
     */
    public void actualizarTablaPerdedores(List<ReporteBuscaminasRegistro> datos) {
        javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) tablaPerdedores.getModel();
        modelo.setRowCount(0); // Limpia la tabla

        for (ReporteBuscaminasRegistro fila : datos) {
            modelo.addRow(new Object[] {
                    fila.jugador(),
                    fila.casillasDescubiertas(),
                    fila.minasMarcadas(),
                    (int) (fila.duracionMs() / 1000)
            });
        }

        tablaPerdedores.revalidate();
        tablaPerdedores.repaint();
    }

    /**
     * Actualiza la tabla de partidas de Hunting
     * 
     * @param datos Array de objetos que contiene los datos para mostrar en la tabla
     */
    public void actualizarTablaHunting(List<ReporteHuntingRegistro> datos) {
        javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) tablaHunting.getModel();
        modelo.setRowCount(0); // Limpia la tabla

        for (ReporteHuntingRegistro fila : datos) {
            modelo.addRow(new Object[] {
                    fila.jugador(),
                    fila.aciertos(),
                    fila.fallos(),
                    fila.configuracion()
            });
        }

        tablaHunting.revalidate();
        tablaHunting.repaint();
    }

    /**
     * Limpia todas las tablas de reportes
     */
    public void limpiarTablas() {
        ((javax.swing.table.DefaultTableModel) tablaGanadores.getModel()).setRowCount(0);
        ((javax.swing.table.DefaultTableModel) tablaPerdedores.getModel()).setRowCount(0);
        ((javax.swing.table.DefaultTableModel) tablaHunting.getModel()).setRowCount(0);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        titulo = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        tabbedPaneReportes = new javax.swing.JTabbedPane();
        panelGanadoresBuscaMinas = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaGanadores = new javax.swing.JTable();
        panelPerdedoresBuscaMinas = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaPerdedores = new javax.swing.JTable();
        panelHunting = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaHunting = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        regresarMenu = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(1080, 700));
        setMinimumSize(new java.awt.Dimension(1080, 700));
        setPreferredSize(new java.awt.Dimension(1080, 700));
        setLayout(new java.awt.BorderLayout());

        jPanel1.setOpaque(false);

        titulo.setFont(new java.awt.Font("Georgia", 1, 36)); // NOI18N
        titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titulo.setText("Reportes de Partidas");
        titulo.setAlignmentY(0.0F);
        titulo.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15));
        titulo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        titulo.setMaximumSize(new java.awt.Dimension(999, 999));
        titulo.setMinimumSize(new java.awt.Dimension(0, 0));
        titulo.setPreferredSize(new java.awt.Dimension(900, 150));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(titulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addComponent(titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(762, 450));

        tabbedPaneReportes.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tabbedPaneReportes.setPreferredSize(new java.awt.Dimension(850, 365));

        panelGanadoresBuscaMinas.setPreferredSize(new java.awt.Dimension(850, 400));
        panelGanadoresBuscaMinas.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setPreferredSize(new java.awt.Dimension(850, 400));

        tablaGanadores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Jugador", "Casillas Descubiertas", "Minas Marcadas", "Tiempo (s)"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tablaGanadores.setPreferredSize(new java.awt.Dimension(850, 400));
        jScrollPane1.setViewportView(tablaGanadores);

        panelGanadoresBuscaMinas.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        tabbedPaneReportes.addTab("Ganadores Buscaminas", panelGanadoresBuscaMinas);

        panelPerdedoresBuscaMinas.setPreferredSize(new java.awt.Dimension(850, 400));

        jScrollPane2.setPreferredSize(new java.awt.Dimension(850, 400));

        tablaPerdedores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Jugador", "Casillas Descubiertas", "Minas Marcadas", "Tiempo (s)"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tablaPerdedores.setPreferredSize(new java.awt.Dimension(850, 400));
        jScrollPane2.setViewportView(tablaPerdedores);

        javax.swing.GroupLayout panelPerdedoresBuscaMinasLayout = new javax.swing.GroupLayout(panelPerdedoresBuscaMinas);
        panelPerdedoresBuscaMinas.setLayout(panelPerdedoresBuscaMinasLayout);
        panelPerdedoresBuscaMinasLayout.setHorizontalGroup(
            panelPerdedoresBuscaMinasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelPerdedoresBuscaMinasLayout.setVerticalGroup(
            panelPerdedoresBuscaMinasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
        );

        tabbedPaneReportes.addTab("Perdedores Buscaminas", panelPerdedoresBuscaMinas);

        panelHunting.setPreferredSize(new java.awt.Dimension(850, 400));

        jScrollPane3.setPreferredSize(new java.awt.Dimension(850, 400));

        tablaHunting.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Jugador", "Aciertos", "Fallos", "Configuración de Juego"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tablaHunting.setPreferredSize(new java.awt.Dimension(850, 400));
        jScrollPane3.setViewportView(tablaHunting);

        javax.swing.GroupLayout panelHuntingLayout = new javax.swing.GroupLayout(panelHunting);
        panelHunting.setLayout(panelHuntingLayout);
        panelHuntingLayout.setHorizontalGroup(
            panelHuntingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelHuntingLayout.setVerticalGroup(
            panelHuntingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
        );

        tabbedPaneReportes.addTab("Hunting", panelHunting);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tabbedPaneReportes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tabbedPaneReportes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(362, 120));

        regresarMenu.setFont(new java.awt.Font("Segoe UI Black", 1, 22)); // NOI18N
        regresarMenu.setText("Volver al Menú");
        regresarMenu.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15));
        regresarMenu.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        regresarMenu.setMaximumSize(new java.awt.Dimension(999, 999));
        regresarMenu.setMinimumSize(new java.awt.Dimension(0, 0));
        regresarMenu.setPreferredSize(new java.awt.Dimension(350, 60));
        regresarMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                regresarMenuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(regresarMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(regresarMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        add(jPanel3, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void regresarMenuActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_regresarMenuActionPerformed
        if (marco != null) {
            marco.volverAlMenu();
        } else {
            System.err.println("Error: marco no está inicializado.");
        }
    }// GEN-LAST:event_regresarMenuActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel panelGanadoresBuscaMinas;
    private javax.swing.JPanel panelHunting;
    private javax.swing.JPanel panelPerdedoresBuscaMinas;
    private javax.swing.JButton regresarMenu;
    private javax.swing.JTabbedPane tabbedPaneReportes;
    private javax.swing.JTable tablaGanadores;
    private javax.swing.JTable tablaHunting;
    private javax.swing.JTable tablaPerdedores;
    private javax.swing.JLabel titulo;
    // End of variables declaration//GEN-END:variables
}
