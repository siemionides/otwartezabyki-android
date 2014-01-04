package pl.siemionczyk.otwartezabytki.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import pl.siemionczyk.otwartezabytki.BundleKeys;
import pl.siemionczyk.otwartezabytki.R;
import pl.siemionczyk.otwartezabytki.rest.RelicJsonWrapper;
import pl.siemionczyk.otwartezabytki.rest.relicjson.RelicJson;

/**
 * Created by michalsiemionczyk on 13/12/13.
 */
public class MapActivity extends FragmentActivity {


    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._at_map_activity );

        // Get a handle to the Map Fragment

        map = ((SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map)).getMap();

        map.setMyLocationEnabled( true);



        Bundle b = getIntent().getExtras();

        if ( b!= null && b.containsKey( BundleKeys.KEY_BUNDLE_RELICS_WRAPPER)){
            RelicJsonWrapper relicsWrapper = (RelicJsonWrapper) b.getSerializable( BundleKeys.KEY_BUNDLE_RELICS_WRAPPER);

            if ( relicsWrapper.relics.size() > 0){
                double latMin, latMax, longMin, longMax;

                latMin = latMax = relicsWrapper.relics.get(0).latitude;
                longMin = longMax = relicsWrapper.relics.get(0).longitude;

                for ( RelicJson r : relicsWrapper.relics ){
                    LatLng point = new LatLng( r.latitude, r.longitude);

                    //maintain latitude and longitude
                    if ( r.latitude < latMin ) latMin = r.latitude;
                    if ( r.latitude > latMax) latMax = r.latitude;
                    if (r.longitude < longMin) longMin = r.longitude;
                    if (r.longitude > longMax) longMax = r.longitude;

                }

                final LatLngBounds bounds = new LatLngBounds( new LatLng(latMin, longMin), new LatLng( latMax, longMax));
//                map.moveCamera( CameraUpdateFactory.newLatLngBounds( bounds, 30 ));

                map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {

                    @Override
                    public void onCameraChange(CameraPosition arg0) {
                        // Move camera.
                        map.moveCamera(CameraUpdateFactory.newLatLngBounds( bounds, 30));
                        // Remove listener to prevent position reset on camera move.
                        map.setOnCameraChangeListener(null);
                    }
                });


                for ( RelicJson r : relicsWrapper.relics ){

                    LatLng point = new LatLng( r.latitude, r.longitude);

                    map.addMarker( new MarkerOptions()
                            .title( r.identification)
                            .snippet( r.description)
                            .position( point));

                }


            }

        } else {
            provideFewFakedPoints( map);
        }
    }

    private void provideFewFakedPoints( GoogleMap map){
        LatLng point1 = new LatLng(52.2467069, 21.004586);
        LatLng point2 = new LatLng(52.2225188, 21.0285955);
        LatLng point3 = new LatLng(52.2317554, 21.0064816);

        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(point1, 13));

        map.addMarker(new MarkerOptions()
                .title("point1")
                .snippet("The most populous city in Australia.")
                .position(point1));

        map.addMarker(new MarkerOptions()
                .title("point2")
                .snippet("The 2 most populous city in Australia.")
                .position(point2));

        map.addMarker(new MarkerOptions()
                .title("point3")
                .snippet("The 3most populous city in Australia.")
                .position(point3));
    }
}
