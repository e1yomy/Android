package elyo.my;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.MathUtils;
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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.Array;

import java.awt.Button;

public class Ejemplo extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	final float masa=1;
	final float fuerza=100;
	final float angulo=45;
	Body bodyPelota, bodyCaja;
	World world;
	Box2DDebugRenderer debugRenderer;
	OrthographicCamera camara;
	TextureRegion pelota;
	TextureRegion caja;
	Array<Objeto> figuras;
	Objeto piedra;
	float xx=0;
	float yy=0;
	float x1,y1,x2,y2;
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

		world = new World(new Vector2(0, -50f), true);
		debugRenderer = new Box2DDebugRenderer();
		camara=new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		camara.position.x=Gdx.graphics.getWidth()/2;
		camara.position.y=Gdx.graphics.getHeight()/2;
		pelota=new TextureRegion(new Texture(Gdx.files.internal("pelota.png")));
		caja=new TextureRegion(new Texture(Gdx.files.internal("caja.png")));
		figuras= new Array<Objeto>();
		CrearPiso();
		Input();

	}

	@Override
	public void render () {

		if(Gdx.input.justTouched())
		{

			xx=Gdx.input.getX();
			yy=Gdx.graphics.getHeight()-Gdx.input.getY();
			if(MathUtils.randomBoolean())
				CrearPelota();
			else
				CrearCaja();
		}


		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		float tiempo=Gdx.graphics.getDeltaTime();
		//piedra=Fisica.mover(piedra, tiempo);
		camara.update();

		world.step(tiempo, 6, 2);
		debugRenderer.render(world, camara.combined);
		batch.begin();
		//batch.draw(img, piedra.posicion.x, piedra.posicion.y);
		for(int i=0;i<figuras.size;i++)
		{
			//batch.draw(figuras.get(i).textura,figuras.get(i).body.getPosition().x-20, bodyPelota.getPosition().y-20,20,20,40,40,1,1,(float)Math.toDegrees(figuras.get(i).body.getAngle()));
			batch.draw(figuras.get(i).textura,figuras.get(i).body.getPosition().x-20, figuras.get(i).body.getPosition().y-20,20,20,40,40,1,1,(float)Math.toDegrees(figuras.get(i).body.getAngle()));
		}

		//batch.draw(pelota, bodyPelota.getPosition().x-20, bodyPelota.getPosition().y-20,20,20,40,40,1,1,(float)Math.toDegrees(bodyPelota.getAngle()));
		//batch.draw(caja, bodyCaja.getPosition().x-30, bodyCaja.getPosition().y-30,30,30,60,60,1,1,(float)Math.toDegrees(bodyCaja.getAngle()));
		batch.end();
	}
	public void CrearPelota(){
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		//bodyDef.position.set(280, 30);
		bodyDef.position.set(xx, yy);
		bodyPelota = world.createBody(bodyDef);
		CircleShape circle = new CircleShape();

		circle.setRadius(20f);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.density = 0.5f;
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 0.6f; // Make it bounce a little bit
		Fixture fixture = bodyPelota.createFixture(fixtureDef);
		circle.dispose();
		figuras.add(new Objeto(pelota,bodyPelota));
	}
	public void CrearPiso() {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;
		bodyDef.position.set(0, 10);
		Body body = world.createBody(bodyDef);
		EdgeShape edge = new EdgeShape();
		edge.set(0,0,Gdx.graphics.getWidth(),0);
		//edge.set(0,0,Gdx.graphics.getWidth(),200);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = edge;
		fixtureDef.friction = 0.3f;
		Fixture fixture = body.createFixture(fixtureDef);
		edge.dispose();
	}
	public void CrearCaja(){
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(xx, yy);
		bodyCaja = world.createBody(bodyDef);
		PolygonShape poli = new PolygonShape();
		poli.setAsBox(20f,20f);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = poli;
		fixtureDef.density = 15f;
		fixtureDef.friction = 0.5f;
		fixtureDef.restitution = 0.5f; // Make it bounce a little bit
		Fixture fixture = bodyCaja.createFixture(fixtureDef);
		poli.dispose();
		figuras.add(new Objeto(caja,bodyCaja));
	}
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
	public void Input(){
		Gdx.input.setInputProcessor(new InputProcessor() {
			@Override
			public boolean keyDown(int keycode) {
				return false;
			}

			@Override
			public boolean keyUp(int keycode) {
				return false;
			}

			@Override
			public boolean keyTyped(char character) {
				return false;
			}

			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				x1=screenX;
				y1=Gdx.graphics.getHeight()-screenY;
				return false;
			}

			@Override
			public boolean touchUp(int screenX, int screenY, int pointer, int button) {
				x2=screenX;
				y2=Gdx.graphics.getHeight()-screenY;
				float dx=0,dy=0;
				if(x1!=x2&&y1!=y2)
				{
					if(x1>x2)
					{
						//Izquierda
						dx=x1-x2;
						dx=-dx;
					}
					if (x1<x2)
					{
						//Derecha
						dx=x2-x1;
					}
					if(y1>y2)
					{
						//Abajo
						dy=y2-y1;
					}
					if (y1<y2)
					{
						//Arriba
						dy=y1-y2;
						dy=-dy;
					}
					dx*=100000;
					dy*=100000;

					for(int i=0;i<figuras.size;i++)
					{
						figuras.get(i).body.applyForceToCenter(new Vector2(dx,dy),true);
					}
					dx=dy=0;
				}
				else
				{
					if(MathUtils.randomBoolean())
						CrearPelota();
					else
						CrearCaja();
				}
				x1=x2=y1=y2=-10;

				return false;
			}

			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer) {


				return false;
			}

			@Override
			public boolean mouseMoved(int screenX, int screenY) {
				xx = screenX;
				yy = Gdx.graphics.getHeight()-screenY;
				return false;
			}

			@Override
			public boolean scrolled(int amount) {
				return false;
			}
		});

	}
}
