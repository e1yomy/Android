package elyo.my.practica1;

/**
 * Created by elyo_ on 02/02/2017.
 */

public class Fisica {
    final static double g=9.81;
    public static Objeto mover(Objeto obj, double tiempo){
        obj.posicion.x+=obj.velocidad.x*tiempo+(1/2)*obj.getMasa()*tiempo*tiempo;
        obj.posicion.y+=obj.velocidad.y*tiempo+(1/2)*obj.getMasa()*tiempo*tiempo;
        return obj;
    }
}
