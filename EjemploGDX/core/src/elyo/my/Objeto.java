package elyo.my;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by elyo_ on 02/02/2017.
 */

public class Objeto {
    TextureRegion textura;
    Body body;

    protected Objeto(TextureRegion textura, Body body){
        this.textura=textura;
        this.body=body;
    }





}

