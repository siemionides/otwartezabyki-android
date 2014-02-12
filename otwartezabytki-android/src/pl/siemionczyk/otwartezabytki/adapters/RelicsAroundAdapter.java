package pl.siemionczyk.otwartezabytki.adapters;

import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
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
    private ArrayList<RelicJson> mValues, mFilterBaseData;
    private final int mResourceId;
    private Location mUserLocation;
    private int nrPagesLoaded = 0;

    private Object mLock = new Object();


    public RelicsAroundAdapter ( Context context, int resource, ArrayList<RelicJson> objects,
                                 Location userLocation) {
        super( context, resource, objects );

        this.mContext = context;
        this.mValues  = objects;

        this.mFilterBaseData = new ArrayList<RelicJson>();
        this.mFilterBaseData.addAll( mValues);

        this.mResourceId = resource;
        this.mUserLocation = userLocation;
    }


    /** Updates both mFilterBase data and normal mValues*/
    public void addSingleLastRelic( RelicJson relic ){
        this.mValues.add( relic );
        this.mFilterBaseData.add( relic );
    }



    public void addRelicsToTheEnd ( ArrayList<RelicJson> relics){
        this.mValues.addAll( relics );
        this.mFilterBaseData.addAll( relics );
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

    @Override
    public int getCount() {
        return mValues.size();
    }

    public ArrayList<RelicJson> getItems(){
        return this.mValues;
    }


    @Override
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mValues = (ArrayList<RelicJson>) results.values;
                RelicsAroundAdapter.this.notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                synchronized (mLock) {

                    List<RelicJson> filteredList = new ArrayList<RelicJson>();
                    for (RelicJson  rj : mFilterBaseData) {
                        if (rj.identification.toLowerCase().contains(constraint.toString().toLowerCase()))
                            filteredList.add(rj);
                    }
                    FilterResults results = new FilterResults();
                    results.values = filteredList;
                    return results;
                }
            }
        };
    }


    public void setNrPagesLoaded( int nrPages ) {
        this.nrPagesLoaded = nrPages;
    }

    public int getNrPagesLoaded(){
        return this.nrPagesLoaded;
    }
}
