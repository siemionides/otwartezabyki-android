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

    @Inject
    public OtwarteZabytkiClient(){
//        new
        Gson gson = new Gson();
//        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setServer("http://otwartezabytki.pl/api/v1")
                .setConverter(new GsonConverter(gson))
                .build();

        api = restAdapter.create(OtwarteZabytkiApi.class);
    }



    public void getSideEffects ( String place, String relicName, String from, String to,
                                 Callback<RelicJsonWrapper> cb){
        api.getRelics(place, relicName, from, to, true, cb);
    }


    public void getSideEffectsAround(double latitude, double longitute, float radius,
                                     boolean hasPhotos,
                                     Callback<RelicJsonWrapper> cb){

        api.getRelics( latitude, longitute, radius,  hasPhotos, cb );

    }



}
