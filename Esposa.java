public class Esposa extends Objeto {

    public static boolean ESPOSADO = true;
	public void usarEsposa(Jugador j) {
		j.setEstaEsposado(ESPOSADO);
	}
}
