package pl.siemionczyk.otwartezabytki.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONObject;

import pl.siemionczyk.otwartezabytki.R;
import pl.siemionczyk.otwartezabytki.domain.Relic;
import pl.siemionczyk.otwartezabytki.rest.OtwarteZabytkiRestClient;
import pl.siemionczyk.otwartezabytki.rest.handlers.GetRelicsResponseHandler;

/**
 * Created by majkeliusz on 7/7/13.
 */
public class SearchRelicFragment extends Fragment {

    public static String TAG = "SearchRelicFragment";


    /** Adapter for ListView of Relics objects */
    RelicsAroundAdapter adapter = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//

        final View containerView = inflater.inflate(R.layout.fragment_screen_wyszukaj,
                container, false);


        final double bialystokLatitude = 53.1346562;
        final double bialystokLongitude = 23.1685799;

        final Button buttonTest = (Button) containerView.findViewById(R.id.button1);
        buttonTest.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(TAG, "calling RestClient start");

                // create listener
                GetRelicsResponseHandler.ResponseListener listener = new GetRelicsResponseHandler.ResponseListener() {
                    @Override
                    public void onResponse(JSONObject response) {


                        populateTextView(containerView, response.toString());
                    }
                };
                OtwarteZabytkiRestClient.getRelicsByLocation(bialystokLatitude, bialystokLongitude, new GetRelicsResponseHandler(listener));

                Log.d(TAG, "calling RestClient stop");

            }
        });




        return containerView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {


        super.onActivityCreated(savedInstanceState);

        displayListView();
    }

    private void populateTextView(View containerView ,String text){

        TextView textView = (TextView) containerView.findViewById(R.id.textView2);
        textView.setText(text);
    }

    /** Injects the listview with sample fake data */
    private void displayListView() {

        adapter = new RelicsAroundAdapter(this.getActivity(),  R.layout.list_item_relic_around);

        performRelicsRequest();

        ListView listView = (ListView) getActivity().findViewById(R.id.listRelicsAround);
        listView.setAdapter(adapter);

//        listView.setOnItemClickListener(new OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//
//                Station station = (Station) parent.getItemAtPosition(position);
//
//                Intent i = new Intent(WelcomeActivity.this,
//                        StationActivity.class);
//                i.putExtra(Station.SERIALIZABLE_NAME, (Station) station);
//                startActivity(i);
//
//                Toast.makeText(getApplicationContext(),
//                        "Clicked on Row: " + station.getLocation(),
//                        Toast.LENGTH_LONG).show();
//            }
//        });
    }

    /** Simple calss injecting fake data*/
    private void performRelicsRequest() {


//        collectionRequest = new CollectionRequest<StationCollection>(
//                StationCollection.class, Constants.STATIONS_URI);
//        collectionRequest.addLimit(Integer.toString(limit));
//        LocationRequest lr = new LocationRequest(this,this);

        adapter.clear();

        String[] names = new String[] {"Wieża Mariacka", "Kolejka Wąskotorowa", "Istotny pomnik", "Głowy lenina", "Wieża Eiffela"};
        String[] dating = new String[] {"okolice XV", "okolice XVI", "okolice XVII", "okolice XVIII", "okolice XIX"};
        for(int i = 0; i < 5; i++){
            Relic r = new Relic();
            r.setDistanceFrom(i + 0.3);
            r.setName(names[i]);
            r.setDating(dating[i]);

            adapter.add(r);
        }

        adapter.notifyDataSetChanged();
    }

    private class RelicsAroundAdapter extends ArrayAdapter<Relic> {

        public RelicsAroundAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }



        private class ViewHolder {
            TextView relicNameLabel;
            TextView relicName;
            TextView relicDateLabel;
            TextView relicDate;
            TextView relicDistanceTo;

        }

        /** */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.list_item_relic_around,
                        null);

                holder = new ViewHolder();
                holder.relicNameLabel = (TextView) convertView
                        .findViewById(R.id.tv_relic_name_label);
                holder.relicName = (TextView) convertView
                        .findViewById(R.id.tv_relic_name);
                holder.relicDateLabel = (TextView) convertView
                        .findViewById(R.id.tv_relic_date_label);
                holder.relicDate = (TextView) convertView
                        .findViewById(R.id.tv_relic_date);
                holder.relicDistanceTo = (TextView) convertView
                        .findViewById(R.id.tv_relic_distance);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Relic relic = this.getItem(position);

            holder.relicName.setText(relic.getName());
            holder.relicDate.setText(relic.getDating());
            holder.relicDistanceTo.setText(Double.toString(relic.getDistanceFrom()));
            return convertView;
        }

//        private String formatStationInfoText(Station station) {
//            String stationDistrict = station.getDistrict();
//            int bikes = station.getBicycles();
//
//            String bikesString = Integer.toString(bikes);
//            if (bikes == Station.MORE_THAN_FOUR) {
//                bikesString = "4+";
//            }
//
//            return "Station district: " + stationDistrict + "\nBikes: "
//                    + bikesString;
//        }

//        private String formatStationMessagesText(Station station) {
//
//            InformativeMessage informative = station
//                    .getLastInformativeMessage();
//            LogisticalMessage logistical = station.getLastLogisticalMessage();
//            ServiceMessage service = station.getLastServiceMessage();
//
//            String informativeText = (informative == null) ? "" : informative
//                    .getText();
//            String logisticalText = (logistical == null) ? "" : logistical
//                    .getText();
//            String serviceText = (informative == null) ? "" : service.getText();
//
//            String messages = "Inf: " + informativeText + "\nLog: "
//                    + logisticalText + "\nServ: " + serviceText;
//            return messages;
//        }

//        private String formatDistanceToText(Station station) {
//            return station.getDistanceTo().toString() + " km";
//        }
    }
}
