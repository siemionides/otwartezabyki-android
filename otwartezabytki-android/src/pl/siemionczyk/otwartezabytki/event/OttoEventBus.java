package pl.siemionczyk.otwartezabytki.event;

import android.os.Handler;
import android.os.Looper;
import com.squareup.otto.Bus;

import javax.inject.Singleton;


/**
 * Created by michalsiemionczyk on 13/11/13.
 */
@Singleton
public class OttoEventBus implements EventBus {

    private final Bus bus = new Bus();
    private final Handler mHandler = new Handler( Looper.getMainLooper());

//    @Inject
//    public OttoEventBus(){
//
//    }



    @Override
    public void register(Object subscriber) {
        bus.register(subscriber);
    }

    @Override
    public void unregister(Object subscriber) {
        bus.unregister(subscriber);
    }

    @Override
    public void post(final Object event) {

        if (Looper.myLooper() == Looper.getMainLooper()) {
            bus.post(event);
        } else {
            mHandler.post(new Runnable() {
                @Override public void run() {
                    bus.post(event);
                }
            });
        }

//        bus.post(event);
    }
}
