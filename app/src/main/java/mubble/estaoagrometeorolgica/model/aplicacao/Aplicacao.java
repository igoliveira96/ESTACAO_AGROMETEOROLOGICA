package mubble.estaoagrometeorolgica.model.aplicacao;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Criado por Igor em 14/03/2018.
 */

public class Aplicacao extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }

}
