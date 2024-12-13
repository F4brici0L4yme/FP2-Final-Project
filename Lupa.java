import java.util.ArrayList;
import javax.swing.*;

public class Lupa extends Objeto {
	public void UsarLupa(Escopeta e, JFrame ventanaPrincipal) {
		ArrayList<String> balas = e.getMunicion();
		JOptionPane.showMessageDialog(ventanaPrincipal, "La siguiente bala es " + balas.get(0));
	}
}