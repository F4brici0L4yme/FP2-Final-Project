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
	ImageIcon fondo = new ImageIcon("./images/texturaCaja.jpg");
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
	    ventana.setSize(700, 400);
	    ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    crearContenedor();
	    ventana.setVisible(true);
		ventana.setLocationRelativeTo(null);
		reproducirSonido("./sonidos/sonidoCaja.wav");
	}

	public void crearContenedor() {
		botones.clear();
        llenarBotones();
        ventana.setLayout(new GridLayout(2, 4, 7, 7));
        for (JButton b : botones)
            ventana.add(b);
    }
    // public void llenarBotones() {
    //     for (Objeto ob : caja) {
    //         JButton boton;
    //         if (ob instanceof Lupa) {
    //             boton = new JButton(lupaIcon);
    //         } else if (ob instanceof Cigarro) {
    //             boton = new JButton("Cigarro");
    //         } else if (ob instanceof Sierra) {
    //             boton = new JButton("Sierra");
    //         } else if (ob instanceof Cerveza) {
    //             boton = new JButton("Cerveza");
    //         } else if (!(ob instanceof Adrenalina)) {
    //             boton = new JButton("Esposa");
    //         } else 
    //         	boton = new JButton("Adrenalina");//lajo, allen vice estructura discretas, girl gilmore girl, sflix***
            
    //         botones.add(boton);
    //     }
    // }
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
