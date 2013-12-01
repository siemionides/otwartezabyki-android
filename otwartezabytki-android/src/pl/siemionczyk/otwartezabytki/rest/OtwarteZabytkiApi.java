package pl.siemionczyk.otwartezabytki.rest;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by michalsiemionczyk on 12/11/13.
 */



public interface OtwarteZabytkiApi {

    @GET("/relics.json")
    void getRelics(@Query("place") String place, @Query("query") String relicName,
                   @Query("from") String from,  @Query("to") String to, @Query("has_photos") boolean hasPhotos,
                   Callback<RelicJsonWrapper> cb);

    /**
     *
     * @param latitude eg. 52.232222 (Warsaw)
     * @param longitude eg. 21.008333 (Warsaw)
     * @param radius eg. 1.3 [km]
     * @param hasPhotos true / false
     * @param cb
     */
    @GET("/relics.json")
    void getRelics(@Query("latitude") double latitude, @Query("longitude") double longitude,
                   @Query("distance") float radius, @Query("has_photos") boolean hasPhotos,
                   Callback<RelicJsonWrapper> cb);

}