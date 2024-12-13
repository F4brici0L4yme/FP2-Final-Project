import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

public class Adrenalina extends Objeto {
    
    private ArrayList<JButton> botones = new ArrayList<>();
    private ArrayList<Objeto>  enemigaObs;
    private ArrayList<Objeto> misObs;
    private JFrame ventana; 
    private Timer cierreAutomatico;
    private int posicionAdrenalina;

    public void recibirCajaDelJugador(CajaDeObjetos obs, int posicion) {
        misObs = obs.getCaja();
        posicionAdrenalina = posicion;
    }

    public void usarAdrenalina(CajaDeObjetos enemigo) {
        enemigaObs = enemigo.getCaja();
        ventana = new JFrame();
        ventana.setTitle("CAJA DE OBJETOS ENEMIGA");
        ventana.setSize(700, 400);
        crearContenedor();
        ventana.setVisible(true);

        cierreAutomatico = new Timer(90000, e -> {
            ventana.dispose();
            if (posicionAdrenalina >= 0 && posicionAdrenalina < misObs.size()) {
                misObs.remove(posicionAdrenalina);
            }
        });
        cierreAutomatico.setRepeats(false);
        cierreAutomatico.start();
    }

    public void crearContenedor() {
        botones.clear();
        llenarBotones();
        int posicion = 0;

        ventana.setLayout(new GridLayout(2, 4, 7, 7));
        for (JButton b : botones) {
            ventana.add(b);
            b.addActionListener(new Listener(posicion));
            posicion++;
        }
    }

    private class Listener implements ActionListener {
        private int pos;

        public Listener(int pos) {
            this.pos = pos;
        }

        public void actionPerformed(ActionEvent e) {
            cierreAutomatico.stop();
            misObs.remove(posicionAdrenalina);
            misObs.add(enemigaObs.get(pos));
            enemigaObs.remove(pos);
            ventana.dispose();
        }
    }

    public void llenarBotones() {
        for (Objeto ob : enemigaObs) {
            JButton boton = null;

            if (ob instanceof Lupa) {
                boton = new JButton("Lupa");
            } else if (ob instanceof Cigarro) {
                boton = new JButton("Cigarro");
            } else if (ob instanceof Sierra) {
                boton = new JButton("Sierra");
            } else if (ob instanceof Cerveza) {
                boton = new JButton("Cerveza");
            } else if (ob instanceof Esposa) {
                boton = new JButton("Esposa");
            }

            if (boton != null)
                botones.add(boton);
        }
    }
}