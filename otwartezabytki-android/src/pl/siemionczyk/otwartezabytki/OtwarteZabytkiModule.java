package pl.siemionczyk.otwartezabytki;

import android.content.Context;

//import javax.inject.Singleton;
//import javax

import dagger.Module;
import dagger.Provides;
import pl.siemionczyk.otwartezabytki.activities.MainActivity;
import pl.siemionczyk.otwartezabytki.event.EventBus;
import pl.siemionczyk.otwartezabytki.event.OttoEventBus;
import pl.siemionczyk.otwartezabytki.fragment.RelicsListFragment;
import pl.siemionczyk.otwartezabytki.fragment.SearchRelicFragment;
//import pl.siemionczyk.otwartezabytki.service.TestService;

import javax.inject.Singleton;
//import pl.warsjawa.android2.event.EventBus;
//import pl.warsjawa.android2.event.OttoEventBus;
//import pl.warsjawa.android2.ui.LauncherActivity;
//import pl.warsjawa.android2.ui.LoginActivity;
//import pl.warsjawa.android2.ui.MainActivity;
//import pl.warsjawa.android2.ui.list.MeetupListFragment;
//import pl.warsjawa.android2.ui.map.MeetupsMapFragment;

//import javax.inject.Singleton;


@Module(injects = {
            MainActivity.class,
            //Fragments
            RelicsListFragment.class,
            SearchRelicFragment.class
        })
        //staticInjections = {ParseInitializer.class})

public class OtwarteZabytkiModule {

    private Context context;

    public OtwarteZabytkiModule ( Context context ) {
        this.context = context;
    }

    @Provides
    @Singleton
    EventBus providesBus() {
        return new OttoEventBus();
    }
}
