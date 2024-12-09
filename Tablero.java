import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Tablero extends JFrame {
    /*Añadir clases de jugadores */
    public ImageIcon shotgun = new ImageIcon("./images/shotgun.png");
    private JLabel imagenJugador1, imagenJugador2;
    private JLabel vidasJugador1, vidasJugador2;
    private JPanel panelInventario;
    private JTextArea logEventos;
    private boolean turnoJugador1 = true;

    private int vidaJ1 = 4;
    private int vidaJ2 = 4;

    public Tablero() {
        setTitle("Duelo 1v1: Buckshot Roulette");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel izquierdo: Vidas
        JPanel panelVidas = new JPanel();
        panelVidas.setLayout(new GridLayout(2, 1));
        vidasJugador1 = new JLabel("Vida J1: " + vidaJ1);
        vidasJugador2 = new JLabel("Vida J2: " + vidaJ2);
        panelVidas.add(vidasJugador1);
        panelVidas.add(vidasJugador2);
        add(panelVidas, BorderLayout.WEST);

        // Panel central: Escopeta y fondo
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BorderLayout());
        JButton botonEscopeta = new JButton("Escopeta");
        botonEscopeta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarOpcionesDisparo();
            }
        });
        panelCentral.add(botonEscopeta, BorderLayout.CENTER);
        add(panelCentral, BorderLayout.CENTER);

        // Panel derecho: Inventario
        panelInventario = new JPanel();
        panelInventario.setLayout(new GridLayout(8, 1)); // Espacio para 5 objetos
        panelInventario.setBorder(BorderFactory.createTitledBorder("Inventario"));
        generarInventario(); // Inventario inicial del J1
        add(panelInventario, BorderLayout.EAST);

        // Panel inferior: Log de eventos y jugador activo
        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new BorderLayout());

        // Imagen del jugador activo
        imagenJugador1 = new JLabel(new ImageIcon("./images/p1.png")); // Reemplazar con ruta de la imagen
        imagenJugador2 = new JLabel(new ImageIcon("./images/p2.png")); // Reemplazar con ruta de la imagen
        panelInferior.add(imagenJugador1, BorderLayout.WEST);

        // Log de eventos
        logEventos = new JTextArea(5, 30);
        logEventos.setEditable(false);
        JScrollPane scrollLog = new JScrollPane(logEventos);
        scrollLog.setBorder(BorderFactory.createTitledBorder("Eventos del Juego"));
        panelInferior.add(scrollLog, BorderLayout.CENTER);

        add(panelInferior, BorderLayout.SOUTH);
    }

    private void generarInventario() {
        /*Aquí debemos de agregar con Random los objetos a la clase Jugador y mostrarlos en el panel */
    }

    private void mostrarOpcionesDisparo() {
        String[] opciones = {"A ti mismo", "Al enemigo"};
        int respuesta = JOptionPane.showOptionDialog(null, "Disparar...", "", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, shotgun, opciones, opciones[0]);
        if (respuesta == 0) { // Disparar al oponente
            if (turnoJugador1) {
                /*Llamar a escopeta para que haga el daño corresponiente */
                vidaJ2--;
                logEventos.append("J1 disparó a J2. Vida de J2: " + vidaJ2 + "\n");
            } else {
                vidaJ1--;
                logEventos.append("J2 disparó a J1. Vida de J1: " + vidaJ1 + "\n");
            }
        } else if (respuesta == 1) { // Disparar a sí mismo
            if (turnoJugador1) {
                vidaJ1--;
                logEventos.append("J1 disparó a sí mismo. Vida de J1: " + vidaJ1 + "\n");
            } else {
                vidaJ2--;
                logEventos.append("J2 disparó a sí mismo. Vida de J2: " + vidaJ2 + "\n");
            }
        }
        /*Agrega logs para el uso de obejtos */

        actualizarVidas();
        verificarGanador();
        cambiarTurno();
    }

    private void actualizarVidas() {
        vidasJugador1.setText("Vida J1: " + vidaJ1);
        vidasJugador2.setText("Vida J2: " + vidaJ2);
    }

    private void verificarGanador() {
        if (vidaJ1 <= 0 || vidaJ2 <= 0) {
            String ganador = vidaJ1 > 0 ? "J1" : "J2";
            JOptionPane.showMessageDialog(this, "¡El ganador es " + ganador + "!");
            System.exit(0);
        }
    }

    private void cambiarTurno() {
        turnoJugador1 = !turnoJugador1;

        // Cambiar imágenes
        if (turnoJugador1) {
            imagenJugador1.setIcon(new ImageIcon("./images/p1.png"));
            imagenJugador2.setIcon(new ImageIcon("./images/p2.png"));
        } else {
            imagenJugador1.setIcon(new ImageIcon("./images/p2.png"));
            imagenJugador2.setIcon(new ImageIcon("./images/p1.png"));
        }

        generarInventario(); 
    }

    public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> {
            Tablero frame = new Tablero();
            frame.setVisible(true);
        });
    }
}
