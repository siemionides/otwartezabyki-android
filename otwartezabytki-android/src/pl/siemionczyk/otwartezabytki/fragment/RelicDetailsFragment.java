package pl.siemionczyk.otwartezabytki.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pl.siemionczyk.otwartezabytki.BundleKeys;
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

        //get RelicsJson from arguments
        Bundle b = getArguments();
        if ( b != null && b.containsKey( BundleKeys.KEY_BUNDLE_SINGLE_RELIC_JSON)){
            fillViewsWithContent( (RelicJson) b.get( BundleKeys.KEY_BUNDLE_SINGLE_RELIC_JSON));
        }

        return view;
    }

    private void fillViewsWithContent ( RelicJson relic){
        tvRelicName.setText( relic.identification);

        tvLocation.setText( relic.place_name);

        tvDating.setText( relic.dating_of_obj);

        //insert description
        tvDescription.setText(Html.fromHtml(relic.description));

        //insert trivia  / interesting facts / legends
        String legendsTitles = "";
        for ( RelicJson.EntryJson entry : relic.entries){
            legendsTitles += entry.title + " \n";
        }
        tvLegends.setText( legendsTitles);


        //insert dates
        String dates = "";
        for (RelicJson.EventJson event: relic.events){
            dates += "<b>" + event.date + "</b> - " + event.name + "<br/><br/>";
        }

        if ( !dates.equals("")) dates = dates.substring(0, dates.length()-5);   //this cut the last <br/>

        tvDates.setText( Html.fromHtml( dates));



        //insert categories
        StringBuilder catB = new StringBuilder();
        for ( String cat : relic.categories){
            catB.append( cat).append(", ");
        }
        if ( catB.length() > 0) catB.delete( catB.length()-2, catB.length());  //this cut the last ", "

        tvCategories.setText( catB.toString());


        //insert tags
        StringBuilder tagB = new StringBuilder();
        for ( String tag : relic.tags){
            tagB.append( tag).append(", ");
        }
        if ( tagB.length() > 0) tagB.delete( tagB.length()-2, tagB.length());  //this cut the last ", "

        tvTags.setText( tagB.toString());




        tvRelicRegisterNr.setText( relic.register_number);





    }
}
