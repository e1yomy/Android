package elyo.my.practica1;

/**
 * Created by elyo_ on 02/02/2017.
 */

public class Objeto {
    protected double masa;
    public Vector2 velocidad;
    public Vector2 aceleracion;
    public Vector2 posicion;


    public Objeto(double masa, Vector2 posicion, Vector2 aceleracion, Vector2 velocidad) throws Exception {
        try {
            setMasa(masa);
        } catch (Exception e) {
            throw e;
        }
        this.posicion=posicion;
        this.velocidad=velocidad;
        this.aceleracion=aceleracion;
    }
    public void setMasa(double masa) throws Exception{
        if(masa<=0)
            throw new Exception("La masa tiene que ser mayor que cero.");
        this.masa=masa;
    }
    public double getMasa(){
        return masa;
    }


}

