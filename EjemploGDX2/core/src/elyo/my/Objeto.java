package mx.edu.itlp;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by Macro-D on 02/02/2017.
 */

public class Objeto {

    public TextureRegion textura;
    public Body body;
    public float width;
    public float height;
    public int tipo;

    public static final int tipoSinEspesificar = 1;
    public static final int tipoNave = 1;
    public static final int tipoAsteroide = 2;
    public static final int tipoLaser = 3;

    public Objeto(TextureRegion textura,Body body,
                  float width, float height ){
        this.textura=textura;
        this.body=body;
        this.width = width;
        this.height = height;
        this.tipo = 0;
    }

}