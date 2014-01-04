package pl.siemionczyk.otwartezabytki.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import pl.siemionczyk.otwartezabytki.R;

/**
 * Created by majkeliusz on 7/7/13.
 * Map Fragment is meant to show map:
 * - of the relics around (based on settings)
 * - of the relics provided in intent
 *
 *
 */
public class MapFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//

        View view = inflater.inflate(R.layout._ft_screen_map,
                container, false);




        // Get a handle to the Map Fragment
        GoogleMap map = ((SupportMapFragment) getFragmentManager()
                .findFragmentById(R.id.map)).getMap();

//        LatLng point1 = new LatLng(-33.867, 151.206);
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


        return view;
    }
}
