
import java.util.*;
import javax.swing.*;

import java.awt.*;
public class CajaDeObjetos {
    public ImageIcon adrenalinaIcon = new ImageIcon("./images/adrenalina.png");
	private ArrayList<Objeto> caja = new ArrayList<Objeto>();
	private ArrayList<JButton> botones = new ArrayList<JButton>();
	private JFrame ventana;
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
	}

	public void crearContenedor() {
		botones.clear();
        llenarBotones();
        ventana.setLayout(new GridLayout(2, 4, 7, 7));
        for (JButton b : botones)
            ventana.add(b);
    }
    public void llenarBotones() {
        for (Objeto ob : caja) {
            JButton boton;
            if (ob instanceof Lupa) {
                boton = new JButton("Lupa");
            } else if (ob instanceof Cigarro) {
                boton = new JButton("Cigarro");
            } else if (ob instanceof Sierra) {
                boton = new JButton("Sierra");
            } else if (ob instanceof Cerveza) {
                boton = new JButton("Cerveza");
            } else if (!(ob instanceof Adrenalina)) {
                boton = new JButton("Esposa");
            } else 
            	boton = new JButton("Adrenalina");//lajo, allen vice estructura discretas, girl gilmore girl, sflix***
            
            botones.add(boton);
        }
    }
   	// public void llenarBotones() {
    // 	for (Objeto ob : caja) {
	// 		JButton boton = new JButton();
	// 		if (ob instanceof Lupa) {
	// 			boton.setIcon(new ImageIcon("./images/lupa.png"));
	// 		} else if (ob instanceof Cigarro) {
	// 			boton.setIcon(new ImageIcon("./images/cigarro.png"));
	// 		} else if (ob instanceof Sierra) {
	// 			boton.setIcon(new ImageIcon("./images/sierra.png"));
	// 		} else if (ob instanceof Cerveza) {
	// 			boton.setIcon(new ImageIcon("./images/cerveza.png"));
	// 		} else if (ob instanceof Esposa) {
	// 			boton.setIcon(new ImageIcon("./images/esposa.png"));
	// 		} else if (ob instanceof Adrenalina) {
	// 			boton.setIcon(new ImageIcon("./images/adrenalina.png"));
	// 		}
	// 		boton.setBorderPainted(false);  // Opcional: Remueve el borde.
	// 		botones.add(boton);
   	// 	 }
	// }
    public ArrayList<Objeto> getCaja() {
        return caja;
    }

    public ArrayList<JButton> getBotones() {
        return botones;
    }

    public JFrame getVentana() {
        return ventana;
    }
}
