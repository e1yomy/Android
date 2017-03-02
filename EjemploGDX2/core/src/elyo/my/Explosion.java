package mx.edu.itlp;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Centro de Computo on 23/02/2017.
 */

public class Explosion {
    public Vector2 posicion;
    public float tiempo;
    public Explosion(Vector2 posicion)
    {
        this.posicion = posicion;
        this.tiempo = 0;
    }
}
