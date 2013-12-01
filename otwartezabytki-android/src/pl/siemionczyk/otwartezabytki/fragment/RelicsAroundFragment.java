package pl.siemionczyk.otwartezabytki.fragment;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import pl.siemionczyk.otwartezabytki.OtwarteZabytkiApp;
import pl.siemionczyk.otwartezabytki.R;
import pl.siemionczyk.otwartezabytki.adapters.RelicsAroundAdapter;
import pl.siemionczyk.otwartezabytki.helper.MyLog;
import pl.siemionczyk.otwartezabytki.rest.OtwarteZabytkiClient;
import pl.siemionczyk.otwartezabytki.rest.RelicJson;
import pl.siemionczyk.otwartezabytki.rest.RelicJsonWrapper;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by majkeliusz on 7/7/13.
 */
public class RelicsAroundFragment extends Fragment {

    public final static String TAG = "RelicsAroundFragment";

    ListView mListViewRelics;

    LinearLayout mProgressBar;

    RelicsAroundAdapter mAdapter;

    TextView mNrRelicsFound;

    TextView mRadiusRelics;

    @Inject
    OtwarteZabytkiClient mClient;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//

        View view = inflater.inflate(R.layout._ft_relics_around,
                container, false);

        //inject dagger
        ((OtwarteZabytkiApp) getActivity().getApplication()).inject(this);

        //inject views
        mListViewRelics = ( ListView ) view.findViewById( R.id.list_view_relics );
        mProgressBar = ( LinearLayout ) view.findViewById( R.id.progress_bar_relics );
        mNrRelicsFound = ( TextView ) view.findViewById( R.id.tv_nr_relic_found );
        mRadiusRelics = ( TextView ) view.findViewById( R.id.tv_relic_found_radius );


        //get positionInfo
        getLocationInfoAndUpdateRelics( view );

        return view;
    }

    private void getLocationInfoAndUpdateRelics ( View rootView) {
        LocationManager locationManager;
        String svcName = Context.LOCATION_SERVICE;
        locationManager = ( LocationManager) getActivity().getSystemService(svcName);

        final TextView title = ( TextView) rootView.findViewById( R.id.textView_title);


        LocationListener ll = new LocationListener() {
            @Override
            public void onLocationChanged ( Location location ) {
                MyLog.i( TAG, "onLocationChanged: long: " + location.getLongitude() + ", lat:" + location.getLatitude()   );

                String latLongString;
                latLongString = "lat: " + location.getLatitude() + ", long: " + location.getLongitude();

                //set this temporary view value
                title.setText( latLongString ); 
                //donwload the relics
                downloadListRelics( location.getLatitude(), location.getLongitude(), 2f);
            }

            @Override
            public void onStatusChanged ( String provider, int status, Bundle extras ) {
                MyLog.i( TAG, "onStatusChanged"  );

            }

            @Override
            public void onProviderEnabled ( String provider ) {
                MyLog.i( TAG, "onProviderEnabled"  );

            }

            @Override
            public void onProviderDisabled ( String provider ) {
                MyLog.i( TAG, "onProviderDisabled"  );

            }
        };

        locationManager.requestSingleUpdate( LocationManager.GPS_PROVIDER, ll, null );

    }

    private void fillListView( ArrayList<RelicJson> relics){

        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>(  );

        //stupind conversion
        for ( RelicJson r : relics){
            HashMap<String, String> map = new HashMap<String, String>(  );
            map.put( "name", r.identification );
            map.put( "place", r.place_name );
            list.add( map );
        }

        mAdapter = new RelicsAroundAdapter( getActivity(), R.layout.list_item_relic_around, relics );

        mListViewRelics.setAdapter( mAdapter );
    }



    /**
     * @param latitude szerokość
     * @param longitude długość geograf
     * @param radius in kilometers*/
    private void downloadListRelics( double latitude, double longitude, float radius){

        Callback<RelicJsonWrapper> cb = new Callback<RelicJsonWrapper>() {
            @Override
            public void success ( RelicJsonWrapper relicJsonWrapper, Response response ) {
                MyLog.i( TAG, "success on downloading relics:" + relicJsonWrapper.relics.size() );

                //hide progressBar
                mProgressBar.setVisibility( View.GONE );

                //fill listview
                fillListView( relicJsonWrapper.relics );


            }

            @Override
            public void failure ( RetrofitError retrofitError ) {
                MyLog.i( TAG, "failture on connection:" + retrofitError );
            }
        };
        mClient.getSideEffectsAround( (float) latitude, (float) longitude, radius, true, cb );
    }
}
