package ProyectoFinalLabs;

public class Jugador {
    String nombre;
    int vida=4;
    boolean estado=true;
    ArrayList<CajaObjetos> caja;
    
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
        for (CajaObjetos obj : caja){
            System.out.println(obj.getName());
        }
    }
    
    public void usarObjeto(CajaObjeto obj){
        if (obj instance Cigarro){
            obj.usar();
            caja.remove(obj);
        }else if (obj instance Cerveza){
            obj.usar();
            caja.remove(obj);
        }else if (obj instance Adrenalina){
            obj.usar();
            caja.remove(obj);
        }else if (obj instance Lupa){
            obj.usar();
            caja.remove(obj);
        }else if (obj instance Esposas){
            obj.usar();
            caja.remove(obj);
        }else if (obj instance Sierra){
            obj.usar();
            caja.remove(obj);
        }
    }

    public void agregarObjeto(CajaObjetos obj) {
        if (caja.size()<4){
            caja.add(obj);
            System.out.println(obj.getName() + " agregado a la caja.");
        }else{
            System.out.println("La caja está llena. No se puede agregar "+obj.getName());
        }
    } 
}
