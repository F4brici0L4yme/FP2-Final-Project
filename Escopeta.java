import java.util.*;
import javax.sound.sampled.*;
import javax.swing.JOptionPane;

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
        balas.add("azul");
        int cantidadBalas = (int)(Math.random()*6);
        balas.add("roja");
        String balasAMostrar = "[" + balas.get(0) + "] [" +balas.get(1) + "] ";
        for (int i = 0; i < cantidadBalas; i++) {
            if (random.nextBoolean()) {
                balas.add("roja");
            }
            else {
                balas.add("azul");
            }
            balasAMostrar += "[" + balas.get(i+2) + "] ";
        }
        JOptionPane.showMessageDialog(null, balasAMostrar);
        Collections.shuffle(balas);
        reproducirSonido("./sonidos/sonidaRecarga.wav");

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
        reproducirSonido("./sonidos/donidoDisparo.wav");
        
    }

    public void esBalaSierra(boolean esSierra) {
        balaSierra = esSierra;
    }
    public void reproducirSonido(String sonidoCargarBalas){
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(getClass().getResource(sonidoCargarBalas));
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}

