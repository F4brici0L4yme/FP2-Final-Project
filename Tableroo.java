import java.awt.*;
import javax.swing.*;

public class Tableroo extends JFrame{
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
    public Escopeta escopeta = new Escopeta();
    public boolean turnoJugador1 = true;
    public Jugador jugador1 = new Jugador("P1");
    public Jugador jugador2 = new Jugador("P2");
    public Tableroo() {
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
        botonCaja.addActionListener(e -> mostrarInventario(turnoJugador1 ? jugador1 : jugador2));
        add(botonCaja, BorderLayout.EAST);

        /*Abajo */
        JPanel panelInferior = new JPanel(new BorderLayout());

        imagenJugadorActual = new JLabel(imagenP1);
        panelInferior.add(imagenJugadorActual, BorderLayout.WEST);

        logEventos = new JTextArea(5, 30);
        logEventos.setEditable(false);
        panelInferior.add(logEventos, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
        mostrarInformacionEscopeta(logEventos);
        
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
                if(escopeta.getMunicion().get(0).equals("rojo")){
                    escopeta.disparar(jugador2);
                    logEventos.append("Disparaste a J2");
                }
                else{
                    logEventos.append("Era una bala falsa...");
                }
            } else {
                if(escopeta.getMunicion().get(0).equals("rojo")){
                    escopeta.disparar(jugador1);
                    logEventos.append("Disparaste a J1");
                }
                else{
                    logEventos.append("Era una bala falsa...");
                }
            }
        } else if (respuesta == 1) {
            if (turnoJugador1) {
                if(escopeta.getMunicion().get(0).equals("rojo")){
                    escopeta.disparar(jugador1);
                    logEventos.append("Era una bala verdadera...");
                }
                else{
                    logEventos.append("Era una bala falsa...¡Ganas un turno por tu valentía!");
                }
            } else {
                if(escopeta.getMunicion().get(0).equals("rojo")){
                    escopeta.disparar(jugador2);
                    logEventos.append("Era una bala verdadera...");
                }
                else{
                    logEventos.append("Era una bala falsa...¡Ganas un turno por tu valentía!");
                }
            }
        }
        if(escopeta.getMunicion().get(0).equals("rojo") || !jugador1.estaEsposado())
            cambiarTurno();
        else if(jugador1.estaEsposado()) {
            jugador1.setEstaEsposado(false);
        }
        actualizarVidas(panelVidasJ1, jugador1.getVida());
        actualizarVidas(panelVidasJ2, jugador2.getVida());
        if(escopeta.getMunicion().isEmpty()){
            logEventos.append("Fin de la ronda...");
            escopeta.cargarBalas();
            for(int i = 0; i<escopeta.getMunicion().size(); i++){
                logEventos.append(escopeta.getMunicion().get(i));
            }
        }
        verificarGanador();

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
        if (turnoJugador1){
            imagenJugadorActual.setIcon(imagenP2);
            mostrarInventario(jugador1);
        }
        else{
            imagenJugadorActual.setIcon(imagenP1);
            mostrarInventario(jugador2);
        }
    }


    public void mostrarInventario(Jugador jugador) {
        if (jugador.getCajaObjetos().getCaja().isEmpty()) {
            JOptionPane.showMessageDialog(this, jugador.getNombre() + " no tiene objetos en su caja.");
            return;
        }
        jugador.mostrarObjetos();
    }
    
    public void mostrarInformacionEscopeta(JTextArea logEventos){
        logEventos.append("La escopeta se cargó con " + escopeta.getMunicion().size() + " cartuchos\n");
        for(int i = 0; i<escopeta.getMunicion().size(); i++){
            logEventos.append(escopeta.getMunicion().get(i).toUpperCase() + " ");
        }
    }

    public void usar(Jugador jugadorActual, String objetoEnTexto, Jugador jugador2, Escopeta escopeta){
        
            if (jugadorActual.objeto_a_Usar() instanceof Adrenalina) {
                ((Adrenalina) jugadorActual.objeto_a_Usar()).usarAdrenalina(jugador2.getCajaObjetos());
            }
            else if (jugadorActual.objeto_a_Usar() instanceof Cerveza) {
                ((Cerveza) jugadorActual.objeto_a_Usar()).UsarCerveza(escopeta);
            }
            else if (jugadorActual.objeto_a_Usar() instanceof Cigarro) {
                ((Cigarro) jugadorActual.objeto_a_Usar()).utilizarCigarro(jugadorActual);
            }
            else if (jugadorActual.objeto_a_Usar() instanceof Esposa) {
                ((Esposa) jugadorActual.objeto_a_Usar()).usarEsposa(jugadorActual);
            }
            else if (jugadorActual.objeto_a_Usar() instanceof Lupa) {
                ((Lupa) jugadorActual.objeto_a_Usar()).UsarLupa(escopeta);
            }
            else {
                ((Sierra) jugadorActual.objeto_a_Usar()).usarSierra(escopeta);
            }
    }
    public void repartirCajaJugador(Jugador jugador) {
        jugador.repartirObjetos();
    }
    public void main(String[] args) {
        new Tableroo();
    }
}


