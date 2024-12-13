
public class Objeto {
    private static int contador = 0; 
    private int id; 

    public Objeto() {
        this.id = contador;
        contador++;
    }

    public int getId() {
        return id;
    }
}
