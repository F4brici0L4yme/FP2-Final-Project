import java.util.*;
public class Escopeta extends Tablero {
    private ArrayList<String> balas;
    private Random random;
    // Constructor de la clase Escopeta
    public Escopeta() {
        this.balas = new ArrayList<>();
        this.random = new Random();
        cargarBalas(); // La escopeta se carga automáticamente al ser creada
    }
    private void cargarBalas() {
        balas.clear();
        int cantidadBalas = random.nextInt(5) + 2; 
        // Aseguramos al menos una bala azul
        balas.add("azul");
        for (int i = 1; i < cantidadBalas; i++) {
            if (random.nextBoolean()) {
                balas.add("roja");
            } else {
                balas.add("azul");
            }
        }
        Collections.shuffle(balas); // Mezcla las balas aleatoriamente
        System.out.println("La escopeta ha sido cargada con balas mezcladas.");
    }

    // Método para disparar
    public void disparar() {
        if (balas.isEmpty()) {
            System.out.println("No hay balas en la escopeta. Fin de la ronda.");
            return;
        }
        String balaActual = balas.remove(0); // Obtiene y elimina la primera bala
        if (balaActual.equals("roja")) {
            System.out.println("¡Disparo con bala roja! El personaje recibe 1 de daño.");
        } else {
            System.out.println("¡Disparo con bala azul! No ocurre nada.");
        }
    }
    // Método para retornar el arreglo de balas
    public ArrayList<String> getBalas() {
        return new ArrayList<>(balas); // Retorna una copia del arreglo de balas
    }<
}
