package pl.siemionczyk.otwartezabytki.rest;

import com.google.gson.Gson;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by michalsiemionczyk on 12/11/13.
 */

@Singleton
public class OtwarteZabytkiClient {

    private OtwarteZabytkiApi api;

    public static final String HOST = "http://otwartezabytki.pl";

    @Inject
    public OtwarteZabytkiClient(){
        Gson gson = new Gson();
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setServer("http://otwartezabytki.pl/api/v1")
                .setConverter(new GsonConverter(gson))
                .build();
        api = restAdapter.create(OtwarteZabytkiApi.class);
    }

    public void getRelics(String place, String relicName, String from, String to, boolean hasPhotos,
                          Callback<RelicJsonWrapper> cb){
        api.getRelics(place, relicName, from, to, hasPhotos, cb);
    }

    public void getRelics(String place, String relicName, String from, String to, boolean hasPhotos,
                          int page, Callback<RelicJsonWrapper> cb){
        api.getRelics(place, relicName, from, to, hasPhotos, page, cb);
    }

    public void getRelicsAround(double latitude, double longitude, float radius,
                                boolean hasPhotos, int page,
                                Callback<RelicJsonWrapper> cb){
        api.getRelics( latitude, longitude, radius,  hasPhotos, page, cb );
    }
}
