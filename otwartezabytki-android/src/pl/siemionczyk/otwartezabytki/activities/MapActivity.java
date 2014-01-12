package pl.siemionczyk.otwartezabytki.activities;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import pl.siemionczyk.otwartezabytki.BundleKeys;
import pl.siemionczyk.otwartezabytki.R;
import pl.siemionczyk.otwartezabytki.rest.RelicJsonWrapper;
import pl.siemionczyk.otwartezabytki.rest.relicjson.RelicJson;

/**
 * Created by michalsiemionczyk on 13/12/13.
 */
public class MapActivity extends FragmentActivity {

    GoogleMap map;

    TextView tvDistanceTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._at_map_activity );


        tvDistanceTo = ( TextView ) findViewById( R.id.tv_distance_to);


        // Get a handle to the Map Fragment

        map = ((SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map)).getMap();

        map.setMyLocationEnabled( true);

        final Bundle b = getIntent().getExtras();

        if ( b!= null && ( b.containsKey( BundleKeys.KEY_BUNDLE_RELICS_WRAPPER) ||
                b.containsKey( BundleKeys.KEY_BUNDLE_SINGLE_RELIC_JSON) )  )  {

            //set title
            setTitle( R.string.title_map_of_list);

            //load menu with list action icon (come back)


            //get relic wrapper, or directly or create new one;
            RelicJsonWrapper tempWrapper = null;

            if ( b.containsKey( BundleKeys.KEY_BUNDLE_RELICS_WRAPPER)){
                tempWrapper = (RelicJsonWrapper) b.getSerializable( BundleKeys.KEY_BUNDLE_RELICS_WRAPPER);
            } else if (b.containsKey( BundleKeys.KEY_BUNDLE_SINGLE_RELIC_JSON)){
                ArrayList<RelicJson> list = new ArrayList<RelicJson>();
                list.add( (RelicJson) b.getSerializable( BundleKeys.KEY_BUNDLE_SINGLE_RELIC_JSON));
                tempWrapper = new RelicJsonWrapper( list );
            }


            final RelicJsonWrapper relicsWrapper = tempWrapper ;

            if ( relicsWrapper.relics.size() > 0){



                //set camera place
                map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {

                    @Override
                    public void onCameraChange(CameraPosition arg0) {


                        if ( b.containsKey( BundleKeys.KEY_BUNDLE_RELICS_WRAPPER)){
                            //in case of many relics,, calculate the bounds of all of them
                            double latMin, latMax, longMin, longMax;

                            latMin = latMax = relicsWrapper.relics.get(0).latitude;
                            longMin = longMax = relicsWrapper.relics.get(0).longitude;

                            for ( RelicJson r : relicsWrapper.relics ){
                                //maintain latitude and longitude
                                if ( r.latitude < latMin ) latMin = r.latitude;
                                if ( r.latitude > latMax) latMax = r.latitude;
                                if (r.longitude < longMin) longMin = r.longitude;
                                if (r.longitude > longMax) longMax = r.longitude;
                            }

                            final LatLngBounds bounds = new LatLngBounds( new LatLng(latMin, longMin), new LatLng( latMax, longMax));

                            // Move camera.
                            map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 30));
                        } else if ( b.containsKey( BundleKeys.KEY_BUNDLE_SINGLE_RELIC_JSON)){

                            //in case of single relic, just center camera on it
                            RelicJson relic = relicsWrapper.relics.get(0);
                            LatLng relicPost = new LatLng( relic.latitude, relic.longitude );

                            map.moveCamera( CameraUpdateFactory.newLatLngZoom( relicPost, 14 ));
                        }

                        // Remove listener to prevent position reset on camera move.
                        map.setOnCameraChangeListener(null);
                    }
                });




                //add markers
                for ( RelicJson r : relicsWrapper.relics ){

                    LatLng point = new LatLng( r.latitude, r.longitude);

                    map.addMarker(new MarkerOptions()
                            .title(r.identification)
                            .snippet(r.description)
                            .position(point)

                    );
                }

                //add clicklisener - sets the distance
                map.setOnMarkerClickListener( new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {

                        //set dinstace
                        float[] result = new float[1];  // in meters
                        Location.distanceBetween(
                                map.getMyLocation().getLatitude(), map.getMyLocation().getLongitude(),
                                marker.getPosition().latitude, marker.getPosition().longitude, result);
                        String textDistance = String.format("%.2f", result[0] / 1000);
                        textDistance += " km";
                        tvDistanceTo.setText( textDistance);
                        return false;
                    }
                });

                //set on info cloud click - start new activity
                map.setOnInfoWindowClickListener( new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {


                        int relicIndex = -1;
                        //get RelicJson id in relics, compare markers using title
                        for ( int i = 0; i < relicsWrapper.relics.size(); i++){
                            if ( relicsWrapper.relics.get(i).identification.equals( marker.getTitle())){
                                relicIndex = i;
                                break;
                            }
                        }
                        //just check
                        if (relicIndex == -1) throw new UnsupportedOperationException( "did not find proper marker!");

                        showNewRelicDetailsActivity( relicsWrapper, relicIndex);
                    }
                });


                //on map click just remove "-"
                map.setOnMapClickListener( new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        tvDistanceTo.setText( " - ");
                    }
                });


            }

        } else {
            provideFewFakedPoints( map);
        }
    }

    private void showNewRelicDetailsActivity( RelicJsonWrapper relicWrapper, int relicPosition){


        Intent i = new Intent( this, RelicDetailsActivity.class);
        i.putExtra( BundleKeys.KEY_BUNDLE_RELICS_WRAPPER, relicWrapper);
        i.putExtra( BundleKeys.KEY_BUNDLE_RELIC_POSITION, relicPosition);

        startActivity( i);

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
