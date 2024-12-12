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
    public ImageIcon cajaIcon = new ImageIcon("./images/cofre_cerrado.png");
    public JLabel imagenJugadorActual;
    public JPanel panelVidasJ1, panelVidasJ2;
    public JPanel panelInventario;
    public JTextArea logEventos;
    public boolean turnoJugador1 = false;
    public Escopeta escopetaObj = new Escopeta();
    public Jugador jugador1 = new Jugador("P1");
    public Jugador jugador2 = new Jugador("P2");
    public Tablero() {
        setTitle("Peruvian Roulette");
        setSize(800, 600);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        createContents();
        setVisible(true);
    }
    public void createContents() {
        /*Centro*/
        JButton botonEscopeta = new JButton(shotgun);
        add(botonEscopeta, BorderLayout.CENTER);
        botonEscopeta.addActionListener(e -> mostrarOpcionesEscopeta());

        /*Izquierda */
        JPanel panelVidas = new JPanel(new GridLayout(2, 1));
        panelVidasJ1 = new JPanel(new FlowLayout(FlowLayout.LEFT));/*LEFT hace que no se ajuste al centro y quede más chevere */
        actualizarVidas(panelVidasJ1, jugador1.getVida());
        panelVidas.add(panelVidasJ1);

        panelVidasJ2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actualizarVidas(panelVidasJ2, jugador2.getVida());
        panelVidas.add(panelVidasJ2);

        add(panelVidas, BorderLayout.WEST);
        /*Derecha */
        JButton botonCaja = new JButton(cajaIcon);
        // botonCaja.addActionListener(e -> mostrarInventario());
        add(botonCaja, BorderLayout.EAST);

        /*Abajo */
        JPanel panelInferior = new JPanel(new BorderLayout());

        imagenJugadorActual = new JLabel(imagenP1);
        panelInferior.add(imagenJugadorActual, BorderLayout.WEST);

        logEventos = new JTextArea(5, 30);
        logEventos.setEditable(false);
        panelInferior.add(logEventos, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
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
    /*Menú que sale al darle click a la escopeta */
    public void mostrarOpcionesEscopeta() {
        String[] opciones = {"Al enemigo", "A ti mismo"};
        int respuesta = JOptionPane.showOptionDialog(
                this, "¿A quién deseas disparar?", "Disparo",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, opciones, opciones[0]);
        if (respuesta == 0) {
            if (turnoJugador1) {
                jugador2.reducirVida(escopetaObj.getDañoActual());
                logEventos.append("J1 disparó a J2. Vida de J2: " + jugador2.getVida() + "\n");
            } else {
                jugador1.reducirVida(escopetaObj.getDañoActual());;
                logEventos.append("J2 disparó a J1. Vida de J1: " + jugador1.getVida() + "\n");
            }
        } else if (respuesta == 1) {
            if (turnoJugador1) {
                jugador1.reducirVida(escopetaObj.getDañoActual());
                logEventos.append("J1 disparó a sí mismo. Vida de J1: " + jugador1.getVida() + "\n");
            } else {
                jugador2.reducirVida(escopetaObj.getDañoActual());
                logEventos.append("J2 disparó a sí mismo. Vida de J2: " + jugador2.getVida() + "\n");
            }
        }

        actualizarVidas(panelVidasJ1, jugador1.getVida());
        actualizarVidas(panelVidasJ2, jugador2.getVida());
        verificarGanador();
        cambiarTurno();
    }
    public void verificarGanador() {
        if (jugador1.getVida() <= 0 || jugador2.getVida() <= 0) {
            String ganador = (jugador1.getVida() > 0) ? jugador1.getNombre() : jugador2.getNombre();
            JOptionPane.showMessageDialog(this, "¡El ganador es " + ganador + "!");
            System.exit(0);
        }
    }
    public void cambiarTurno() {
        turnoJugador1 = !turnoJugador1;
        if (turnoJugador1)
            imagenJugadorActual.setIcon(imagenP2);
        else
            imagenJugadorActual.setIcon(imagenP1);
        // mostrarInventario();
    }
    // public void mostrarInventario(){
    //     if(turnoJugador1)
            
    //     else
    // }
    public static void main(String[] args) {
        new Tablero();
    }
}


