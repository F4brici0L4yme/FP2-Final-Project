import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

public class Adrenalina extends Objeto {
    
    private ArrayList<JButton> botones = new ArrayList<>();
    private ArrayList<Objeto>  enemigaObs;
    private ArrayList<Objeto> misObs;
    private JFrame ventana;
    private JPanel panel1;
	private JPanel panel2;
	private ImageIcon cofreArriba = new ImageIcon("./images/cofre_arriba.png");
	private ImageIcon cofreAbajo = new ImageIcon("./images/cofre_abajo.png"); 
    private Timer cierreAutomatico;
    private int posicionAdrenalina;

    public void recibirCajaDelJugador(CajaDeObjetos obs, int posicion) {
        misObs = obs.getCaja();
        posicionAdrenalina = posicion;
    }

    public void usarAdrenalina(CajaDeObjetos enemigo) {
        enemigaObs = enemigo.getCaja();
        ventana = new JFrame();
        ventana.setTitle("CAJA DE OBJETOS ENEMIGA");
        ventana.setSize(700, 600);
        crearContenedor();
        ventana.setVisible(true);

        cierreAutomatico = new Timer(90000, e -> {
            ventana.dispose();
            if (posicionAdrenalina >= 0 && posicionAdrenalina < misObs.size()) {
                misObs.remove(posicionAdrenalina);
            }
        });
        cierreAutomatico.setRepeats(false);
        cierreAutomatico.start();
    }

    public void crearContenedor() {
        ventana.setLayout(new GridLayout(2, 1));
	    botones.clear();
	    llenarBotones();
        int posicion = 0;

        panel1 = new JPanel() {
	        @Override
	        protected void paintComponent(Graphics g) {
	            super.paintComponent(g);
	            if (cofreArriba.getImage() != null) {
	                g.drawImage(cofreArriba.getImage(), 0, 0, getWidth(), getHeight(), this);
	            }
	        }
	    };

	    panel2 = new JPanel() {
	        @Override
	        protected void paintComponent(Graphics g) {
	            super.paintComponent(g);
	            if (cofreAbajo.getImage() != null) {
	                g.drawImage(cofreAbajo.getImage(), 0, 0, getWidth(), getHeight(), this);
	            }
	        }
	    };

	    panel2.setLayout(new GridLayout(2, 4, 7, 7));
        for (JButton b : botones) {
            panel2.add(b);
            b.addActionListener(new Listener(posicion));
            posicion++;
        }

        ventana.add(panel1);
	    ventana.add(panel2);
    }

    private class Listener implements ActionListener {
        private int pos;

        public Listener(int pos) {
            this.pos = pos;
        }

        public void actionPerformed(ActionEvent e) {
            cierreAutomatico.stop();
            misObs.remove(posicionAdrenalina);
            misObs.add(enemigaObs.get(pos));
            enemigaObs.remove(pos);
            ventana.dispose();
        }
    }

    public void llenarBotones() {
        for (Objeto ob : enemigaObs) {
            JButton boton = new JButton();
			if (ob instanceof Lupa) {
				boton.setIcon(redimensionarImagen("./images/Lupa.png"));
			} else if (ob instanceof Cigarro) {
				boton.setIcon(redimensionarImagen("./images/Cigarrillo.png"));
			} else if (ob instanceof Sierra) {
				boton.setIcon(redimensionarImagen("./images/Serrucho.png"));
			} else if (ob instanceof Cerveza) {
				boton.setIcon(redimensionarImagen("./images/Cristal.png"));
			} else if (ob instanceof Esposa) {
				boton.setIcon(redimensionarImagen("./images/Esposas.png"));
			} 
            boton.setBorderPainted(false);
            botones.add(boton);
            modificarBotones(boton);
        }
    }

    public void modificarBotones(JButton boton){
        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
    }

    public ImageIcon redimensionarImagen(String path){
		ImageIcon imageIcon = new ImageIcon(path);
		Image image = imageIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
		return new ImageIcon(image);
	}
}