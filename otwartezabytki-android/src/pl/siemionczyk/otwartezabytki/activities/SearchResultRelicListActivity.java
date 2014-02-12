package pl.siemionczyk.otwartezabytki.activities;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import pl.siemionczyk.otwartezabytki.BundleKeys;
import pl.siemionczyk.otwartezabytki.R;
import pl.siemionczyk.otwartezabytki.fragment.RelicsListFragment;
import pl.siemionczyk.otwartezabytki.rest.RelicJsonWrapper;


public class SearchResultRelicListActivity extends FragmentActivity {
	
	private final static String TAG = "SearchResultRelicListActivity";



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.navigation_drawer );

        //set title
        setTitle( R.string.main_menu_wyszukaj_wyniki );


        if ( !getIntent().getExtras().containsKey(BundleKeys.KEY_BUNDLE_RELICS_WRAPPER))
            throw new UnsupportedOperationException( "bundle should be provieded i");

        //get relics from intent
        RelicJsonWrapper rWrapper =
                ( RelicJsonWrapper) getIntent().getExtras().getSerializable( BundleKeys.KEY_BUNDLE_RELICS_WRAPPER);

        //insert proper fragment in here
        RelicsListFragment f = new RelicsListFragment();
        Bundle b = new Bundle();
        b.putSerializable( BundleKeys.KEY_BUNDLE_RELICS_WRAPPER, rWrapper );
        f.setArguments( b);

        getSupportFragmentManager().beginTransaction()
//                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                .replace(R.id.content_frame, f)
//                .addToBackStack(null)
                .commit();

	}



    @Override
    protected void onResume () {
        super.onResume();
    }

    @Override
    protected void onPause () {
        super.onPause();
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

        // Handle your other action bar items...
        return super.onOptionsItemSelected(item);
    }
}
