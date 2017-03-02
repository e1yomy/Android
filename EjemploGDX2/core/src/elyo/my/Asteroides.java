package mx.edu.itlp;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.particles.influencers.RegionInfluencer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.sun.corba.se.impl.interceptors.InterceptorInvoker;
import com.sun.corba.se.impl.javax.rmi.CORBA.Util;

/**
 * Created by Centro de Computo on 22/02/2017.
 */

public class Asteroides extends ApplicationAdapter implements InputProcessor, ContactListener {
    SpriteBatch batch;
    World world;
    OrthographicCamera camara;
    Box2DDebugRenderer debugRenderer;
    TextureRegion texturaAsteroide;
    TextureRegion texturaCielo;
    TextureRegion texturaNave;
    TextureRegion texturaLaser;

    float worldWidth = 50;
    float worldHeight = 50;
    float pantallaWidth = 600;
    float pantallaHeight = 600;
    float escalaX;
    float escalaY;

    Nave nave;
    Vector2 touch = new Vector2(0,0);
    Body destruirA = null;
    Body destruirB = null;

    Animation animacionExplosion;
    Array<Explosion> explosiones;
    float tiempoTotalExplosion;

    private TextureRegion[] getSprites( String file, int cols, int rows ) {
        Texture texture = new Texture( Gdx.files.internal(file) );
        TextureRegion[][] tmp = TextureRegion.split( texture, texture.getWidth() / cols, texture.getHeight() / rows);
        TextureRegion[] Frames = new TextureRegion[cols * rows];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Frames[index++] = tmp[i][j];
            }
        }
        return Frames;
    }

    @Override
    public void create () {
        batch = new SpriteBatch();

        texturaAsteroide=new TextureRegion(new Texture(Gdx.files.internal("Asteroide.png")));
        texturaCielo=new TextureRegion(new Texture(Gdx.files.internal("nebula.png")));
        texturaNave=new TextureRegion(new Texture(Gdx.files.internal("nave.png")));
        texturaLaser=new TextureRegion(new Texture(Gdx.files.internal("laserAzul.png")));
        float tiempoFrame = 0.2f;
        animacionExplosion = new Animation(tiempoFrame, getSprites("explosion.png", 4, 4));
        tiempoTotalExplosion = 0.1f * 16;
        explosiones = new Array<Explosion>();
        pantallaWidth = Gdx.graphics.getWidth();
        pantallaHeight = Gdx.graphics.getHeight();
        escalaX = pantallaWidth / worldWidth;
        escalaY = pantallaHeight / worldHeight;
        camara=new OrthographicCamera(
                   worldWidth,worldHeight);
        camara.position.x=worldWidth/2;
        camara.position.y=worldHeight/2;
        world = new World(new Vector2(0, -9.8f), true);
        world.setContactListener(this);
        debugRenderer = new Box2DDebugRenderer();
        nave = (new Nave(world, texturaNave,
                worldWidth/2,4,2));
        nave.tipo = Objeto.tipoNave;
        Gdx.input.setInputProcessor(this);
    }

    float segundos=0;
    @Override
    public void render () {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float touchWorldX = touch.x/escalaX + camara.position.x - worldWidth/2;
        if (touch.x!=0 && touch.y!=0) {
            if (touchWorldX > nave.body.getPosition().x)
                nave.body.setLinearVelocity(20, 0);
            else
                nave.body.setLinearVelocity(-20, 0);
        }
        else
                nave.body.setLinearVelocity(0,0);
        float tiempo = Gdx.graphics.getDeltaTime();
        segundos += tiempo;
        if (segundos>0.2)
        {
            Pelota asteroide = new Pelota(world, texturaAsteroide, MathUtils.random(worldWidth), worldHeight, 1);
            asteroide.tipo = Objeto.tipoAsteroide;
            asteroide.body.applyForceToCenter(MathUtils.random(100)-50,MathUtils.random(100)-50,true);
            asteroide.body.applyForce(MathUtils.random(4)-2,MathUtils.random(4)-2,
                    MathUtils.random(asteroide.width*10)/10f,MathUtils.random(asteroide.height*10)/10f,true);
            segundos = 0;
        }
        camara.update();
        world.step(tiempo, 6, 2);
        for (int i=0; i<explosiones.size; i++)
        {
            Explosion ex = (Explosion)explosiones.get(i);
            ex.tiempo += tiempo;
            if (ex.tiempo > tiempoTotalExplosion) {
                explosiones.removeIndex(i);
                i--;
            }
        }
        if (destruirA!=null && ((Objeto)(destruirA.getUserData())).tipo == Objeto.tipoAsteroide)
        {
            explosiones.add(new Explosion(new Vector2(destruirA.getPosition().x,destruirA.getPosition().y)));
        }
        if (destruirB!=null && ((Objeto)(destruirB.getUserData())).tipo == Objeto.tipoAsteroide)
        {
            explosiones.add(new Explosion(new Vector2(destruirB.getPosition().x,destruirB.getPosition().y)));
        }
        if (destruirA!=null) {
            world.destroyBody(destruirA);
            destruirA = null;
        }
        if (destruirB!=null) {
            world.destroyBody(destruirB);
            destruirB = null;
        }
        //debugRenderer.render(world, camara.combined);
        batch.begin();
        batch.draw(texturaCielo,0,0,0,0,pantallaWidth, pantallaHeight,1,1,0);
        Array<Body> figuras = new Array<Body>();
        world.getBodies(figuras);
        for(int i=0;i<figuras.size;i++){
            Objeto o = (Objeto)figuras.get(i).getUserData();
            if (o.tipo == Objeto.tipoLaser && o.body.getPosition().y>camara.position.y+worldHeight/2)
                world.destroyBody(o.body);
            else
            {
                Body b = figuras.get(i);
                if (o != null && o.textura != null) {
                    float x = (-camara.position.x + worldWidth / 2
                            + b.getPosition().x) *
                            escalaX;
                    float y = (-camara.position.y + worldHeight / 2
                            + b.getPosition().y) *
                            escalaY;
                    batch.draw(o.textura, x, y,
                            o.width / 2, o.height / 2,
                            o.width, o.height,
                            escalaX, escalaY,
                            (float) Math.toDegrees(b.getAngle()));
                }
            }
        }
        for (int i=0; i<explosiones.size; i++) {
            TextureRegion frame = (TextureRegion) animacionExplosion.getKeyFrame(explosiones.get(i).tiempo,true);
            float x = (-camara.position.x + worldWidth / 2
                    + explosiones.get(i).posicion.x) *
                    escalaX;
            float y = (-camara.position.y + worldHeight / 2
                    + explosiones.get(i).posicion.y) *
                    escalaY;
            batch.draw(frame, x-16, y-16, 32, 32);
        }
        batch.end();
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
        touch.x = screenX;
        touch.y = screenY;
        Caja laser = new Caja(world, texturaLaser, nave.body.getPosition().x, nave.body.getPosition().y + nave.height,0.5f,3f);
        laser.tipo = Objeto.tipoLaser;
        laser.body.applyForceToCenter(0,10000,true);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touch.x = 0;
        touch.y = 0;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        touch.x = screenX;
        touch.y = screenY;
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        touch.x = screenX;
        touch.y = screenY;
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
        if ((objetoA.tipo == Objeto.tipoLaser || objetoB.tipo == Objeto.tipoLaser) &&
             (objetoA.tipo == Objeto.tipoAsteroide || objetoB.tipo == Objeto.tipoAsteroide))
        {
            destruirA = objetoA.body;
            destruirB = objetoB.body;
        }
        else {
            if ((objetoA.tipo == Objeto.tipoNave || objetoB.tipo == Objeto.tipoNave) &&
                    (objetoA.tipo == Objeto.tipoAsteroide || objetoB.tipo == Objeto.tipoAsteroide)) {
                //world.destroyBody(objetoA.body);
                //world.destroyBody(objetoB.body);
            }
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