import javax.swing.*;

public class Cerveza extends Objeto {
	private String bala;

	public void usarCerveza(Escopeta e, JFrame ventanaPrincipal) {
		bala = e.getMunicion().remove(0);
		JOptionPane.showMessageDialog(ventanaPrincipal, "La bala quitada fue " + bala);
	}
}
