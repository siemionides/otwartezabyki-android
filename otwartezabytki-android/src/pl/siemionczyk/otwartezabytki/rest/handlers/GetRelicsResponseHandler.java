package pl.siemionczyk.otwartezabytki.rest.handlers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;

/** Handler used when response for GET relics is returned*/
public class GetRelicsResponseHandler extends JsonHttpResponseHandler {
	private static final String TAG = "GetRelicsResponseHandler";
	
	private JSONObject response;
	
	private ResponseListener listener;
	
	public GetRelicsResponseHandler(ResponseListener listener){
		this.listener = listener;
	}
	
	/** ReponseListener */
	public interface ResponseListener{
		public void onResponse(JSONObject response);
	}
	
	@Override
	public void onSuccess(JSONArray response){
		Log.d(TAG, "resulted array size:" + response.length());
		if(response.length() > 1){
			for(int i = 0; i < response.length(); i++){
				try {
					Log.d(TAG, "obj" + i + " > " + response.get(i).toString());
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public void onSuccess(JSONObject response){
		Log.d(TAG, "success obj " + response);
		
		this.listener.onResponse(response);
	}
	
	@Override
	public void  onFailure(Throwable e, JSONArray errorResponse){
		Log.d(TAG, "Failure array ");
		Log.d(TAG, e.toString());
		Log.d(TAG, errorResponse.toString());
	}
	
	@Override
	public void  onFailure(Throwable e, JSONObject errorResponse){
		Log.d(TAG, "Failure");
		Log.d(TAG, e.toString());
		Log.d(TAG, errorResponse.toString());
	}
	
}
