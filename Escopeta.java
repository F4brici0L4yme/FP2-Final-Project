import java.util.*;

public class Escopeta implements DanioBala {
    private ArrayList<String> balas;
    private Random random;
    private boolean balaSierra = false;

    public Escopeta() {
        balas = new ArrayList<String>();
        random = new Random();
        cargarBalas();
    }

    public void cargarBalas() {
        balas.clear();
        int cantidadBalas = (int)(Math.random()*6);
        balas.add("azul");
        balas.add("roja");
        for (int i = 0; i < cantidadBalas; i++) {
            if (random.nextBoolean()) {
                balas.add("roja");
            } 
            else {
                balas.add("azul");
            }
        }
        Collections.shuffle(balas);
    }
    public ArrayList<String> getMunicion() {
            return balas;
    }

    public void disparar(Jugador j) {
        if(balaSierra) {
        j.reducirVida(Sierra.DOBLE);
            balaSierra = false;
        }
        else
            j.reducirVida(DanioBala.DANIO_NORMAL);
    }
<<<<<<< HEAD
    // Método para retornar el arreglo de balas
    public ArrayList<String> getBalas() {
        return new ArrayList<>(balas); // Retorna una copia del arreglo de balas
    }<
=======

    public void  esBalaSierra(boolean esSierra) {
        balaSierra = esSierra;
    }

    
>>>>>>> 49c68635df8a05d21f8c6e27232bd3576bfad960
}


