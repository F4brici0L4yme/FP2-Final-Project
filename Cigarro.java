public class Cigarro extends Objeto{
    public void utilizarCigarro(Jugador j) {
		if(j.getVida() < 4)
			j.aumentarVida();
	}
}