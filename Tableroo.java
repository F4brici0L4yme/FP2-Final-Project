import java.awt.*;
import java.util.concurrent.CountDownLatch;
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
    Image imagenRedimensionada = cajaIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
    ImageIcon cajaRedimensionada = new ImageIcon(imagenRedimensionada);
    public JLabel imagenJugadorActual;
    public JLabel nombreJugadorActual;
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
        JPanel panelVidas = new JPanel(new GridLayout(4, 1));
        panelVidasJ1 = new JPanel(new FlowLayout(FlowLayout.LEFT));/*LEFT hace que no se ajuste al centro y quede más chevere */
        actualizarVidas(panelVidasJ1, jugador1.getVida());
        panelVidas.add(new JLabel(jugador1.getNombre()));
        panelVidas.add(panelVidasJ1);

        panelVidasJ2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actualizarVidas(panelVidasJ2, jugador2.getVida());
        panelVidas.add(new JLabel(jugador2.getNombre()));
        panelVidas.add(panelVidasJ2);

        add(panelVidas, BorderLayout.WEST);
        /*Derecha */
        JPanel panelCajas = new JPanel(new GridLayout(4,1));
        JButton botonCaja = new JButton(cajaRedimensionada);
        JButton botonCajaEnemiga = new JButton(cajaRedimensionada);
        
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
        nombreJugadorActual = new JLabel(jugador1.getNombre());
        JPanel imagenYnombre = new JPanel(new GridLayout(2,1));
        imagenYnombre.add(nombreJugadorActual);
        imagenYnombre.add(imagenJugadorActual);
        panelInferior.add(imagenJugadorActual, BorderLayout.WEST);

        logEventos = new JTextArea(5, 30);
        logEventos.setEditable(false);
        logEventos.setLineWrap(true);
        logEventos.setWrapStyleWord(true);
        modificarBotones(botonCaja);
        modificarBotones(botonCajaEnemiga);
        modificarBotones(botonEscopeta);
        panelInferior.add(logEventos, BorderLayout.CENTER);
        repartirCajaJugador(jugador1);
        repartirCajaJugador(jugador2);
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
    
        // Obtener la bala actual
        String balaDisparada = escopeta.getMunicion().get(0);
        boolean balaEsReal = balaDisparada.equals("roja");
    
        // Determinar objetivo y mensajes
        Jugador objetivo = null;
        if (respuesta == 0) { // Disparar al enemigo
            objetivo = turnoJugador1 ? jugador2 : jugador1;
            if (balaEsReal) {
                escopeta.disparar(objetivo);
                logEventos.append("Fue una bala verdadera...\n");
            } else {
                logEventos.append("Era una bala falsa...\n");
            }
        } else if (respuesta == 1) { // Disparar a ti mismo
            objetivo = turnoJugador1 ? jugador1 : jugador2;
            if (balaEsReal) {
                escopeta.disparar(objetivo);
                logEventos.append("Fue una bala verdadera...\n");
            } else {
                logEventos.append("Era una bala falsa... ¡Ganas un turno por tu valentía!\n");
            }
        }
        escopeta.getMunicion().remove(0);
        if (respuesta != 1 || balaEsReal) {
            cambiarTurno();
        }
        actualizarVidas(panelVidasJ1, jugador1.getVida());
        actualizarVidas(panelVidasJ2, jugador2.getVida());
        verificarGanador();
        if (escopeta.getMunicion().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ronda terminada...\n");
            escopeta.cargarBalas();
            logEventos.append("Nuevas balas insertadas:\n");
            mostrarInformacionEscopeta(logEventos);
            repartirCajaJugador(jugador1);
            repartirCajaJugador(jugador2);
        }
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
            nombreJugadorActual.setText(jugador2.getNombre());
        }
        else{
            imagenJugadorActual.setIcon(imagenP1);
            nombreJugadorActual.setText(jugador2.getNombre());
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
        // Verificar si la caja está vacía
        if (jugador.getCajaObjetos().getCaja().isEmpty()) {
            JOptionPane.showMessageDialog(this, jugador.getNombre() + " Aquí no hay objetos...");
            return;
        }

        // Usar SwingWorker para manejar la selección de objetos
        SwingWorker<Objeto, Void> worker = new SwingWorker<>() {
            @Override
            protected Objeto doInBackground() {
                CountDownLatch latch = new CountDownLatch(1);
                
                // Mostrar la ventana para seleccionar objeto
                jugador.seleccionarObjeto(latch);

                try {
                    latch.await(); // Esperar sin bloquear la interfaz gráfica
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return null;
                }

                // Retornar el objeto seleccionado
                return jugador.objeto_a_Usar();
            }

            @Override
            protected void done() {
                try {
                    // Obtener el objeto seleccionado
                    Objeto objeto = get();
                    if (objeto != null) {
                        System.out.println("Objeto seleccionado: " + objeto.getClass().getSimpleName());

                        usarCajaDelJugador(
                            turnoJugador1 ? jugador1 : jugador2,
                            turnoJugador1 ? jugador2 : jugador1,
                            escopeta
                        );
                        System.out.println("Antes de remover: " + jugador.getCajaObjetos().getCaja());
                        jugador.getCajaObjetos().getCaja().remove(jugador.retornarPosicion());
                        System.out.println("Después de remover: " + jugador.getCajaObjetos().getCaja());
                        
                        logEventos.append(jugador.getNombre() + " usó " + objeto.getClass().getSimpleName() + "\n");
                    } else {
                        logEventos.append(jugador.getNombre() + " no seleccionó ningún objeto\n");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        // Ejecutar el SwingWorker
        worker.execute();
    }
    
    public void mostrarInformacionEscopeta(JTextArea logEventos){
        logEventos.append("La escopeta se cargó con " + escopeta.getMunicion().size() + " cartuchos\n");
        // for(int i = 0; i<escopeta.getMunicion().size(); i++){
        //     logEventos.append("[" + escopeta.getMunicion().get(i).toUpperCase() + "] ");
        // }
        logEventos.append("\n");
    }

    public void usarCajaDelJugador(Jugador jugadorActual, Jugador jugadorEnemigo, Escopeta escopeta){
        
            if (jugadorActual.objeto_a_Usar() instanceof Adrenalina) {
                logEventos.append("Se ha utilizado la Adrenalina...\n");
                ((Adrenalina) jugadorActual.objeto_a_Usar()).usarAdrenalina(jugadorEnemigo.getCajaObjetos());
                ((Adrenalina) jugadorActual.objeto_a_Usar()).recibirCajaDelJugador(jugadorActual.getCajaObjetos());
            }
            else if (jugadorActual.objeto_a_Usar() instanceof Cerveza) {
                logEventos.append("Se ha utilizado la Cerveza...\n");
                ((Cerveza) jugadorActual.objeto_a_Usar()).usarCerveza(escopeta, this);
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
                logEventos.append("Se ha utilizado el Cigarro...\n");
                ((Cigarro) jugadorActual.objeto_a_Usar()).utilizarCigarro(jugadorActual);
                actualizarVidas(panelVidasJ1, jugador1.getVida());
                actualizarVidas(panelVidasJ2, jugador2.getVida());
            }
            else if (jugadorActual.objeto_a_Usar() instanceof Esposa) {
                logEventos.append("Se ha utilizado las Esposas...\n");
                ((Esposa) jugadorActual.objeto_a_Usar()).usarEsposa(jugadorActual);
            }
            else if (jugadorActual.objeto_a_Usar() instanceof Lupa) {
                logEventos.append("Se ha utilizado la Lupa...\n");
                ((Lupa) jugadorActual.objeto_a_Usar()).UsarLupa(escopeta, this);
            }
            else {
                logEventos.append("Se ha utilizado La sierra\n");
                ((Sierra) jugadorActual.objeto_a_Usar()).usarSierra(escopeta);
            }
    }

    public void repartirCajaJugador(Jugador jugador) {
        jugador.repartirObjetos();
    }
    public void modificarBotones(JButton boton){
        boton.setContentAreaFilled(false); // Hace el botón transparente
        boton.setBorderPainted(false);     // Quita el borde del botón
        boton.setFocusPainted(false);      // Quita el indicador de foco
    }

    public static void main(String[] args) {
        new Tableroo();
    }
}