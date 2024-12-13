public class Cerveza extends Objeto {
	private String bala;
	
	public void UsarCerveza(Escopeta e) {
		bala = e.getMunicion().remove(0);
		
	}
	public void mostrarBala() {
		System.out.println("La bala era: " + bala);
	}
}
