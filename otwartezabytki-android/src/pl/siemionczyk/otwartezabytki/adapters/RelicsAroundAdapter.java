package pl.siemionczyk.otwartezabytki.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import pl.siemionczyk.otwartezabytki.R;
import pl.siemionczyk.otwartezabytki.rest.RelicJson;

import java.util.List;

/**
 * Created by michalsiemionczyk on 29/11/13.
 */
public class RelicsAroundAdapter extends ArrayAdapter<RelicJson> {

    private final Context mContext;
    private final List<RelicJson> mValues;
    private final int mResourceId;

    public RelicsAroundAdapter ( Context context, int resource, List<RelicJson> objects ) {
        super( context, resource, objects );

        this.mContext = context;
        this.mValues = objects;
        this.mResourceId = resource;
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
        relicDistance.setText( Float.toString( ob.latitude) );

        return rowView;

    }
}
