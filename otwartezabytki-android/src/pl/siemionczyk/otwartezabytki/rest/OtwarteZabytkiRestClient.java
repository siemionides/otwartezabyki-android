package pl.siemionczyk.otwartezabytki.rest;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Stub of rest client communication API
 * @author Michal Siemionczyk michal.siemionczyk@gmail.com
 *
 */
public class OtwarteZabytkiRestClient {
	
	

	  private static final String BASE_URL = "http://otwartezabytki.pl/api/v1/";
	  
	  private static final String PARAM_PLACE_KEY = "place";
	  
	  private static final String PARAM_LATITUDE_KEY = "latitude";
	  
	  private static final String PARAM_LONGITUDE_KEY = "longitude";
	  
	  private static final String PARAM_API_KEY_KEY = "api_key";
	  
	  /** Public, as it may be useful*/
	  public static final String API_KEY = "3mJdUhjaE67U7P8xUfXP";

	  private static AsyncHttpClient client = new AsyncHttpClient();
	  
	  
	  
	  /**
	   * For getting back the relics
	   * @param latitude
	   * @param longitude
	   * @param responseHandler
	   */
	  public static void getRelicsByLocation(double latitude, double longitude, AsyncHttpResponseHandler responseHandler ){
		  //add API key
		  //http://otwartezabytki.pl/api/v1/relics.json?place=
		  //place=Bia%C5%82ystok&latitude=53.1346562&longitude=23.1685799&api_key=3mJdUhjaE67U7P8xUfX
		  //http://otwartezabytki.pl/api/v1/relics.json?latitude=53.1346562&longitude=23.1685799&api_key=3mJdUhjaE67U7P8xUfXP

		  String url = getAbsoluteUrl("relics.json");
		  
		  RequestParams params = new RequestParams();
		  
		  params.put(PARAM_LATITUDE_KEY, Double.toString(latitude));
		  params.put(PARAM_LONGITUDE_KEY, Double.toString(longitude));
		  params.put(PARAM_API_KEY_KEY, API_KEY);
		  
		  client.get(url, params, responseHandler);
//		  client.
		  
		  
	  }

	  private static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
	      client.get(getAbsoluteUrl(url), params, responseHandler);
	  }

	  private static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
	      client.post(getAbsoluteUrl(url), params, responseHandler);
	  }

	  private static String getAbsoluteUrl(String relativeUrl) {
	      return BASE_URL + relativeUrl;
	  }
}
