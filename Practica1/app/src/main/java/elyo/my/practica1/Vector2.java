package elyo.my.practica1;

/**
 * Created by elyo_ on 02/02/2017.
 */

public class Vector2 {
    public double x;
    public double y;
    public Vector2(){
    }
    public Vector2(double v, double a){
        x=v*Math.sin(a);
        y=v*Math.cos(a);
    }
}
