package pl.siemionczyk.otwartezabytki.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;

import javax.inject.Inject;

import pl.siemionczyk.otwartezabytki.OtwarteZabytkiApp;
import pl.siemionczyk.otwartezabytki.R;
import pl.siemionczyk.otwartezabytki.helper.MyLog;
import pl.siemionczyk.otwartezabytki.rest.OtwarteZabytkiClient;
import pl.siemionczyk.otwartezabytki.rest.RelicJsonWrapper;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by majkeliusz on 7/7/13.
 */
public class SearchRelicFragment extends Fragment {

    public static String TAG = "SearchRelicFragment";

    @Inject
    OtwarteZabytkiClient mClient;


    CheckBox mCheckBoxDistance;

    Spinner mSpinnerDistances;

    Button mButtonSearch;

    EditText mEtRelicName;

    EditText mEtPlaceName;

    EditText mEtDateFrom;

    EditText mEtDateTo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         View containerView = inflater.inflate(R.layout._ft_screen_wyszukaj,
                container, false);

        //inject dagger
        ((OtwarteZabytkiApp) getActivity().getApplication()).inject(this);


        configureViews( containerView);


        return containerView;
    }


    public void configureViews ( View rootView){
        mSpinnerDistances = ( Spinner) rootView.findViewById( R.id.spinner_distances);

        mCheckBoxDistance = ( CheckBox) rootView.findViewById( R.id.checkBox_distance_from);
        mCheckBoxDistance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MyLog.i( TAG, "on checked:" + isChecked);

                ((Spinner) mSpinnerDistances).getSelectedView().setEnabled( isChecked);
                mSpinnerDistances.setEnabled(isChecked);

            }
        });
        mCheckBoxDistance.setChecked( false);


        mButtonSearch = ( Button) rootView.findViewById( R.id.button_search);
        mButtonSearch.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performRequestAndDisplayResults();
            }
        });

        mEtPlaceName = ( EditText) rootView.findViewById( R.id.et_place_name);

        mEtRelicName = ( EditText) rootView.findViewById( R.id.et_relic_name);

        mEtDateFrom = ( EditText) rootView.findViewById( R.id.et_date_from);

        mEtDateTo = ( EditText) rootView.findViewById( R.id.et_date_to);


    }

    private void performRequestAndDisplayResults() {
        String relicName = mEtRelicName.getText().toString();

        String relicPlace = mEtPlaceName.getText().toString();

        String dateFrom = mEtDateFrom.getText().toString();

        String dateTo = mEtDateTo.getText().toString();

        Callback<RelicJsonWrapper> cb = new Callback<RelicJsonWrapper>(){

            @Override
            public void success(RelicJsonWrapper relicJsonWrapper, Response response) {
                MyLog.i( TAG, "success!");
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                MyLog.i( TAG, "failure!");

            }
        };

        mClient.getSideEffects( relicPlace, relicName, dateFrom, dateTo, cb );

    }


}
