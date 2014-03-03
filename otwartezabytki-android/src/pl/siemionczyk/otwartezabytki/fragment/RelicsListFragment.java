package pl.siemionczyk.otwartezabytki.fragment;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import pl.siemionczyk.otwartezabytki.BundleKeys;
import pl.siemionczyk.otwartezabytki.OtwarteZabytkiApp;
import pl.siemionczyk.otwartezabytki.R;
import pl.siemionczyk.otwartezabytki.activities.MapActivity;
import pl.siemionczyk.otwartezabytki.activities.SearchResultRelicListActivity;
import pl.siemionczyk.otwartezabytki.adapters.RelicsAroundAdapter;
import pl.siemionczyk.otwartezabytki.helper.HelperToolkit;
import pl.siemionczyk.otwartezabytki.helper.MyLog;
import pl.siemionczyk.otwartezabytki.rest.OtwarteZabytkiClient;
import pl.siemionczyk.otwartezabytki.rest.RelicJsonWrapper;
import pl.siemionczyk.otwartezabytki.rest.relicjson.RelicJson;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import javax.inject.Inject;
import java.util.ArrayList;

/**
 * Created by majkeliusz on 7/7/13.
 * - Provides fragment for showing up relics list (when relicswrapper was found in bundle) OR
 * - finds relics in the around and shows them!
 *
 */
public class RelicsListFragment extends Fragment implements SearchView.OnQueryTextListener, SearchView.OnCloseListener {

    public final static String TAG = "RelicsListFragment";

    private final static float RADIUS_OF_SEARCH = 5f; //in kilometers

    PullToRefreshListView mListViewRelics;

    LinearLayout mProgressBar;

    RelicsAroundAdapter mAdapter;

    TextView mNrAllRelicsFound, mNrRelicsShownBelow, mNrAllPagesOfRelics;

    TextView mRadiusRelics;

    TextView title;


    private Location mLastFoundLocation;

//    private SearchView mSearchView;



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

        //set title
//        getActivity().setTitle( R.string.main_menu_w_okolicy);

        //inject views
        mListViewRelics = ( PullToRefreshListView ) view.findViewById( R.id.list_view_relics );
        mProgressBar = ( LinearLayout ) view.findViewById( R.id.progress_bar_relics );
        mNrAllRelicsFound = ( TextView ) view.findViewById( R.id.tv_nr_relic_found );
        mRadiusRelics = ( TextView ) view.findViewById( R.id.tv_relic_found_radius );
        mNrRelicsShownBelow = ( TextView) view.findViewById( R.id.tv_nr_relic_shown_below);
        mNrAllPagesOfRelics = (TextView) view.findViewById( R.id.tv_nr_all_relic_pages);
        title = ( TextView) view.findViewById( R.id.textView_title);


        //set list on click listener
        mListViewRelics.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //get relic

//                mAdapter.getItem( position );
//                RelicsAroundAdapter adapt = ( RelicsAroundAdapter) parent.getAdapter();
                RelicJson relic = mAdapter.getItem(position);
                HelperToolkit.makeToast( getActivity(), relic.identification);

