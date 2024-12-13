import java.awt.*;
import javax.swing.*;

public class Tableroo extends JFrame {
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
    public boolean turnoJugador1 = false;
    public Jugador jugador1 = new Jugador(JOptionPane.showInputDialog("Ingrese el nombre del jugador 1"));
    public Jugador jugador2 = new Jugador(JOptionPane.showInputDialog("Ingrese el nombre del jugador 2"));
    
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
        JPanel panelCajas = new JPanel(new GridLayout(4,1));
        JButton botonCaja = new JButton(cajaIcon);
        JButton botonCajaEnemiga = new JButton(cajaIcon);
        
        botonCaja.addActionListener(e -> seleccionarInventario(turnoJugador1 ? jugador1 : jugador2));
        botonCajaEnemiga.addActionListener(e -> mostrarInventario(turnoJugador1 ? jugador2 : jugador1));
        panelCajas.add(new JLabel("Caja Enemiga"));
        panelCajas.add(botonCajaEnemiga);
        panelCajas.add(new JLabel("Mi caja"));
        panelCajas.add(botonCaja);
        add(panelCajas, BorderLayout.EAST);

        /*Abajo */
        JPanel panelInferior = new JPanel(new BorderLayout());

        imagenJugadorActual = new JLabel(imagenP1);
        panelInferior.add(imagenJugadorActual, BorderLayout.WEST);

        logEventos = new JTextArea(5, 30);
        logEventos.setEditable(false);
        logEventos.setLineWrap(true);
        logEventos.setWrapStyleWord(true);
        panelInferior.add(logEventos, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
        repartirCajaJugador(jugador1);
        repartirCajaJugador(jugador2);
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
        String balaDisparada = escopeta.getMunicion().get(0);
        if (respuesta == 0) {
            if (turnoJugador1) {
                if(escopeta.getMunicion().get(0).equals("roja")){
                    escopeta.disparar(jugador2);
                    logEventos.append("Disparaste a " + jugador2.getNombre() + "\n");
                }
                else{
                    logEventos.append("Era una bala falsa...\n");
                }
                escopeta.getMunicion().remove(0);
            } else {
                if(escopeta.getMunicion().get(0).equals("roja")){
                    escopeta.disparar(jugador1);
                    logEventos.append("Disparaste a " + jugador1.getNombre() + "\n");
                }
                else{
                    logEventos.append("Era una bala falsa...\n");
                }
                escopeta.getMunicion().remove(0);
            }
        } else if (respuesta == 1) {
            if (turnoJugador1) {
                if(escopeta.getMunicion().get(0).equals("roja")){
                    escopeta.disparar(jugador1);
                    logEventos.append("Era una bala verdadera...\n");
                }
                else{
                    logEventos.append("Era una bala falsa...¡Ganas un turno por tu valentía!\n");
                }
                escopeta.getMunicion().remove(0);
            } else {
                if(escopeta.getMunicion().get(0).equals("roja")){
                    escopeta.disparar(jugador2);
                    logEventos.append("Era una bala verdadera...\n");
                }
                else{
                    logEventos.append("Era una bala falsa...¡Ganas un turno por tu valentía!\n");
                }
                escopeta.getMunicion().remove(0);
            }
        }
        verificarGanador();
        if(escopeta.getMunicion().isEmpty()){
            JOptionPane.showMessageDialog(null, "Ronda terminada...");
            escopeta.cargarBalas();
            logEventos.append("Nuevas balas insertadas:\n");
            mostrarInformacionEscopeta(logEventos);
            repartirCajaJugador(jugador1);
            repartirCajaJugador(jugador2);
        }
        if(balaDisparada.equals("roja"))
            cambiarTurno();
        // if(jugador1.estaEsposado()) {
        //     jugador1.setEstaEsposado(false);
        // }
        actualizarVidas(panelVidasJ1, jugador1.getVida());
        actualizarVidas(panelVidasJ2, jugador2.getVida());
    }
    
