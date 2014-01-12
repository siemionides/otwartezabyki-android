package pl.siemionczyk.otwartezabytki.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import pl.siemionczyk.otwartezabytki.BundleKeys;
import pl.siemionczyk.otwartezabytki.R;

/**
 * Created by michalsiemionczyk on 12/01/14.
 */
public class TriviaDialog extends DialogFragment {

    TextView triviaContent;
    Button buttonClose;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout._ft_dialog_trivia,
                container, false);

        //get args
        Bundle args = getArguments();
        if ( ! args.containsKey(BundleKeys.KEY_BUNDLE_TRIVIA_TITLE) || ! args.containsKey( BundleKeys.KEY_BUNDLE_TRIVIA_CONTENT) ){
            throw new UnsupportedOperationException( " title and content should be hre");
        }
        String title = args.getString( BundleKeys.KEY_BUNDLE_TRIVIA_TITLE);
        String body = args.getString( BundleKeys.KEY_BUNDLE_TRIVIA_CONTENT);


        //inject views
        triviaContent =  ( TextView) rootView.findViewById( R.id.tv_trivia_content);
        buttonClose = ( Button) rootView.findViewById(R.id.trivia_close_button);


        //fill views
        getDialog().setTitle( title);
        triviaContent.setText( Html.fromHtml( body ));


        //set listeners
        buttonClose.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().cancel();
            }
        });

        return rootView;
    }
}
