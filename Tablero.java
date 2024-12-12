import java.awt.*;
import javax.swing.*;

public class Tablero extends JFrame {
    public ImageIcon shotgun = new ImageIcon("./images/shotgun.png");
    public ImageIcon corazonLleno = new ImageIcon("./images/vida.png");
    public ImageIcon fondo = new ImageIcon("./images/bg.jpg");
    public ImageIcon imagenP1 = new ImageIcon("./images/p1.png");
    public ImageIcon imagenP2 = new ImageIcon("./images/p2.png");
    public ImageIcon balaAzulIcon = new ImageIcon("./images/balaAzul.png");
    public ImageIcon balaRojaIcon = new ImageIcon("./images/balaRoja.png");
    public JLabel imagenJugadorActual; // Muestra el jugador actual
    public JPanel panelVidasJ1, panelVidasJ2;
    public JPanel panelInventario;
    public JTextArea logEventos;
    public boolean turnoJugador1 = false;
    public int vidaJ1 = 4;
    public int vidaJ2 = 4;
    Escopeta EscopetaOBJ = new Escopeta();
    // Ingreso de nombres de jugadores
    String P1Nombre = JOptionPane.showInputDialog("Ingrese el nombre del jugador 1:");
    public Jugador p1 = new Jugador(P1Nombre);

    String P2Nombre = JOptionPane.showInputDialog("Ingrese el nombre del jugador 2:");
    public Jugador p2 = new Jugador(P2Nombre);

    public Tablero() {
        setTitle("Peruvian Roulette");
        setSize(800, 600);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createContents();
        setVisible(true);
    }

    public void generarInventario() {
        // Aquí puedes agregar lógica para generar objetos aleatorios al inventario
        panelInventario.removeAll();
        JLabel objetoEjemplo = new JLabel("Objeto de Ejemplo");
        panelInventario.add(objetoEjemplo);
        panelInventario.revalidate();
        panelInventario.repaint();
    }

    public void createContents() {
        // Panel izquierdo: Vidas
        JPanel panelVidas = new JPanel();
        panelVidas.setLayout(new GridLayout(2, 1));

        panelVidasJ1 = new JPanel(new FlowLayout(FlowLayout.LEFT));/*LEFT hace que no se ajuste al centro y quede más chevere */
        actualizarVidas(panelVidasJ1, vidaJ1);
        panelVidas.add(panelVidasJ1);

        panelVidasJ2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actualizarVidas(panelVidasJ2, vidaJ2);
        panelVidas.add(panelVidasJ2);

        add(panelVidas, BorderLayout.WEST);
        /*PANEL CENTRAL */
        JPanel panelCentral = new JPanel(new BorderLayout());
        JButton botonEscopeta = new JButton(shotgun);
        botonEscopeta.setToolTipText("Úsame...");
        botonEscopeta.addActionListener(e -> mostrarOpcionesDisparo());
        panelCentral.add(botonEscopeta, BorderLayout.CENTER);
        add(panelCentral, BorderLayout.CENTER);
        
        /*PANEL IZQUIERDO*/

        panelInventario = new JPanel(new GridLayout(8, 1));
        panelInventario.setBorder(BorderFactory.createTitledBorder("Inventario"));
        generarInventario();
        add(panelInventario, BorderLayout.EAST);

        /*PANEL INFERIOR*/
        JPanel panelInferior = new JPanel(new BorderLayout());

        imagenJugadorActual = new JLabel(imagenP1); // Jugador 1 empieza
        panelInferior.add(imagenJugadorActual, BorderLayout.WEST);

        logEventos = new JTextArea(5, 30);
        logEventos.setEditable(false);
        JScrollPane scrollLog = new JScrollPane(logEventos);
        scrollLog.setPreferredSize(new Dimension(300, 30)); // Ancho: 300, Alto: 80

        scrollLog.setBorder(BorderFactory.createTitledBorder("Eventos del Juego"));
        panelInferior.add(scrollLog, BorderLayout.CENTER);

        add(panelInferior, BorderLayout.SOUTH);
        logEventos.append(EscopetaOBJ.cargarBalas());
    }

    public void mostrarOpcionesDisparo() {
        String[] opciones = {"Al enemigo", "A ti mismo"};
        int respuesta = JOptionPane.showOptionDialog(
                this, "¿A quién deseas disparar?", "Disparo",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                shotgun, opciones, opciones[0]
        );

        if (respuesta == 0) { // Al enemigo
            if (turnoJugador1) {
                vidaJ2--;
                logEventos.append("J1 disparó a J2. Vida de J2: " + vidaJ2 + "\n");
            } else {
                vidaJ1--;
                logEventos.append("J2 disparó a J1. Vida de J1: " + vidaJ1 + "\n");
            }
        } else if (respuesta == 1) { // A sí mismo
            if (turnoJugador1) {
                vidaJ1--;
                logEventos.append("J1 disparó a sí mismo. Vida de J1: " + vidaJ1 + "\n");
            } else {
                vidaJ2--;
                logEventos.append("J2 disparó a sí mismo. Vida de J2: " + vidaJ2 + "\n");
            }
        }

        actualizarVidas(panelVidasJ1, vidaJ1);
        actualizarVidas(panelVidasJ2, vidaJ2);
        verificarGanador();
        cambiarTurno();
    }

    public void actualizarVidas(JPanel panelVidas, int vidas) {
        panelVidas.removeAll();
        for (int i = 0; i < vidas; i++) {
            JLabel corazon = new JLabel(corazonLleno);
            panelVidas.add(corazon);
        }
        panelVidas.revalidate();
        panelVidas.repaint();
    }
    /*Se muestra y verifica al ganador */
    public void verificarGanador() {
        if (vidaJ1 <= 0 || vidaJ2 <= 0) {
            String ganador = (vidaJ1 > 0) ? p1.getnombre() : p2.getnombre();
            JOptionPane.showMessageDialog(this, "¡El ganador es " + ganador + "!");
            System.exit(0);
        }
    }
    /*Se cambia de turno e imagen que se muestra en pantalla */
    public void cambiarTurno() {
        turnoJugador1 = !turnoJugador1;
        if (turnoJugador1)
            imagenJugadorActual.setIcon(imagenP1);
        else
            imagenJugadorActual.setIcon(imagenP2);
        generarInventario();
    }
    
    public static void main(String[] args) {
        new Tablero();
    }
}


