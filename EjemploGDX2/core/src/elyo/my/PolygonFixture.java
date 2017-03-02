package mx.edu.itlp;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

/**
 * Created by Centro de Computo on 20/02/2017.
 */

public class PolygonFixture extends FixtureDef {

    public PolygonFixture(Vector2[] vertices, float density, float friction, float restitucion)
    {
        super();
        PolygonShape shape = new PolygonShape();
        shape.set(vertices);
        this.shape = shape;
        this.density = density;
        this.friction = friction;
        this.restitution = restitucion;
    }
}
