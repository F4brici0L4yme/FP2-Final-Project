package CajaJuego;

import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

public class Lupa {

	public void UsarObjeto(Escopeta e) {
		ArrayList<String> balas = e.RecuperarBalas();
		String BalaMostrar = balas.get(0);
			JFrame ventana = new JFrame();
			ventana.setSize(200,200);
			ventana.setLayout(new FlowLayout(FlowLayout.RIGHT));
		    ventana.setVisible(true);
		    String frase1 = "La siguiente bala es azul";
		    String frase2 = "La siguiente bala es roja";
	}
	
	
}