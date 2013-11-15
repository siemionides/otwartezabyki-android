package pl.siemionczyk.otwartezabytki;

import android.app.Application;

import dagger.ObjectGraph;

public class OtwarteZabytkiApp extends Application {

    //EventBus bus =  new Bus;

    private ObjectGraph graph;

    @Override
    public void onCreate() {
        super.onCreate();
        graph = ObjectGraph.create(new OtwarteZabytkiModule(this));
        graph.injectStatics();
    }

    public void inject(Object obj) {
        graph.inject(obj);
    }
}
