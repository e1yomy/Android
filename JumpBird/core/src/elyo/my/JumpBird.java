package elyo.my;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class JumpBird extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	TextureRegion o;
	World world;
	Box2DDebugRenderer debugRenderer;
	OrthographicCamera camara;
	Body bodyNube;
	float worldWidth = 20;
	float worldHeight = 20;
	float pantallaWidth = 400;
	float pantallaHeight = 400;
	float escalaX;
	float escalaY;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("nube.png");

		world = new World(new Vector2(0, -200), true);

		debugRenderer = new Box2DDebugRenderer();
		camara=new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		camara.position.x=Gdx.graphics.getWidth()/2;
		camara.position.y=Gdx.graphics.getHeight()/2;
		o=new TextureRegion(new Texture(Gdx.files.internal("nube.png")));

		CrearPiso();
		CrearNube();

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//bodyNube.applyForceToCenter(new Vector2(5000000,0),true);
		bodyNube.applyForceToCenter(new Vector2(50000,0),true);
		if(Gdx.input.isTouched())
		{
			bodyNube.applyForceToCenter(new Vector2(0,5000000),true);
		}

		float tiempo=Gdx.graphics.getDeltaTime();
		camara.update();
		world.step(tiempo, 50,20);

		debugRenderer.render(world, camara.combined);


		batch.begin();
		//batch.draw(img, 0, 0);
		//batch.draw(o,50,50);
		//batch.draw(o,bodyNube.getPosition().x-25,bodyNube.getPosition().y-15,25,15,50,30,1,1,(float)Math.toDegrees(bodyNube.getAngle()));
		float x = (-camara.position.x + worldWidth/2
				+ bodyNube.getPosition().x)*
				escalaX;
		float y = (-camara.position.y + worldHeight/2
				+ bodyNube.getPosition().y)*
				escalaY;
		batch.draw(o,bodyNube.getPosition().x-25,bodyNube.getPosition().y-15,25,15,50,30,1f,1f,(float)Math.toDegrees(bodyNube.getAngle()));
		//batch.draw(o,x,y,25,15,50,30,1,1,(float)Math.toDegrees(bodyNube.getAngle()));

		batch.end();
	}
	public void CrearNube(){
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		//bodyDef.position.set(280, 30);
		bodyDef.position.set(50, 120);
		bodyNube = world.createBody(bodyDef);
		PolygonShape circle = new PolygonShape();
		circle.setAsBox(25,15);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.density = 0.3f;
		fixtureDef.friction = 0.2f;
		fixtureDef.restitution = 0f; // Make it bounce a little bit
		Fixture fixture = bodyNube.createFixture(fixtureDef);
		circle.dispose();
	}
	public void CrearPiso() {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;
		bodyDef.position.set(0, 10);
		Body body = world.createBody(bodyDef);
		EdgeShape edge = new EdgeShape();
		edge.set(10,10,Gdx.graphics.getWidth(),10);

		//edge.set(0,0,Gdx.graphics.getWidth(),200);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = edge;
		fixtureDef.friction = 0.3f;
		Fixture fixture = body.createFixture(fixtureDef);
		edge.dispose();
	}
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

}
