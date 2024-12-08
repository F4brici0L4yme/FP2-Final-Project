package CajaDeObjetos;

import java.util.*;
import javax.swing.Timer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Adrenalina extends CajaDeObjetos {

    private ArrayList<JButton> botones = new ArrayList<>();
    private ArrayList<CajaDeObjetos> obs;
    private ArrayList<CajaDeObjetos> misObs;
    private JFrame ventana; 
    private Timer cierreAutomatico;

    public void recibirCajaDelJugador(ArrayList<CajaDeObjetos> obs) {
        misObs = obs;
    }

    public void UsarObjeto(ArrayList<CajaDeObjetos> enemigo) {
        obs = enemigo;
        ventana = new JFrame();
        ventana.setSize(700, 400);
        crearContenedor(ventana);
        ventana.setVisible(true);

        cierreAutomatico = new Timer(20000, e -> ventana.dispose());
        cierreAutomatico.setRepeats(false); 
        cierreAutomatico.start(); 
    }

    public void crearContenedor(JFrame ventana) {
        llenarBotones();
        int cantObjetos = botones.size();
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
            misObs.add(obs.get(pos));
            obs.remove(pos);
            ventana.dispose();
        }
    }

    public void llenarBotones() {
        for (CajaDeObjetos ob : obs) {
            JButton boton = null;

            if (ob instanceof Lupa) {
                boton = new JButton("Lupa");
            } else if (ob instanceof Cigarro) {
                boton = new JButton("Cigarro");
            } else if (ob instanceof Sierra) {
                boton = new JButton("Sierra");
            } else if (ob instanceof Cerveza) {
                boton = new JButton("Cerveza");
            } else if (!(ob instanceof Adrenalina)) {
                boton = new JButton("Esposa");
            }

            if (boton != null)
                botones.add(boton);
        }
    }

    public static void main(String[] args) {
        ArrayList<CajaDeObjetos> objetos = new ArrayList<>();
        objetos.add(new Lupa());
        objetos.add(new Lupa());
        objetos.add(new Cigarro());
        objetos.add(new Sierra());
        objetos.add(new Esposa());
        objetos.add(new Cerveza());
        objetos.add(new Cigarro());

        Adrenalina adrenalina = new Adrenalina();
        adrenalina.recibirCajaDelJugador(new ArrayList<>());
        adrenalina.UsarObjeto(objetos);
    }
}
