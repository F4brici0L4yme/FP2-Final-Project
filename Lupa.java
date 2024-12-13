import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class Lupa extends Objeto {

	public void UsarLupa(Escopeta e, JFrame ventanaPrincipal) {
		ArrayList<String> balas = e.getMunicion();
		JFrame ventana = new JFrame();
		ventana.setSize(400,100);
		ventana.setLayout(new FlowLayout(FlowLayout.CENTER));
		crearContenedor(ventana, balas);

		ventana.setLocationRelativeTo(ventanaPrincipal);
		ventana.setVisible(true);
	}
	
	public void crearContenedor(JFrame ventana, ArrayList<String> balas) {
		JTextField resultado;
		String BalaMostrar = balas.get(0);
		String frase1 = "La siguiente bala es azul";
	    String frase2 = "La siguiente bala es roja";
	    
	    if(BalaMostrar.equals("roja")) {
	    	resultado = new JTextField(frase2);
			resultado.setEditable(false);
	    	ventana.add(resultado);
	    }
	    else {
	    	resultado = new JTextField(frase1);
			resultado.setEditable(false);
	    	ventana.add(resultado);
	    }
	}
}