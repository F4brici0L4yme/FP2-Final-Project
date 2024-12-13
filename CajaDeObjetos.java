import java.awt.*;
import java.util.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class CajaDeObjetos {
	private ArrayList<Objeto> caja = new ArrayList<Objeto>();
	private ArrayList<JButton> botones = new ArrayList<JButton>();
	private JFrame ventana;
	private JPanel panel1;
	private JPanel panel2;
	private ImageIcon cofreArriba = new ImageIcon("./images/cofre_arriba.png");
	private ImageIcon cofreAbajo = new ImageIcon("./images/cofre_abajo.png");
	private static final int MAX_OBJETOS = 8;
	
    public void entregarCaja() {
    	Random random = new Random();
	        
	    for (int i = 0; i < 4; i++) { 
	    	int objRandom = random.nextInt(6);
	        if(caja.size() < MAX_OBJETOS) {
	        	switch (objRandom) {
		        	case 0: caja.add(new Adrenalina());
		                    break;
		            case 1: caja.add(new Cigarro());
		                    break;
		            case 2: caja.add(new Esposa());
		                    break;
		            case 3: caja.add(new Lupa());
		                    break;
		            case 4: caja.add(new Sierra());
		                    break;
		            case 5: caja.add(new Cerveza());
		                    break;
		        }
	        }
	        else { break; }
	    }
	}
	
	public void mostrarCaja() {
	    ventana = new JFrame();
		ventana.setTitle("CAJA DE OBJETOS");
	    ventana.setSize(700, 600);
	    ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    crearContenedor();
	    ventana.setVisible(true);
		ventana.setLocationRelativeTo(null);
		reproducirSonido("./sonidos/sonidoCaja.wav");
	}

	public void crearContenedor() {
	    ventana.setLayout(new GridLayout(2, 1));
	    botones.clear();
	    llenarBotones();

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

	    for (JButton b : botones) 
	        panel2.add(b);

	    ventana.add(panel1);
	    ventana.add(panel2);
	}

   	public void llenarBotones() {
    	for (Objeto ob : caja) {
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
			} else if (ob instanceof Adrenalina) {
				boton.setIcon(redimensionarImagen("./images/Adrenalina.png"));
			}
			boton.setBorderPainted(false);  // Opcional: Remueve el borde.
			botones.add(boton);
			modificarBotones(boton);
   		 }
	}
    public ArrayList<Objeto> getCaja() {
        return caja;
    }

    public ArrayList<JButton> getBotones() {
        return botones;
    }

    public JFrame getVentana() {
        return ventana;
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
}
