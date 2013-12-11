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
 * 2-11 23:49:41.296    5385-5385/pl.siemionczyk.otwartezabytki I/RelicsAroundFragment﹕ long:20.9767578, lat:52.2445059
 12-11 23:49:41.296    5385-5385/pl.siemionczyk.otwartezabytki I/RelicsAroundFragment﹕ long:20.9952901, lat:52.2227739
 12-11 23:49:41.296    5385-5385/pl.siemionczyk.otwartezabytki I/RelicsAroundFragment﹕ long:21.004586, lat:52.2467069
 12-11 23:49:41.296    5385-5385/pl.siemionczyk.otwartezabytki I/RelicsAroundFragment﹕ long:21.0064631, lat:52.2237114
 12-11 23:49:41.296    5385-5385/pl.siemionczyk.otwartezabytki I/RelicsAroundFragment﹕ long:21.0133561, lat:52.2477023
 12-11 23:49:41.296    5385-5385/pl.siemionczyk.otwartezabytki I/RelicsAroundFragment﹕ long:20.983705, lat:52.2197176
 12-11 23:49:41.296    5385-5385/pl.siemionczyk.otwartezabytki I/RelicsAroundFragment﹕ long:21.0400586, lat:52.2358226
 12-11 23:49:41.296    5385-5385/pl.siemionczyk.otwartezabytki I/RelicsAroundFragment﹕ long:21.0039553, lat:52.2471153
 12-11 23:49:41.296    5385-5385/pl.siemionczyk.otwartezabytki I/RelicsAroundFragment﹕ long:21.0304514, lat:52.2194941
 12-11 23:49:41.296    5385-5385/pl.siemionczyk.otwartezabytki I/RelicsAroundFragment﹕ long:21.0126471, lat:52.235749
 12-11 23:49:41.296    5385-5385/pl.siemionczyk.otwartezabytki I/RelicsAroundFragment﹕ long:21.0227643, lat:52.2395953
 12-11 23:49:41.296    5385-5385/pl.siemionczyk.otwartezabytki I/RelicsAroundFragment﹕ long:21.0013723, lat:52.2456439
 12-11 23:49:41.296    5385-5385/pl.siemionczyk.otwartezabytki I/RelicsAroundFragment﹕ long:21.0064816, lat:52.2317554
 12-11 23:49:41.296    5385-5385/pl.siemionczyk.otwartezabytki I/RelicsAroundFragment﹕ long:21.0285955, lat:52.2225188
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
