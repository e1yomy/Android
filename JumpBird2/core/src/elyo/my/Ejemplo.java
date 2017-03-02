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
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.Array;

import java.awt.Button;

import static com.badlogic.gdx.Gdx.input;

public class Ejemplo extends ApplicationAdapter implements InputProcessor, ContactListener {
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
	Body destruirA = null;
	Body destruirB = null;

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
		world = new World(new Vector2(0, 0), true);
		debugRenderer = new Box2DDebugRenderer();
		//pelota = (new Pelota(world, fotopelota,2,11,1)).body;
		Pelota p=new Pelota(world, fotopelota,2,11,1);
		p.tipo=Objeto.tipoPelota;
		pelota = p.body;

		//new Caja(world, fotocaja,15,2,2,2);
		//new Caja(world, fotocaja,15,4,2,2);
		//new Caja(world, fotocaja,15,6,2,2);
		//new Caja(world, fotocaja,15,8,2,2);
		CrearObstaculos();


	}
	public void CrearObstaculos()
	{
		float xx=2;
		for(byte i=0;i<30;i++)
		{
			float yy = MathUtils.random(worldHeight/3, worldHeight*3/4);
			xx+=10;
			Caja c;
			c =new Caja(world, fotocaja, xx, yy, 2, 2);
			c.tipo=Caja.tipoCaja;
				//new Caja(world, fotocaja, xx, yy, 2, yy);
				//new Caja(world, fotocaja, xx, yy+10, 2, yy);

			//new Caja(world, fotocaja,xx,worldHeight-yy+7,2,worldHeight-yy+7);
			//new Caja(world, fotocaja,xx,yy,2,15);
			//new Caja(world, fotocaja,xx,yy+worldHeight/2+7,2,10);

		}
	}

	float x1,x2=0;
	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		float tiempo=Gdx.graphics.getDeltaTime();
		camara.update();
		crearPiso(-1,0,camara.position.x+Gdx.graphics.getWidth(),0);//piso
		crearPiso(-1,20,camara.position.x+Gdx.graphics.getWidth(),20);//techo
		world.step(tiempo, 6, 2);
		//debugRenderer.render(world, camara.combined);
		camara.position.x=pelota.getPosition().x+5;
		pelota.applyForceToCenter(1,-20,true);


		x1=pelota.getPosition().x;
		checkCollision();
		if (destruirA!=null) {
			create();
			destruirA = null;
		}
		if (destruirB!=null) {
			create();

			destruirB = null;
		}

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
		x2=x1;
	}
	public void checkCollision(){
		int contacts = world.getContactCount();

		if(contacts > 0){
			for(Contact contact : world.getContactList()){
				Fixture fa = contact.getFixtureA();
				Fixture fb = contact.getFixtureB();
				boolean collideOnce  = false;
				if(fa.getBody().getType() == BodyDef.BodyType.DynamicBody){
					collideOnce = fa.getUserData()==null? true : false;
					fa.setUserData("t");
				}else if(fb.getBody().getType() == BodyDef.BodyType.DynamicBody){
					collideOnce = fb.getUserData()==null? true: false;
					fb.setUserData("t");
				}

				if((fa.getBody().getType() == BodyDef.BodyType.DynamicBody && fb.getBody().getType() == BodyDef.BodyType.StaticBody) || (fb.getBody().getType() == BodyDef.BodyType.DynamicBody && fa.getBody().getType() == BodyDef.BodyType.StaticBody)){
					if(collideOnce ){
						// play some sound or score or something in your game that would benefit to collision between static and dynamic bodies
						create();

					}

				}
			}
		}
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

		fixtureDef.friction = 30f;
		Fixture fixture = body.createFixture(fixtureDef);
		piso.dispose();
		input.setInputProcessor(this);
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
		pelota.applyForceToCenter(2,800,true);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		fuerza.x = (screenX - fuerza.x)*100;
		fuerza.y = -(screenY - fuerza.y)*100;

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

	@Override
	public void beginContact(Contact contact) {
		Objeto objetoA = (Objeto)contact.getFixtureA().getBody().getUserData();
		Objeto objetoB = (Objeto)contact.getFixtureB().getBody().getUserData();
		if ((objetoA.tipo == Objeto.tipoPelota || objetoB.tipo == Objeto.tipoPelota) &&
				(objetoA.tipo == Objeto.tipoCaja || objetoB.tipo == Objeto.tipoCaja))
		{
			destruirA = objetoA.body;
			destruirB = objetoB.body;
		}
	}

	@Override
	public void endContact(Contact contact) {

	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {

	}
}