                replaceToRelicDetailsFragment(
                        new RelicJsonWrapper(mAdapter.getItems()), position);


            }
        });

        if ( getArguments() != null && getArguments().containsKey( BundleKeys.KEY_BUNDLE_RELICS_WRAPPER ) ){
            //FROM DETAILED SEARCH - list of relics found

            mProgressBar.setVisibility( View.GONE);

            //add them to adapter
            final RelicJsonWrapper rWrapper = ( RelicJsonWrapper) getArguments().get( BundleKeys.KEY_BUNDLE_RELICS_WRAPPER);

            final Location userLocation = getLastKnownLocation();
            mAdapter = new RelicsAroundAdapter( getActivity(), R.layout.list_item_relic_around, rWrapper.relics, userLocation
                     );
            mAdapter.setNrPagesLoaded( 1 );

            mAdapter.notifyDataSetChanged();
            mListViewRelics.setAdapter( mAdapter);

            //configure pullable list
            // The same serach as for detiled search should be performed
            mListViewRelics.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
                @Override
                public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                    // Do work to refresh the list here.
                    int currentPage = mAdapter.getNrPagesLoaded();

                    performRequestForSearchQueryAndDisplay( rWrapper.searchQueryDetails, currentPage+1, userLocation);
                }
            });

        } else {
            //FROM relics around
            //get positionInfo
            getLocationInfoAndUpdateRelicsAround(RADIUS_OF_SEARCH, 1, false);  //for the very first page of results

            //configure pullable list
            // Set a listener to be invoked when the list should be refreshed - the relics around are to be found
            mListViewRelics.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
                @Override
                public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                    // Do work to refresh the list here.
                    int currentPage = mAdapter.getNrPagesLoaded();

                    getLocationInfoAndUpdateRelicsAround( RADIUS_OF_SEARCH, currentPage + 1, true );

                }
            });

        }

        return view;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_relics_around, menu);

        SearchView mSearchView= (SearchView) menu.findItem(R.id.action_search).getActionView();


        mSearchView.setOnQueryTextListener(this);
        mSearchView.setOnCloseListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_show_grid){
            HelperToolkit.makeToast( getActivity(), "not yet implemented");
            return true;

        } else if ( item.getItemId() == R.id.action_map){

            RelicJsonWrapper wrapper = new RelicJsonWrapper();
            wrapper.relics = mAdapter.getItems();

            Intent i = new Intent( getActivity(), MapActivity.class);
            i.putExtra(BundleKeys.KEY_BUNDLE_RELICS_WRAPPER, wrapper);

            startActivity(i);

            return true;

        } else if ( item.getItemId() == R.id.action_search){
            HelperToolkit.makeToast( getActivity(), "not yet implemented");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextChange(String query) {
        if (mAdapter != null) {

            MyLog.i( TAG, "filter for:" + query);
            mAdapter.getFilter().filter(query, new Filter.FilterListener() {
                @Override
                public void onFilterComplete(int count) {
                    MyLog.i( TAG, "filter count:" + count);
                }
            });

        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String arg0) {
        return true;
    }

    @Override
    public boolean onClose() {
        mAdapter.getFilter().filter("");
        return true;
    }


    private Location getLastKnownLocation (){
        LocationManager locationManager;
        String svcName = Context.LOCATION_SERVICE;
        locationManager = ( LocationManager) getActivity().getSystemService(svcName);

        return locationManager.getLastKnownLocation( LocationManager.GPS_PROVIDER);


    }


    private void getLocationInfoAndUpdateRelicsAround(final float radius, final int page, final boolean fromPullRefresh) {
        LocationManager locationManager;
        String svcName = Context.LOCATION_SERVICE;
        locationManager = ( LocationManager) getActivity().getSystemService(svcName);



        LocationListener ll = new LocationListener() {
            @Override
            public void onLocationChanged ( Location location ) {
                MyLog.i( TAG, "onLocationChanged: long: " + location.getLongitude() + ", lat:" + location.getLatitude()   );

                String latLongString;
                latLongString = "lat: " + location.getLatitude() + ", long: " + location.getLongitude();

                //set this temporary view value
                title.setText( latLongString );


                //donwload the relics
                performReqForRelicsAroundAndDisplay(location, radius, page, fromPullRefresh);
            }

            @Override
            public void onStatusChanged ( String provider, int status, Bundle extras ) {
                MyLog.i( TAG, "onStatusChanged"  );

            }

            @Override
            public void onProviderEnabled ( String provider ) {
                MyLog.i( TAG, "onProviderEnabled"  );

            }

            @Override
            public void onProviderDisabled ( String provider ) {
                MyLog.i( TAG, "onProviderDisabled"  );

            }
        };

        locationManager.requestSingleUpdate( LocationManager.GPS_PROVIDER, ll, null );

    }

    /** */
    private void fillListView( ArrayList<RelicJson> relics, Location userLocation, int currentResultPage, boolean fromPullRefresh){

        if ( fromPullRefresh ){
            mAdapter.addRelicsToTheEnd(relics);
            HelperToolkit.makeToast( getActivity().getApplicationContext(),
                    "new relics added\nnr new: " + relics.size() +"\nnr all: " + mAdapter.getCount() +  "\npages loaded: " + currentResultPage  );
            mAdapter.notifyDataSetChanged();
            mListViewRelics.onRefreshComplete();

        } else {
            mAdapter = new RelicsAroundAdapter( getActivity(), R.layout.list_item_relic_around, relics, userLocation );
            mListViewRelics.setAdapter( mAdapter );
        }

        mAdapter.setNrPagesLoaded( currentResultPage );
    }


    private void performRequestForSearchQueryAndDisplay( final RelicJsonWrapper.DetailsOfSearchQuery qD,
                                                         int nrPage, final Location userLocation ) {
        //get data from UI
        Callback<RelicJsonWrapper> cb = new Callback<RelicJsonWrapper>(){

            @Override
            public void success(RelicJsonWrapper relicJsonWrapper, Response response) {
                MyLog.i( TAG, "success!");
                //update by query details

                fillListView( relicJsonWrapper.relics, userLocation, relicJsonWrapper.meta.current_page, true );

                //update nr_relics field
                mNrAllRelicsFound.setText(Integer.toString( relicJsonWrapper.meta.total_count  ));

                //update nr relics shown below
                int nrRelics = mAdapter == null ? relicJsonWrapper.relics.size() : mAdapter.getCount();

                mNrRelicsShownBelow.setText( Integer.toString( nrRelics  ));

                //update nr Pages
                mNrAllPagesOfRelics.setText( Integer.toString( relicJsonWrapper.meta.total_pages ));


                //update radius
                mRadiusRelics.setText( Float.toString( RADIUS_OF_SEARCH) );


            }

            @Override
            public void failure(RetrofitError retrofitError) {
                MyLog.i( TAG, "failure!");

            }
        };

        mClient.getRelics(qD.relicPlace, qD.relicName, qD.dateFrom, qD.dateTo, qD.onlyWithPhotos, nrPage, cb);
    }

    /**
     * @param location location
     *
     * @param radius in kilometers*/
    private void performReqForRelicsAroundAndDisplay(final Location location, final float radius, final int page, final boolean fromPullRefresh){

        Callback<RelicJsonWrapper> cb = new Callback<RelicJsonWrapper>() {
            @Override
            public void success ( RelicJsonWrapper relicJsonWrapper, Response response ) {
                MyLog.i( TAG, "success on downloading relics: " + relicJsonWrapper.relics.size() );
                MyLog.i( TAG, "current Page: " + relicJsonWrapper.meta.current_page );
                MyLog.i( TAG, "all pages: " + relicJsonWrapper.meta.total_pages );

                for ( RelicJson r: relicJsonWrapper.relics ) {
                    MyLog.i(TAG, "name: " + r.identification +" long:" + r.longitude + ", lat:" + r.latitude + " nrPhotos: " + r.photos.size() );
                }

                //fill listview
                fillListView( relicJsonWrapper.relics , location, relicJsonWrapper.meta.current_page, fromPullRefresh );

                //hide progressBar
                mProgressBar.setVisibility( View.GONE );

                //update nr_relics field
                mNrAllRelicsFound.setText(Integer.toString( relicJsonWrapper.meta.total_count  ));

                //update nr relics shown below
                int nrRelics = mAdapter == null ? relicJsonWrapper.relics.size() : mAdapter.getCount();

                mNrRelicsShownBelow.setText( Integer.toString( nrRelics  ));

                //update nr Pages
                mNrAllPagesOfRelics.setText( Integer.toString( relicJsonWrapper.meta.total_pages ));


                //update radius
                mRadiusRelics.setText( Float.toString( radius) );
            }

            @Override
            public void failure ( RetrofitError retrofitError ) {
                MyLog.i( TAG, "failture on connection:" + retrofitError );
            }
        };
        mClient.getRelicsAround((float) location.getLatitude(), (float) location.getLongitude(), radius, true, page, cb);
    }

    public void replaceToRelicDetailsFragment( RelicJsonWrapper relicWrapper, int currentItem){
        RelicsDetailsPagerFragment fragment = new RelicsDetailsPagerFragment(); //TODO, find one in stack

        Bundle b = new Bundle();
        b.putSerializable( BundleKeys.KEY_BUNDLE_RELICS_WRAPPER, relicWrapper);
        b.putSerializable( BundleKeys.KEY_BUNDLE_RELIC_POSITION, currentItem);

        fragment.setArguments( b);

        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                .replace(R.id.content_frame, fragment)
                .addToBackStack(null)
                .commit();
    }
}
