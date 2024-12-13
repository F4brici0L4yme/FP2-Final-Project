import java.awt.*;
import javax.swing.*;

public class Cerveza extends Objeto {
	private String bala;
	
	public void usarCerveza(Escopeta e, JFrame ventanaPrincipal) {
		bala = e.getMunicion().remove(0);
		JFrame ventana = new JFrame();
		ventana.setSize(400,100);
		ventana.setLayout(new FlowLayout(FlowLayout.CENTER));
		crearContenedor(ventana);

		ventana.setLocationRelativeTo(ventanaPrincipal);
		ventana.setVisible(true);
		
	}
	public void crearContenedor(JFrame ventana) {
		JTextField resultado;
		String frase = "La bala quitada es " + bala;
		resultado = new JTextField(frase);
		resultado.setEditable(false);
		ventana.add(resultado);
	}
}

