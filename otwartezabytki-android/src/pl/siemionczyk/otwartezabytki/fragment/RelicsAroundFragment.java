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
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import pl.siemionczyk.otwartezabytki.OtwarteZabytkiApp;
import pl.siemionczyk.otwartezabytki.R;
import pl.siemionczyk.otwartezabytki.activities.MainActivity;
import pl.siemionczyk.otwartezabytki.activities.RelicDetailsActivity;
import pl.siemionczyk.otwartezabytki.adapters.RelicsAroundAdapter;
import pl.siemionczyk.otwartezabytki.helper.HelperToolkit;
import pl.siemionczyk.otwartezabytki.helper.MyLog;
import pl.siemionczyk.otwartezabytki.rest.OtwarteZabytkiClient;
import pl.siemionczyk.otwartezabytki.rest.RelicJsonWrapper;
import pl.siemionczyk.otwartezabytki.rest.relicjson.RelicJson;
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

    private final static float RADIUS_OF_SEARCH = 3f; //in kilometers

    ListView mListViewRelics;

    LinearLayout mProgressBar;

    RelicsAroundAdapter mAdapter;

    TextView mNrRelicsFound;

    TextView mRadiusRelics;

    private Location mLastFoundLocation;

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


        //set list on click listener
        mListViewRelics.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //get relic
                RelicsAroundAdapter adapt = ( RelicsAroundAdapter) parent.getAdapter();
                RelicJson relic = adapt.getItem(position);
                HelperToolkit.makeToast( getActivity(), relic.identification);


                (( MainActivity) getActivity()).replaceToRelicDetailsFragment( relic);


            }
        });


        //get positionInfo
        getLocationInfoAndUpdateRelics(view, RADIUS_OF_SEARCH);

        return view;
    }

    private void getLocationInfoAndUpdateRelics ( View rootView, final float radius) {
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
                downloadListRelics( location, radius);
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

    private void fillListView( ArrayList<RelicJson> relics, Location userLocation){

        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>(  );

        //stupind conversion
        for ( RelicJson r : relics){
            HashMap<String, String> map = new HashMap<String, String>(  );
            map.put( "name", r.identification );
            map.put( "place", r.place_name );
            list.add( map );
        }

        mAdapter = new RelicsAroundAdapter( getActivity(), R.layout.list_item_relic_around, relics, userLocation );

        mListViewRelics.setAdapter( mAdapter );
    }



    /**
     * @param location location
     *
     * @param radius in kilometers*/
    private void downloadListRelics( final Location location, final float radius){

        Callback<RelicJsonWrapper> cb = new Callback<RelicJsonWrapper>() {
            @Override
            public void success ( RelicJsonWrapper relicJsonWrapper, Response response ) {
                MyLog.i( TAG, "success on downloading relics:" + relicJsonWrapper.relics.size() );

                for (RelicJson r: relicJsonWrapper.relics){
                    MyLog.i(TAG, "long:" + r.longitude + ", lat:" + r.latitude);
                }

                //hide progressBar
                mProgressBar.setVisibility( View.GONE );

                //update nr_relics field
                mNrRelicsFound.setText( Integer.toString( relicJsonWrapper.relics.size() ) );

                //update radius
                mRadiusRelics.setText( Float.toString( radius) );

                //fill listview
                fillListView( relicJsonWrapper.relics , location);


            }

            @Override
            public void failure ( RetrofitError retrofitError ) {
                MyLog.i( TAG, "failture on connection:" + retrofitError );
            }
        };
        mClient.getSideEffectsAround( (float) location.getLatitude(), (float) location.getLongitude(), radius, true, cb );
    }
}