    public void verificarGanador() {
        if (jugador1.getVida() <= 0 || jugador2.getVida() <= 0) {
            String ganador = (jugador1.getVida() > 0) ? jugador1.getNombre() : jugador2.getNombre();
            JOptionPane.showMessageDialog(this, "¡El ganador es " + ganador + "!");
        }
    }
    
    public void cambiarTurno() {
        turnoJugador1 = !turnoJugador1;
        if (turnoJugador1){
            imagenJugadorActual.setIcon(imagenP2);
        }
        else{
            imagenJugadorActual.setIcon(imagenP1);
        }
    }

    public void mostrarInventario(Jugador jugador) {
        if (jugador.getCajaObjetos().getCaja().isEmpty()) {
            JOptionPane.showMessageDialog(this, jugador.getNombre() + "Aquí no hay objetos...");
            return;
        }
        jugador.mostrarObjetos();
    }

    public void seleccionarInventario(Jugador jugador) {
        if (jugador.getCajaObjetos().getCaja().isEmpty()) {
            JOptionPane.showMessageDialog(this, jugador.getNombre() + "Aquí no hay objetos...");
            return;
        }
        jugador.seleccionarObjeto();
        usarCajaDelJugador(turnoJugador1 ? jugador1 : jugador2, turnoJugador1 ? jugador2 : jugador1, escopeta);
        Objeto objeto = jugador.objeto_a_Usar();
        jugador.getCajaObjetos().getCaja().remove(objeto.getId());
    }
    
    public void mostrarInformacionEscopeta(JTextArea logEventos){
        logEventos.append("La escopeta se cargó con " + escopeta.getMunicion().size() + " cartuchos\n");
        for(int i = 0; i<escopeta.getMunicion().size(); i++){
            logEventos.append("[" + escopeta.getMunicion().get(i).toUpperCase() + "] ");
        }
        logEventos.append("\n");
    }

    public void usarCajaDelJugador(Jugador jugadorActual, Jugador jugador2, Escopeta escopeta){
        
            if (jugadorActual.objeto_a_Usar() instanceof Adrenalina) {
                ((Adrenalina) jugadorActual.objeto_a_Usar()).usarAdrenalina(jugador2.getCajaObjetos());
                logEventos.append("Se ha utilizado la Adrenalina...\n");
            }
            else if (jugadorActual.objeto_a_Usar() instanceof Cerveza) {
                ((Cerveza) jugadorActual.objeto_a_Usar()).usarCerveza(escopeta, this);
                logEventos.append("Se ha utilizado la Cerveza...\n");
                if(escopeta.getMunicion().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Ronda terminada...");
                    escopeta.cargarBalas();
                    logEventos.append("Nuevas balas insertadas:\n");
                    mostrarInformacionEscopeta(logEventos);
                    repartirCajaJugador(jugador1);
                    repartirCajaJugador(jugador2);
                }
            }
            else if (jugadorActual.objeto_a_Usar() instanceof Cigarro) {
                ((Cigarro) jugadorActual.objeto_a_Usar()).utilizarCigarro(jugadorActual);
                actualizarVidas(panelVidasJ1, jugador1.getVida());
                actualizarVidas(panelVidasJ2, jugador2.getVida());
                logEventos.append("Se ha utilizado el Cigarro...\n");
            }
            else if (jugadorActual.objeto_a_Usar() instanceof Esposa) {
                ((Esposa) jugadorActual.objeto_a_Usar()).usarEsposa(jugadorActual);
                logEventos.append("Se ha utilizado las Esposas...\n");
            }
            else if (jugadorActual.objeto_a_Usar() instanceof Lupa) {
                ((Lupa) jugadorActual.objeto_a_Usar()).UsarLupa(escopeta, this);
                logEventos.append("Se ha utilizado la Lupa...\n");
            }
            else {
                ((Sierra) jugadorActual.objeto_a_Usar()).usarSierra(escopeta);
                logEventos.append("Se ha utilizado La sierra\n");
            }
    }

    public void repartirCajaJugador(Jugador jugador) {
        jugador.repartirObjetos();
    }


    public static void main(String[] args) {
        new Tableroo();
    }
}