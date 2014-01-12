package pl.siemionczyk.otwartezabytki.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidquery.AQuery;

import pl.siemionczyk.otwartezabytki.BundleKeys;
import pl.siemionczyk.otwartezabytki.R;
import pl.siemionczyk.otwartezabytki.activities.FullScreenPhotoActivity;
import pl.siemionczyk.otwartezabytki.activities.MainActivity;
import pl.siemionczyk.otwartezabytki.helper.HelperToolkit;
import pl.siemionczyk.otwartezabytki.helper.MyLog;
import pl.siemionczyk.otwartezabytki.rest.OtwarteZabytkiApi;
import pl.siemionczyk.otwartezabytki.rest.OtwarteZabytkiClient;
import pl.siemionczyk.otwartezabytki.rest.relicjson.PhotoJson;
import pl.siemionczyk.otwartezabytki.rest.relicjson.RelicJson;

/**
 * Created by majkeliusz on 7/7/13.
 */
public class RelicDetailsFragment extends Fragment {

    private static final String TAG = "RelicDetailsFragment";
    TextView tvRelicName;
    TextView tvLocation;
    TextView tvDating;
    TextView tvDescription;
    TextView tvLegends;
    TextView tvDates;
    TextView tvCategories;
    TextView tvTags;
    TextView tvRelicRegisterNr;

    LinearLayout photosLayout;
//    ImageView ivMainPhoto;



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
        photosLayout = ( LinearLayout) view.findViewById( R.id.layout_photos_gallery);
//        ivMainPhoto = (ImageView) view.findViewById( R.id.main_photo);

        //get RelicsJson from arguments
        Bundle b = getArguments();
        if ( b != null && b.containsKey( BundleKeys.KEY_BUNDLE_SINGLE_RELIC_JSON)){
            AQuery aq = new AQuery( getActivity(), view);
            fillViewsWithContent( (RelicJson) b.get( BundleKeys.KEY_BUNDLE_SINGLE_RELIC_JSON), aq);
        }



        return view;
    }

    private void fillViewsWithContent ( final RelicJson relic, AQuery aq){
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





        //insert photo in there
        int id = 54335323;
        for ( PhotoJson pJ : relic.photos){

            ImageView iv = new ImageView( getActivity());
            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins( 15, 0, 15, 0 );


            iv.setLayoutParams( params);
            iv.setId(id );


            //set on click listener
            final PhotoJson pjF = pJ;
            iv.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HelperToolkit.makeToast( getActivity(), "relic photo clicked:" + pjF.file.midi.url);

                    //create String[] with urls to maxi photos and get it
                    String[] urls = relic.getUrlsToMaxiPhotos();
                    int nrPhoto = relic.photos.indexOf( pjF);

                    onPhotoThumbnailClicked( nrPhoto, urls );
                }
            });


            photosLayout.addView( iv);

            aq.id( id).image( OtwarteZabytkiClient.HOST + pJ.file.midi.url);

            MyLog.i( TAG, "trying to add photo:" + pJ.file.midi.url);

            id++;
        }


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


    private void onPhotoThumbnailClicked( int nrPhoto, String[] urls){

        Bundle b = new Bundle();
        b.putInt( BundleKeys.KEY_BUNDLE_PHOTOS_NR, nrPhoto);
        b.putStringArray( BundleKeys.KEY_BUNDLE_PHOTOS_URLS_ARRAY, urls);

        Intent i = new Intent( getActivity(), FullScreenPhotoActivity.class);
        i.putExtras( b);

        startActivity( i);
    }
}
