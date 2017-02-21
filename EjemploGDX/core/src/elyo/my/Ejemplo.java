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

public class Ejemplo extends ApplicationAdapter implements InputProcessor {
	SpriteBatch batch;
	Texture img;

	World world;
	OrthographicCamera camara;
	Box2DDebugRenderer debugRenderer;
	TextureRegion fotopelota;
	TextureRegion fotocaja;

	Body caja;

	float worldWidth = 20;
	float worldHeight = 20;
	float pantallaWidth = 400;
	float pantallaHeight = 400;
	float escalaX;
	float escalaY;

	Vector2 fuerza;
	Body pelota;

	@Override
	public void create () {
		batch = new SpriteBatch();
		fuerza = new Vector2(0,0);
		fotopelota=new TextureRegion(new Texture(Gdx.files.internal("pelota.png")));
		fotocaja=new TextureRegion(new Texture(Gdx.files.internal("caja.png")));

		pantallaWidth = Gdx.graphics.getWidth();
		pantallaHeight = Gdx.graphics.getHeight();
		escalaX = pantallaWidth / worldWidth;
		escalaY = pantallaHeight / worldHeight;
		camara=new OrthographicCamera(
				worldWidth,worldHeight);
		camara.position.x=worldWidth/2;
		camara.position.y=worldHeight/2;
		world = new World(new Vector2(0, -9.8f), true);
		debugRenderer = new Box2DDebugRenderer();
		pelota = (new Pelota(world, fotopelota,
				0,1,1)).body;
		new Caja(world, fotocaja,15,2,2,2);
		new Caja(world, fotocaja,15,4,2,2);
		new Caja(world, fotocaja,15,6,2,2);
		new Caja(world, fotocaja,15,8,2,2);
		crearPiso(-1,0,21,0);
		crearPiso(-1,20,21,20);
		crearPiso(-1,0,-1,20);
		crearPiso(21,0,21,20);

	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		float tiempo=Gdx.graphics.getDeltaTime();
		camara.update();
		world.step(tiempo, 6, 2);
		debugRenderer.render(world, camara.combined);
		batch.begin();

		Array<Body> figuras = new Array<Body>();
		world.getBodies(figuras);
		for(int i=0;i<figuras.size;i++){
			Objeto o = (Objeto)figuras.get(i).getUserData();
			Body b = figuras.get(i);
			if (o!=null)
			{
				float x = (-camara.position.x + worldWidth/2
						+ b.getPosition().x)*
						escalaX;
				float y = (-camara.position.y + worldHeight/2
						+ b.getPosition().y)*
						escalaY;
				batch.draw(o.textura,x,y,
						o.width/2, o.height/2,
						o.width, o.height,
						escalaX,escalaY,
						(float)Math.toDegrees(b.getAngle()));
			}
		}

		//batch.draw(fotocaja, caja.getPosition().x-10, caja.getPosition().y-10,10,10,20,20,1,1,(float)Math.toDegrees(caja.getAngle()));

		batch.end();
	}

	public  void crearPiso(float x, float y, float x2, float y2){
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;
		bodyDef.position.set(0, 0);

		Body body = world.createBody(bodyDef);

		EdgeShape piso = new EdgeShape();
		piso.set(x,y,x2,y2);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = piso;

		fixtureDef.friction = 0.7f;
		Fixture fixture = body.createFixture(fixtureDef);
		piso.dispose();
		Gdx.input.setInputProcessor(this);
	}



	@Override
	public void dispose () {
		batch.dispose();
  	}

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
		fuerza.x = screenX;
		fuerza.y = screenY;
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		fuerza.x = (screenX - fuerza.x)*100;
		fuerza.y = -(screenY - fuerza.y)*100;
		pelota.applyForceToCenter(fuerza,true);
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}