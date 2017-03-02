package edu.itlp;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.WheelJoint;
import com.badlogic.gdx.physics.box2d.joints.WheelJointDef;

/**
 * Created by Centro de Computo on 17/02/2017.
 */

public class Molino extends Objeto {
    public Molino(World world, TextureRegion textura,
                  float x, float y,
                  float width, float height)
    {
        super(textura, null, width, height);
        Vector2[] vertices ={new Vector2(-width/2,-height/2),
                new Vector2(width/2,-height/2), new Vector2(0,height/2)};
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x, y+height/2f);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body torre = world.createBody(bodyDef);
        torre.createFixture(new PolygonFixture(vertices,15f,0.5f,0.1f));
        torre.setUserData(this);

        Vector2[] vertices1 = {new Vector2(0,0),new Vector2(-height/6f,height/3f), new Vector2(height/6f,height/3f)};
        Vector2[] vertices2 = {new Vector2(0,0),new Vector2(-height/6f,-height/3f), new Vector2(height/6f,-height/3f)};
        Vector2[] vertices3 = {new Vector2(0,0),new Vector2(-height/3f,height/6f), new Vector2(-height/3f,-height/6f)};
        Vector2[] vertices4 = {new Vector2(0,0),new Vector2(height/3f,height/6f), new Vector2(height/3f,-height/6f)};
        BodyDef bodyDefAspas = new BodyDef();
        bodyDefAspas.type = BodyDef.BodyType.DynamicBody;
        bodyDefAspas.position.set(x, y+height);
        Body aspa = world.createBody(bodyDefAspas);
        aspa.createFixture(new PolygonFixture(vertices1,0.5f,0.5f,0.5f));
        aspa.createFixture(new PolygonFixture(vertices2,0.5f,0.5f,0.5f));
        aspa.createFixture(new PolygonFixture(vertices3,0.5f,0.5f,0.5f));
        aspa.createFixture(new PolygonFixture(vertices4,0.5f,0.5f,0.5f));

        RevoluteJointDef revoluteJoinDef=new RevoluteJointDef();
        revoluteJoinDef.initialize(torre,aspa,aspa.getWorldCenter());
        revoluteJoinDef.enableMotor=true;
        revoluteJoinDef.maxMotorTorque=10;
        revoluteJoinDef.collideConnected = false;
        RevoluteJoint revoluteJoin=(RevoluteJoint)world.createJoint(revoluteJoinDef);

        revoluteJoin.setMotorSpeed(3);
        //revoluteJoin.setMaxMotorForce(Math.abs(600*frontAxlePrismaticJoint.getJointTranslation()));
        //revoluteJoin.setMotorSpeed((frontAxlePrismaticJoint.getMotorSpeed()-2*frontAxlePrismaticJoint.getJointTranslation()));


        /*WheelJointDef jointDef = new WheelJointDef();
        jointDef.maxMotorTorque = 1f;
        jointDef.motorSpeed = 50f;
        jointDef.dampingRatio = 0f;
        jointDef.collideConnected = false;
        jointDef.initialize(torre, aspas, new Vector2(x,y+height), new Vector2(0f,0f));

        WheelJoint joint = (WheelJoint)world.createJoint(jointDef);
        */
        //joint.setMotorSpeed(50f);

        //aspas.applyForceToCenter(new Vector2(100,0),true);
    }
}