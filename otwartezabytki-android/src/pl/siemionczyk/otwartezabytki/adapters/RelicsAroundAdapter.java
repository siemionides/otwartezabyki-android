package pl.siemionczyk.otwartezabytki.adapters;

import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import pl.siemionczyk.otwartezabytki.R;
import pl.siemionczyk.otwartezabytki.helper.MyLog;
import pl.siemionczyk.otwartezabytki.rest.relicjson.RelicJson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michalsiemionczyk on 29/11/13.
 */
public class RelicsAroundAdapter extends ArrayAdapter<RelicJson> {

    private static final String TAG = "RelicsAroundAdapter";
    private final Context mContext;
    private final ArrayList<RelicJson> mValues;
    private final int mResourceId;
    private Location mUserLocation;

    public RelicsAroundAdapter ( Context context, int resource, ArrayList<RelicJson> objects,
                                 Location userLocation) {
        super( context, resource, objects );

        this.mContext = context;
        this.mValues = objects;
        this.mResourceId = resource;
        this.mUserLocation = userLocation;
    }

    @Override
    public View getView ( int position, View convertView, ViewGroup parent ) {

        LayoutInflater inflater = ( LayoutInflater ) mContext
                .getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View rowView = inflater.inflate( this.mResourceId, parent, false );


        RelicJson ob = mValues.get( position );

        TextView relicName = (TextView) rowView.findViewById( R.id.tv_relic_name);
        TextView relicDating = (TextView) rowView.findViewById( R.id.tv_relic_dating);
        TextView relicDistance = (TextView) rowView.findViewById( R.id.tv_relic_distance);

        //set values
        relicName.setText( ob.identification );
        relicDating.setText( ob.dating_of_obj );


        //set dinstace
        float[] result = new float[1];  // in meters
        Location.distanceBetween( mUserLocation.getLatitude(), mUserLocation.getLongitude(), ob.latitude, ob.longitude, result );
        String textDistance = String.format("%.2f", result[0] / 1000);
        textDistance += " km";

        relicDistance.setText( textDistance  );

        return rowView;

    }

    public ArrayList<RelicJson> getItems(){
        return this.mValues;
    }
}
