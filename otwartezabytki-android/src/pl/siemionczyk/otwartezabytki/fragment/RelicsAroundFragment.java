package pl.siemionczyk.otwartezabytki.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import butterknife.InjectView;
import butterknife.Views;
import pl.siemionczyk.otwartezabytki.OtwarteZabytkiApp;
import pl.siemionczyk.otwartezabytki.R;
import pl.siemionczyk.otwartezabytki.helper.MyLog;
import pl.siemionczyk.otwartezabytki.rest.OtwarteZabytkiClient;
import pl.siemionczyk.otwartezabytki.rest.RelicJson;
import pl.siemionczyk.otwartezabytki.rest.RelicJsonWrapper;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by majkeliusz on 7/7/13.
 */
public class RelicsAroundFragment extends Fragment {

    public final static String TAG = "RelicsAroundFragment";

    @InjectView( R.id.list_view_relics ) ListView mListViewRelics;

    @InjectView( R.id.progress_bar_relics ) LinearLayout mProgressBar;

    SimpleAdapter mAdapter;

    @Inject
    OtwarteZabytkiClient mClient;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//

        View view = inflater.inflate(R.layout._ft_relics_around,
                container, false);

        //inject dagger
        ((OtwarteZabytkiApp) getActivity().getApplication()).inject(this);
        //inject views
        Views.inject( this, view);

        //donwload
        downloadListRelics();

//       mAdapter = new SimpleAdapter( getActivity(), null, android.R.layout.simple_list_item_2,  )

//        mListViewRelics.setAdapter( mAdapter );

        return view;
    }

    private void fillListView( ArrayList<RelicJson> relics){

        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>(  );

        //stupind conversion
        for ( RelicJson r : relics){
            HashMap<String, String> map = new HashMap<String, String>(  );
            map.put( "name", r.identification );
            map.put( "place", r.place_name );
            list.add( map );
        }



        mAdapter = new SimpleAdapter( getActivity(), list, android.R.layout.simple_list_item_2,
                new String[]{"name", "place"}, new int[] {android.R.id.text1, android.R.id.text2 });

        mListViewRelics.setAdapter( mAdapter );
    }



    private void downloadListRelics(){

        Callback<RelicJsonWrapper> cb = new Callback<RelicJsonWrapper>() {
            @Override
            public void success ( RelicJsonWrapper relicJsonWrapper, Response response ) {
                MyLog.i( TAG, "success on downloading relics:" + relicJsonWrapper.relics.size() );

                //hide progressBar
                mProgressBar.setVisibility( View.GONE );

                //fill listview
                fillListView( relicJsonWrapper.relics );


            }

            @Override
            public void failure ( RetrofitError retrofitError ) {
                MyLog.i( TAG, "failture on connection:" + retrofitError );
            }
        };
        mClient.getSideEffectsAround( 52.232222f, 21.008333f, 1.3f, true, cb );
    }
}
