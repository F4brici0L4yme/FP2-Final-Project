import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Jugador {
    private String nombre;
    private int vida=4;
    private boolean estado = true;
    private CajaDeObjetos obs;
    private boolean esposado = false;
    private Escopeta e;
    private ArrayList<JButton> botones;
    private Objeto objeto;
    

    public Jugador(){
        this.nombre="Anonimo";
        estado = true;
        obs = new CajaDeObjetos();
    }
    public Jugador(String nombre) {
        this.nombre=nombre;
        estado = true;
        obs = new CajaDeObjetos();
    }
    public void aumentarVida () {
        vida+=1; 
    }
    public void reducirVida(int cantidad) {
        vida-=cantidad;
        if (vida<=0){
            vida=0;
            estado=false;
            System.out.println(nombre+" ha sido eliminado.");
        }else{
            System.out.println(nombre+" ahora tiene "+vida+" de vida.");
        }
    }
    
    public void mostrarObjetos(){
        obs.mostrarCaja();
    }
    
    public void repartirObjetos(){
        obs.entregarCaja();
    }

    public void seleccionarObjeto() {
        int contador = 0;
        obs.mostrarCaja();
        botones = obs.getBotones();
        for (JButton b : botones) {
            b.addActionListener(new Listener(contador));
            contador++;
        }
    }

    private class Listener implements ActionListener {
        private int pos;

        public Listener(int pos) {
            this.pos = pos;
        }

        public void actionPerformed(ActionEvent e) {
            objeto = obs.getCaja().get(pos);
            obs.getVentana().dispose();
        }
    }

    public void setEstaEsposado( boolean estaEsposado) {
        esposado = estaEsposado;
    }
	
	public Objeto objeto_a_Usar() {
		return objeto;
	}

    public boolean estaEsposado(){
        return esposado;
    }

    public int getVida(){
        return vida;
    }

    public String getNombre() {
        return nombre;
    }

    public CajaDeObjetos getCajaObjetos() {
        return obs;
    }
}