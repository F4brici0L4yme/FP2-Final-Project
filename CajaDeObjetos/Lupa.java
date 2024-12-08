package CajaJuego;

import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

public class Lupa extends CajaDeObjetos{

	public void UsarObjeto(Escopeta e) {
		ArrayList<String> balas = e.getMunicion();
		JFrame ventana = new JFrame();
		ventana.setSize(400,100);
		ventana.setLayout(new FlowLayout(FlowLayout.CENTER));
		ventana.setVisible(true);
		crearContenedor(ventana, balas);
	}
	
	public void crearContenedor(JFrame ventana, ArrayList<String> balas) {
		JTextField resultado;
		String BalaMostrar = balas.get(0);
		String frase1 = "La siguiente bala es azul";
	    String frase2 = "La siguiente bala es roja";
	    
	    if(BalaMostrar.equals("roja")) {
	    	resultado = new JTextField(frase2);
	    	ventana.add(resultado);
	    }
	    else {
	    	resultado = new JTextField(frase1);
	    	ventana.add(resultado);
	    }
	}
	
	public static void main(String[]args) {
		Escopeta escopeta = new Escopeta();
		new Lupa().UsarObjeto(escopeta);
	}
}