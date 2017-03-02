package mx.edu.itlp;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJoint;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

/**
 * Created by Centro de Computo on 20/02/2017.
 */

public class Carro extends Objeto {
    public RevoluteJoint rearWheelRevoluteJoint;
    public RevoluteJoint frontWheelRevoluteJoint;
    public float motorSpeed = 0;
    public PrismaticJoint frontAxlePrismaticJoint;
    public PrismaticJoint rearAxlePrismaticJoint;

    public Carro(World world, TextureRegion textura,
                 float x, float y,
                 float escala)
    {
        super(textura, null, 120/escala, 20/escala);
        // ************************ THE CAR ************************ //
        // shape
        PolygonShape carShape = new PolygonShape();
        carShape.setAsBox(120/escala, 20/escala);
        // fixture
        FixtureDef carFixture = new FixtureDef();
        carFixture.density=5;
        carFixture.friction=3;
        carFixture.restitution=0.3f;
        carFixture.filter.groupIndex=-1;
        carFixture.shape=carShape;
        // body definition
        BodyDef carBodyDef = new BodyDef();
        carBodyDef.type = BodyDef.BodyType.DynamicBody;
        carBodyDef.position.set(x,y);
        // ************************ THE TRUNK ************************ //
        // shape
        PolygonShape trunkShape = new PolygonShape();
        trunkShape.setAsBox(40/escala,40/escala,new Vector2(-80/escala,60/escala),0);
        // fixture
        FixtureDef trunkFixture = new FixtureDef();
        trunkFixture.density=1;
        trunkFixture.friction=3;
        trunkFixture.restitution=0.3f;
        trunkFixture.filter.groupIndex=-1;
        trunkFixture.shape=trunkShape;
        // ************************ THE HOOD ************************ //
        // shape
        PolygonShape hoodShape = new PolygonShape();
        Vector2[] carVector=new Vector2[3];
        carVector[0]=new Vector2(-40/escala,20/escala);
        carVector[1]=new Vector2(-40/escala,100/escala);
        carVector[2]=new Vector2(120/escala,20/escala);
        hoodShape.set(carVector);
        // fixture
        FixtureDef hoodFixture = new FixtureDef();
        hoodFixture.density=1;
        hoodFixture.friction=3;
        hoodFixture.restitution=0.3f;
        hoodFixture.filter.groupIndex=-1;
        hoodFixture.shape=hoodShape;
        // ************************ MERGING ALL TOGETHER ************************ //
        // the car itself
        body=world.createBody(carBodyDef);
        body.createFixture(carFixture);
        body.createFixture(trunkFixture);
        body.createFixture(hoodFixture);
        // ************************ THE AXLES ************************ //
        // shape
        PolygonShape axleShape = new PolygonShape();
        axleShape.setAsBox(20/escala,20/escala);
        // fixture
        FixtureDef axleFixture = new FixtureDef();
        axleFixture.density=0.5f;
        axleFixture.friction=3;
        axleFixture.restitution=0.3f;
        axleFixture.shape=axleShape;
        axleFixture.filter.groupIndex=-1;
        // body definition
        BodyDef axleBodyDef = new BodyDef();
        axleBodyDef.type=BodyDef.BodyType.DynamicBody;
        // the rear axle itself
        axleBodyDef.position.set(x-(60/escala),y-(65/escala));
        Body rearAxle=world.createBody(axleBodyDef);
        rearAxle.createFixture(axleFixture);
        // the front axle itself
        axleBodyDef.position.set(x+(75/escala),y-(65/escala));
        Body frontAxle =world.createBody(axleBodyDef);
        frontAxle.createFixture(axleFixture);
        // ************************ THE WHEELS ************************ //
        // shape
        CircleShape wheelShape =new CircleShape();//40/worldScale);
        wheelShape.setRadius(40/escala);
        // fixture
        FixtureDef wheelFixture = new FixtureDef();
        wheelFixture.density=1;
        wheelFixture.friction=3;
        wheelFixture.restitution=0.1f;
        wheelFixture.filter.groupIndex=-1;
        wheelFixture.shape=wheelShape;
        // body definition
        BodyDef wheelBodyDef = new BodyDef();
        wheelBodyDef.type=BodyDef.BodyType.DynamicBody;
        // the real wheel itself
        wheelBodyDef.position.set(x-(60/escala),y-(65/escala));
        Body rearWheel=world.createBody(wheelBodyDef);
        rearWheel.createFixture(wheelFixture);
        // the front wheel itself
        wheelBodyDef.position.set(x+(75/escala),y-(65/escala));
        Body frontWheel=world.createBody(wheelBodyDef);
        frontWheel.createFixture(wheelFixture);

        // ************************ REVOLUTE JOINTS ************************ //
        // rear joint
        RevoluteJointDef rearWheelRevoluteJointDef=new RevoluteJointDef();
        rearWheelRevoluteJointDef.initialize(rearWheel,rearAxle,rearWheel.getWorldCenter());
        rearWheelRevoluteJointDef.enableMotor=true;
        rearWheelRevoluteJointDef.maxMotorTorque=10000;
        rearWheelRevoluteJoint=(RevoluteJoint)world.createJoint(rearWheelRevoluteJointDef);
        // front joint
        RevoluteJointDef frontWheelRevoluteJointDef=new RevoluteJointDef();
        frontWheelRevoluteJointDef.initialize(frontWheel,frontAxle,frontWheel.getWorldCenter());
        frontWheelRevoluteJointDef.enableMotor=true;
        frontWheelRevoluteJointDef.maxMotorTorque=10000;
        frontWheelRevoluteJoint=(RevoluteJoint)world.createJoint(frontWheelRevoluteJointDef);
        // ************************ PRISMATIC JOINTS ************************ //
        //  definition
        PrismaticJointDef axlePrismaticJointDef = new PrismaticJointDef();
        axlePrismaticJointDef.lowerTranslation=-20/escala;
        axlePrismaticJointDef.upperTranslation=5/escala;
        axlePrismaticJointDef.enableLimit=true;
        axlePrismaticJointDef.enableMotor=true;
        // front axle
        axlePrismaticJointDef.initialize(body,frontAxle,frontAxle.getWorldCenter(),new Vector2(0,1));
        frontAxlePrismaticJoint=(PrismaticJoint)world.createJoint(axlePrismaticJointDef);
        // rear axle
        axlePrismaticJointDef.initialize(body,rearAxle,rearAxle.getWorldCenter(),new Vector2(0,1));
        rearAxlePrismaticJoint=(PrismaticJoint)world.createJoint(axlePrismaticJointDef);
    }
}
