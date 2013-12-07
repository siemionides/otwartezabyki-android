package pl.siemionczyk.otwartezabytki.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pl.siemionczyk.otwartezabytki.R;
import pl.siemionczyk.otwartezabytki.activities.MainActivity;
import pl.siemionczyk.otwartezabytki.rest.relicjson.RelicJson;

/**
 * Created by majkeliusz on 7/7/13.
 */
public class RelicDetailsFragment extends Fragment {


    TextView tvRelicName;
    TextView tvLocation;
    TextView tvDating;
    TextView tvDescription;
    TextView tvLegends;
    TextView tvDates;
    TextView tvCategories;
    TextView tvTags;
    TextView tvRelicRegisterNr;






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//

        View view = inflater.inflate(R.layout._ft_relic_details,
                container, false);

        //inject views
        tvRelicName = ( TextView) view.findViewById( R.id.tv_relic_name);
        tvLocation = ( TextView) view.findViewById( R.id.tv_location_content);
        tvDating = ( TextView) view.findViewById( R.id.tv_date_content);
        tvDescription = ( TextView) view.findViewById( R.id.tv_desc_content);
        tvLegends = ( TextView) view.findViewById( R.id.tv_legends_content);
        tvDates = ( TextView) view.findViewById( R.id.tv_dates_content);
        tvCategories = ( TextView) view.findViewById( R.id.tv_categories_content);
        tvTags = ( TextView) view.findViewById( R.id.tv_tags_content);
        tvRelicRegisterNr = ( TextView) view.findViewById( R.id.tv_register_content);





        Bundle b = getArguments();
        if ( b != null && b.containsKey( MainActivity.FRAGMENT_BUNDLE_RELIC_JSON_KEY)){
            fillViewsWithContent( (RelicJson) b.get( MainActivity.FRAGMENT_BUNDLE_RELIC_JSON_KEY));
        }



        return view;
    }

    private void fillViewsWithContent ( RelicJson relic){
        tvRelicName.setText( relic.identification);

        tvLocation.setText( relic.place_name);

        tvDating.setText( relic.dating_of_obj);

        tvDescription.setText( relic.description);


        //insert trivia  / interesting facts / legends
        String legendsTitles = "";
        for ( RelicJson.EntryJson entry : relic.entries){
            legendsTitles += entry.title + " \n";
        }
        tvLegends.setText( legendsTitles);

        tvDates.setText("not yet");

        tvCategories.setText("not yet");

        tvTags.setText( "not yet");

        tvRelicRegisterNr.setText( relic.register_number);





    }
}
