package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Macro-D on 16/02/2017.
 */
public class Caja extends Objeto {

    public Caja(World world, TextureRegion textura,
                float x, float y,
                float width, float height)
    {
        super(textura, null, width, height);
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x, y);
        body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2,height/2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 15f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.1f;
        Fixture fixture = body.createFixture(fixtureDef);
        shape.dispose();
        body.setUserData(this);
    }
}