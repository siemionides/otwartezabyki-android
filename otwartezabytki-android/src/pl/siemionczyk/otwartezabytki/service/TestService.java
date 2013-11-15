package pl.siemionczyk.otwartezabytki.service;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Looper;
import com.squareup.otto.Subscribe;
import pl.siemionczyk.otwartezabytki.OtwarteZabytkiApp;
import pl.siemionczyk.otwartezabytki.event.EventBus;
import pl.siemionczyk.otwartezabytki.event.events.FromServiceEvent;
import pl.siemionczyk.otwartezabytki.event.events.ToServiceEvent;
import pl.siemionczyk.otwartezabytki.helper.MyLog;

import javax.inject.Inject;
import java.util.logging.Handler;

/**
 * Created by michalsiemionczyk on 14/11/13.
 */
public class TestService extends IntentService {


    @Inject
    EventBus bus;

    private final static String TAG = "TestService";

//    Runnable run = new Runnable() {
//        @Override
//        public void run () {
//
//
//            stopSelf();
//        }
//    };


    @Override
    public void onCreate () {

        bus.register( this );

        super.onCreate();

        ((OtwarteZabytkiApp ) getApplication()).inject(this);


    }

    public TestService(){
        super("TestService");
    }

    @Override
    public void onDestroy () {

        bus.unregister( this );

        super.onDestroy();


    }

    //    @Override
//    public IBinder onBind ( Intent intent ) {
//        return null;
//    }

    @Subscribe
    public void onToServiceEvent(ToServiceEvent toServiceEvent){
        MyLog.i( TAG, "in service, on ToServiceEvent received " + toServiceEvent.value );
    }

    @Subscribe
    public void onToServiceEvent(FromServiceEvent toServiceEvent){
        MyLog.i( TAG, "in service, on ToServiceEvent received " + toServiceEvent.value );
    }





    @Override
    protected void onHandleIntent ( Intent intent ) {
        for (int i = 0; i < 10; i++){
            try {
                MyLog.i( TAG, "service call:" + i );
                Thread.sleep( 100 );

                FromServiceEvent f = new FromServiceEvent();
                f.value = i;

                bus.post( f );
            } catch ( InterruptedException e ) {
                e.printStackTrace();
            }
        }
    }

}
