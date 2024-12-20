import java.awt.*;
import java.util.concurrent.CountDownLatch;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class Tableroo extends JFrame {
    public ImageIcon shotgun = new ImageIcon("./images/shotgun.png");
    public ImageIcon corazonLleno = new ImageIcon("./images/vida.png");
    public ImageIcon corazonRoto = new ImageIcon("./images/corazonRoto.png");
    public ImageIcon fondo = new ImageIcon("./images/bg.jpg");
    public ImageIcon imagenP1 = new ImageIcon("./images/p1.png");
    public ImageIcon imagenP2 = new ImageIcon("./images/p2.png");
    public ImageIcon Mesa = new ImageIcon("./imageS/fondoParaMesa.jpg");
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
    public JScrollBar logEventos2v;
    public Escopeta escopeta;
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
        escopeta = new Escopeta();
        JPanel panelCentral = new JPanel() {
	        @Override
	        protected void paintComponent(Graphics g) {
	            super.paintComponent(g);
	            if (Mesa.getImage() != null) {
	                g.drawImage(Mesa.getImage(), 0, 0, getWidth(), getHeight(), this);
	            }
	        }
	    };

        JButton botonEscopeta = new JButton(shotgun);
        panelCentral.setLayout(new GridBagLayout()); 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; 
        gbc.gridy = 0; 
        gbc.anchor = GridBagConstraints.CENTER; 
        panelCentral.add(botonEscopeta, gbc);

        add(panelCentral, BorderLayout.CENTER);
        botonEscopeta.addActionListener(e -> mostrarOpcionesEscopeta());
        modificarBotones(botonEscopeta);
    
        /*Izquierda */
        JPanel panelVidas = new JPanel(new GridLayout(4, 1));
        panelVidasJ1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actualizarVidas(panelVidasJ1, jugador1.getVida());
        panelVidas.add(new JLabel(jugador2.getNombre()));
        panelVidas.add(panelVidasJ1);
    
        panelVidasJ2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actualizarVidas(panelVidasJ2, jugador2.getVida());
        panelVidas.add(new JLabel(jugador1.getNombre()));
        panelVidas.add(panelVidasJ2);
    
        add(panelVidas, BorderLayout.WEST);
    
        /*Derecha */
        JPanel panelCajas = new JPanel(new GridLayout(4, 1));
        JButton botonCaja = new JButton(cajaRedimensionada);
        JButton botonCajaEnemiga = new JButton(cajaRedimensionada);
    
        botonCaja.addActionListener(e -> seleccionarInventario(turnoJugador1 ? jugador1 : jugador2));
        botonCajaEnemiga.addActionListener(e -> mostrarInventario(turnoJugador1 ? jugador2 : jugador1));
        panelCajas.add(new JLabel("Caja Enemiga"));
        panelCajas.add(botonCajaEnemiga);
        panelCajas.add(new JLabel("Mi caja"));
        panelCajas.add(botonCaja);
        modificarBotones(botonCaja);
        modificarBotones(botonCajaEnemiga);
        add(panelCajas, BorderLayout.EAST);
    
        /*Abajo */
        JPanel panelInferior = new JPanel(new BorderLayout());
    
        imagenJugadorActual = new JLabel(imagenP1);
        nombreJugadorActual = new JLabel(jugador1.getNombre());
        JPanel imagenYnombre = new JPanel();
        imagenYnombre.setLayout(new BoxLayout(imagenYnombre, BoxLayout.Y_AXIS));
        imagenYnombre.add(nombreJugadorActual);
        imagenYnombre.add(imagenJugadorActual);
    
        imagenYnombre.setPreferredSize(new Dimension(80, 200));
        panelInferior.add(imagenYnombre, BorderLayout.WEST);
    
        logEventos = new JTextArea(5, 50);
        logEventos.setEditable(false);
        logEventos.setLineWrap(true);
        logEventos.setWrapStyleWord(true);

        JScrollPane scrollLogEventos = new JScrollPane(logEventos);
        scrollLogEventos.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panelInferior.add(scrollLogEventos, BorderLayout.CENTER);
        repartirCajaJugador(jugador1);
        repartirCajaJugador(jugador2);
        add(panelInferior, BorderLayout.SOUTH);
        mostrarInformacionEscopeta(logEventos);
    }
    
    public void agregarTextoLog(String texto) {
        logEventos.append(texto + "\n");
        logEventos.setCaretPosition(logEventos.getDocument().getLength());
    }
    
    public void actualizarVidas(JPanel panelVidas, int vidas) {
        panelVidas.removeAll();
        for (int i = 0; i < vidas; i++) {
            JLabel corazon = new JLabel(corazonLleno);
            panelVidas.add(corazon);
        }
        for(int i = 0; i< 4 - vidas; i++){
            JLabel corazon = new JLabel(corazonRoto);
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
        boolean balaEsReal = balaDisparada.equals("roja");
    
        Jugador objetivo = null;
        if (respuesta == 0) {
            objetivo = turnoJugador1 ? jugador2 : jugador1;
            if (balaEsReal) {
                escopeta.disparar(objetivo);
                logEventos.append("Fue una bala verdadera...\n");
            } else {
                logEventos.append("Era una bala falsa...\n");
                reproducirSonido("./sonidos/sonidoDisparoFalso.wav");
            }
        } else if (respuesta == 1) {
            objetivo = turnoJugador1 ? jugador1 : jugador2;
            if (balaEsReal) {
                escopeta.disparar(objetivo);
                logEventos.append("Fue una bala verdadera...\n");
            } else {
                logEventos.append("Era una bala falsa... ¡Ganas un turno por tu valentía!\n");
                reproducirSonido("./sonidos/sonidoDisparoFalso.wav");
            }
        }
        escopeta.getMunicion().remove(0);
        if (respuesta != 1 || balaEsReal) {
            if(!objetivo.estaEsposado()){
                cambiarTurno();
            }
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
            JOptionPane.showMessageDialog(this, "¡¡GANADOR!!");
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
        if (jugador.getCajaObjetos().getCaja().isEmpty()) {
            JOptionPane.showMessageDialog(this, jugador.getNombre() + " Aquí no hay objetos...");
            return;
        }

        SwingWorker<Objeto, Void> worker = new SwingWorker<>() {
            protected Objeto doInBackground() {
                CountDownLatch latch = new CountDownLatch(1);
                
                jugador.seleccionarObjeto(latch);

                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return null;
                }

                return jugador.objeto_a_Usar();
            }

            @Override
            protected void done() {
                try {
                    
                    Objeto objeto = get();
    
                    if (objeto != null) {
                        usarCajaDelJugador(
                            turnoJugador1 ? jugador1 : jugador2,
                            turnoJugador1 ? jugador2 : jugador1,
                            escopeta
                        );
                        if(!(objeto instanceof Adrenalina)) {
                            jugador.getCajaObjetos().getCaja().remove(jugador.retornarPosicion());
                        }
                    } else {
                        logEventos.append(jugador.getNombre() + " no seleccionó ningún objeto\n");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
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
                logEventos.append("Se ha utilizado la adrenalina...\n");
                ((Adrenalina) jugadorActual.objeto_a_Usar()).usarAdrenalina(jugadorEnemigo.getCajaObjetos());
                ((Adrenalina) jugadorActual.objeto_a_Usar()).recibirCajaDelJugador(jugadorActual.getCajaObjetos(), jugadorActual.retornarPosicion());
            }
            else if (jugadorActual.objeto_a_Usar() instanceof Cerveza) {
                logEventos.append("Se ha utilizado la cerveza...\n");
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
                logEventos.append("Se ha utilizado el cigarro...\n");
                ((Cigarro) jugadorActual.objeto_a_Usar()).utilizarCigarro(jugadorActual);
                actualizarVidas(panelVidasJ1, jugador1.getVida());
                actualizarVidas(panelVidasJ2, jugador2.getVida());
            }
            else if (jugadorActual.objeto_a_Usar() instanceof Esposa) {
                logEventos.append("Se ha utilizado la esposa...\n");
                ((Esposa) jugadorActual.objeto_a_Usar()).usarEsposa(jugadorEnemigo);
            }
            else if (jugadorActual.objeto_a_Usar() instanceof Lupa) {
                logEventos.append("Se ha utilizado la lupa...\n");
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
        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
    }
    public void reproducirSonido(String sonidoCargarBalas){
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(getClass().getResource(sonidoCargarBalas));
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new Tableroo();
    }
}