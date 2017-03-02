package mx.edu.itlp;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.RopeJoint;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;

/**
 * Created by Centro de Computo on 20/02/2017.
 */

public class Pendulo extends Objeto {
    public Pendulo(World world, TextureRegion textura,
                   float x, float y,
                   float width, float height)
    {
        super(textura, null, width, height);
        Vector2[] vertices ={new Vector2(-width/2,0), new Vector2(-width/2,height),
                             new Vector2(width/2,height), new Vector2(width/2,0)};
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x, y);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body reloj = world.createBody(bodyDef);
        reloj.createFixture(new PolygonFixture(vertices,15f,0.5f,0.1f));
        reloj.setUserData(this);

        CircleShape shape = new CircleShape();
        shape.setRadius(width/8f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.05f;
        fixtureDef.friction = 0f;
        fixtureDef.restitution = 1f;
        BodyDef bodyDefPendulo = new BodyDef();
        bodyDefPendulo.type = BodyDef.BodyType.DynamicBody;
        bodyDefPendulo.position.set(x, y+height/4f);
        Body pendulo = world.createBody(bodyDefPendulo);
        pendulo.createFixture(fixtureDef);

        RopeJointDef ropeJointDef = new RopeJointDef();
        ropeJointDef.bodyA = reloj;
        ropeJointDef.bodyB = pendulo;
        ropeJointDef.maxLength = height/2;
        ropeJointDef.localAnchorA.set(0, height);
        RopeJoint ropeJoin=(RopeJoint)world.createJoint(ropeJointDef);
        pendulo.applyForceToCenter(10,0,true);
    }
}

