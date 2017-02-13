package elyo.my.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.g3d.particles.batches.BillboardParticleBatch;

import elyo.my.Ejemplo;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height=400;
		config.width=400;
		new LwjglApplication(new Ejemplo(), config);
	}
}
