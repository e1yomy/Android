package elyo.my;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EjemploGDX2 extends ApplicationAdapter implements InputProcessor {
	SpriteBatch batch;
	World world;
	OrthographicCamera camara;
	Box2DDebugRenderer debugRenderer;
	TextureRegion fotopelota;
	TextureRegion fotocaja;

	float worldWidth = 50;
	float worldHeight = 50;
	float pantallaWidth = 600;
	float pantallaHeight = 600;
	float escalaX;
	float escalaY;

	Vector2 fuerza;
	Body pelota;

	private boolean left=false;
	private boolean right=false;
	private float motorSpeed = 0;
	private Carro carro;

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
		new Caja(world, fotocaja,15f,2f,2f,2f);
		new Caja(world, fotocaja,15f,4f,2f,2f);
		new Caja(world, fotocaja,15f,6f,2f,2f);
		new Caja(world, fotocaja,15f,8f,2f,2f);
		crearPiso(-10f,0f, worldWidth,0f);
		crearPiso(-10f,worldHeight,worldWidth,worldHeight);
		crearPiso(-10f,0f,-1f, worldHeight);
		crearPiso(worldWidth,0f,worldWidth,worldHeight);
		new Molino(world, null, 4f, 0f, 2f, 5f);
		new Pendulo(world, null, 8f, 0f, 2f, 4f);
		carro = new Carro(world, null, 20f, 1f, 80f);
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		float tiempo=Gdx.graphics.getDeltaTime();

		if (left) {
			motorSpeed+=2*tiempo;
		}
		if (right) {
			motorSpeed-=2*tiempo;
		}
		motorSpeed-= motorSpeed*tiempo*0.1;
		if (motorSpeed>100) {
			motorSpeed=100;
		}
		carro.rearWheelRevoluteJoint.setMotorSpeed(motorSpeed);
		carro.frontWheelRevoluteJoint.setMotorSpeed(motorSpeed);
		carro.frontAxlePrismaticJoint.setMaxMotorForce(Math.abs(600*carro.frontAxlePrismaticJoint.getJointTranslation()));
		carro.frontAxlePrismaticJoint.setMotorSpeed((carro.frontAxlePrismaticJoint.getMotorSpeed()-2*carro.frontAxlePrismaticJoint.getJointTranslation()));
		carro.rearAxlePrismaticJoint.setMaxMotorForce(Math.abs(600*carro.rearAxlePrismaticJoint.getJointTranslation()));
		carro.rearAxlePrismaticJoint.setMotorSpeed((carro.rearAxlePrismaticJoint.getMotorSpeed()-2*carro.rearAxlePrismaticJoint.getJointTranslation()));

		camara.update();
		world.step(tiempo, 6, 2);
		debugRenderer.render(world, camara.combined);
		batch.begin();

		Array<Body> figuras = new Array<Body>();
		world.getBodies(figuras);
		for(int i=0;i<figuras.size;i++){
			Objeto o = (Objeto)figuras.get(i).getUserData();
			Body b = figuras.get(i);
			if (o!=null && o.textura!=null)
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
		switch (keycode) {
			case Input.Keys.LEFT :
				left=true;
				break;
			case Input.Keys.RIGHT :
				right=true;
				break;
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch (keycode) {
			case Input.Keys.LEFT :
				left=false;
				break;
			case Input.Keys.RIGHT :
				right=false;
				break;
		}
		return true;
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
		fuerza.x = screenX - fuerza.x;
		fuerza.y = -(screenY - fuerza.y);
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