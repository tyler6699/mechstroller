package careless.walker.client;

import careless.walker.Walker;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class GwtLauncher extends GwtApplication {
	@Override
	public GwtApplicationConfiguration getConfig () {
		GwtApplicationConfiguration cfg = new GwtApplicationConfiguration(1024, 768);
		return cfg;
	}

	@Override
	public ApplicationListener getApplicationListener () {
		return new Walker();
	}
}