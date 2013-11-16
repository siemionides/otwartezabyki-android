package pl.siemionczyk.otwartezabytki;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.InjectView;
import butterknife.Views;
import com.squareup.otto.Subscribe;
import pl.siemionczyk.otwartezabytki.event.EventBus;
import pl.siemionczyk.otwartezabytki.event.events.FromServiceEvent;
import pl.siemionczyk.otwartezabytki.event.events.TestEvent;
import pl.siemionczyk.otwartezabytki.event.events.ToServiceEvent;
import pl.siemionczyk.otwartezabytki.fragment.*;
import pl.siemionczyk.otwartezabytki.helper.HelperToolkit;
import pl.siemionczyk.otwartezabytki.helper.MyLog;
import pl.siemionczyk.otwartezabytki.rest.OtwarteZabytkiClient;
import pl.siemionczyk.otwartezabytki.rest.RelicJsonWrapper;
import pl.siemionczyk.otwartezabytki.service.TestService;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import javax.inject.Inject;


public class MainActivity extends FragmentActivity{
	
	private final static String TAG = "MainActivity";

    @InjectView( R.id.drawer_layout )  DrawerLayout mDrawerLayout;

    @InjectView( R.id.left_drawer )     ListView mDrawerList;

    ActionBarDrawerToggle mDrawerToggle;



    private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";

    String[] dropdownValues;

    CharSequence mTitle;

    @Inject
    OtwarteZabytkiClient client;

    @Inject
    EventBus bus;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.navigation_drawer );

        ((OtwarteZabytkiApp) getApplication()).inject(this);

        //inject Views
        Views.inject( this);

        configureLeftMenu();

        //by deafult open 0
        selectItem( 0, getResources().getString( R.string.main_menu_w_okolicy ));

	}

    @Override
    protected void onResume () {
        super.onResume();

        bus.register( this );
    }

    @Override
    protected void onPause () {
        super.onPause();

        bus.unregister( this );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore the previously serialized current dropdown position.
        if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
            getActionBar().setSelectedNavigationItem(savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Serialize the current dropdown position.
        outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getActionBar()
                .getSelectedNavigationIndex());
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position, ((TextView) view).getText().toString() );
        }
    }

    @Subscribe
    public void onTestEventCome( TestEvent event){
        MyLog.i( TAG,  "Test event come back!!!" );
    }

    @Subscribe
    public void onFromServiceEvent (FromServiceEvent event){
        MyLog.i( TAG,  "From service event: received " + event.value );

    }

    private void configureLeftMenu(){
        // This is nasty hack, will have to figure it out better
        dropdownValues = new String[]{
                getResources().getString( R.string.main_menu_w_okolicy ),
                getResources().getString( R.string.main_menu_wyszukaj ),
                getResources().getString( R.string.main_menu_dodaj ),
                getResources().getString( R.string.main_menu_favourites ),
                getResources().getString( R.string.main_menu_map ),
                getResources().getString( R.string.main_menu_settings ),
                getResources().getString( R.string.main_menu_about )
        };

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, dropdownValues));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());




        //validate actin bar
        mDrawerToggle = new ActionBarDrawerToggle( this, mDrawerLayout,
                R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close ) {

            /**
             * Called when a drawer has settled in a completely closed state.
             */
            public void onDrawerClosed ( View view ) {
                getActionBar().setTitle( mTitle );
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /**
             * Called when a drawer has settled in a completely open state.
             */
            public void onDrawerOpened ( View drawerView ) {
                getActionBar().setTitle( mTitle );
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
    }

    /** Swaps fragments in the main content view */
    private void selectItem(int position,  String textButton) {

        Fragment f = null;

        if ( textButton.equals( getString( R.string.main_menu_w_okolicy ) )) f = new RelicsAroundFragment();
        else if (textButton.equals( getString( R.string.main_menu_wyszukaj ) )){
            f = new SearchRelicFragment();
            bus.post( new TestEvent() );
        }
        else if (textButton.equals( getString( R.string.main_menu_wyszukaj ) )) f = new SearchRelicFragment();
        else if (textButton.equals( getString( R.string.main_menu_dodaj ) )) HelperToolkit.makeToast( this, "Adding relics not implemented yet");

        else if (textButton.equals( getString( R.string.main_menu_favourites ) )){
            //test internet here
            MyLog.i( TAG, "trying to test internet here..." );

            f = new FavouriteRelicsFragment();
            Callback<RelicJsonWrapper> cb = new Callback<RelicJsonWrapper>() {
                @Override
                public void success ( RelicJsonWrapper relicJsonWrapper, Response response ) {
                    MyLog.i( TAG, "internet success, nrRelics:" + relicJsonWrapper.relics.size() );
                }

                @Override
                public void failure ( RetrofitError retrofitError ) {
                    MyLog.i( TAG, "internet failure:" + retrofitError.getMessage());

                }
            }            ;

            client.getSideEffects( "Warszawa", "", "", "", cb);
        }

        else if (textButton.equals( getString( R.string.main_menu_map ) )){
            f = new MapFragment();

            //start service
            Intent i = new Intent( this, TestService.class );
            startService( i );
        }

        else if (textButton.equals( getString( R.string.main_menu_settings ) )){
            HelperToolkit.makeToast( this, " Settings not implemented yet");

            ToServiceEvent event = new ToServiceEvent();
            event.value = 3;

            bus.post( event );
        }

        else if (textButton.equals( getString( R.string.main_menu_about ) )) f = new AboutFragment();

        if ( f != null){
            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, f)
                    .commit();
        }


        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle( textButton );
        mDrawerLayout.closeDrawer(mDrawerList);
    }

}
