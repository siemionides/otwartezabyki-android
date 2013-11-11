package pl.siemionczyk.otwartezabytki;


import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import org.json.JSONObject;
import pl.siemionczyk.otwartezabytki.domain.Relic;
import pl.siemionczyk.otwartezabytki.fragment.AddRelicFragment;
import pl.siemionczyk.otwartezabytki.fragment.SearchRelicFragment;
import pl.siemionczyk.otwartezabytki.helper.HelperToolkit;


public class MainActivity extends FragmentActivity implements ActionBar.OnNavigationListener{
	
	private final static String TAG = "MainActivity";

    //TODO that's how you should server main menu;
    private final static int a = R.string.main_menu_about;

    /** This is my short hack for serving the spinner menu*/


//    public final static String[] main_menu_labels = new String[]{
//            getResources().getS
//    }


//dsdsdsdsds
//    private final  String a = getResources().getString(R.array.menu_elements[])

    private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
    int b = 4;

    int c = 3;


    /** ListView for showing up the results */
	private ListView mListView;


//    fdfdfd



    /** contains main meu */
    ArrayAdapter<String> spinnerAdapter = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_list_relics_around);
		
		 //latitude 53.1346562
			//+ longitude 23.1685799



        // Set up the action bar to show a dropdown list.
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        // This is nasty hack, will have to figure it out better
        final String[] dropdownValues = new String[]{
                getResources().getString( R.string.main_menu_wyszukaj ),
                getResources().getString( R.string.main_menu_dodaj ),
                getResources().getString( R.string.main_menu_ulubione ),
                getResources().getString( R.string.main_menu_mapa ),
                getResources().getString( R.string.main_menu_ustawienia ),
                getResources().getString( R.string.main_menu_about )
        };


        // Specify a SpinnerAdapter to populate the dropdown list.
       this.spinnerAdapter = new ArrayAdapter<String>(actionBar.getThemedContext(),
                android.R.layout.simple_spinner_item, android.R.id.text1,
                dropdownValues);

       this.spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        // Set up the dropdown list navigation in the action bar.
        actionBar.setListNavigationCallbacks(spinnerAdapter, this);

        // Use getActionBar().getThemedContext() to ensure
        // that the text color is always appropriate for the action bar
        // background rather than the activity background.


	}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    /** Serves navigation menu */
    @Override
    public boolean onNavigationItemSelected(int position, long id) {
        // When the given dropdown item is selected, show its contents in the
        // container view.

        String s = spinnerAdapter.getItem(position);
        if (s.equals(getResources().getString(R.string.main_menu_wyszukaj))){
            Fragment fragment = new SearchRelicFragment();
            Bundle args = new Bundle();
//            args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
            fragment.setArguments(args);
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment).commit();
            //show main menu wyszukaj;
        } else if (s.equals(getResources().getString(R.string.main_menu_dodaj))){
            Fragment fragment = new AddRelicFragment();
            Bundle args = new Bundle();
//            args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
            fragment.setArguments(args);
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment).commit();
        } else if (s.equals(getResources().getString(R.string.main_menu_mapa))){
            ;
            //show main menu wyszukaj;
        } else if (s.equals(getResources().getString(R.string.main_menu_ustawienia))){
            ;
            //show main menu wyszukaj;
        } else if (s.equals(getResources().getString(R.string.main_menu_ulubione))){
            ;
            //show main menu wyszukaj;
        } else if (s.equals(getResources().getString(R.string.main_menu_about))){
            ;
            //show main menu wyszukaj;
        }



        HelperToolkit.makeToast(this, "selected element pos:" + position);
        Log.d(TAG, "item selected! pos:" + position + ", id:" + id);

        return true;
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





	

	
	   



	



}
