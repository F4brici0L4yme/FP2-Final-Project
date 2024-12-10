import java.util.*;
public class Jugador {
    private String nombre;
    private int vida=4;
    private boolean estado=true;
    private ArrayList<CajaDeObjetos> obs;
    
    public Jugador(String nombre) {
        this.nombre=nombre;
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
    
    public void restaurarVida(int cantidad) {
        if (estado){
            vida+=cantidad;
            System.out.println(nombre+" ha recuperado vida. Vida actual: "+vida);
        }else{
            System.out.println(nombre+" está fuera de combate y no puede recuperar vida.");
        }
    }
    
    public void mostrarObjetos(){
        for (CajaDeObjetos obj : obs){
            System.out.println(obj.getName());
        }
    }
    
    public void usarObjeto(obsObjeto obj){
        if (obj instance Cigarro){
            obj.usar();
            obs.remove(obj);
        }else if (obj instance Cerveza){
            obj.usar();
            obs.remove(obj);
        }else if (obj instance Adrenalina){
            obj.usar();
            obs.remove(obj);
        }else if (obj instance Lupa){
            obj.usar();
            obs.remove(obj);
        }else if (obj instance Esposas){
            obj.usar();
            obs.remove(obj);
        }else if (obj instance Sierra){
            obj.usar();
            obs.remove(obj);
        }
    }

    public void agregarObjeto(CajaDeObjetos obj) {
        if (obs.size()<4){
            obs.add(obj);
            System.out.println(obj.getName() + " agregado a la obs.");
        }else{
            System.out.println("La obs está llena. No se puede agregar "+obj.getName());
        }
    } 
}
