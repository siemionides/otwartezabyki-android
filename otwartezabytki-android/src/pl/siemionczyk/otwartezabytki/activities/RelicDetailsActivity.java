package pl.siemionczyk.otwartezabytki.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import pl.siemionczyk.otwartezabytki.BundleKeys;
import pl.siemionczyk.otwartezabytki.R;
import pl.siemionczyk.otwartezabytki.fragment.RelicDetailsFragment;
import pl.siemionczyk.otwartezabytki.fragment.RelicsDetailsPagerFragment;
import pl.siemionczyk.otwartezabytki.rest.RelicJsonWrapper;

/**
 * Created by michalsiemionczyk on 04/01/14.
 */
public class RelicDetailsActivity extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout._at_relic_details);


        //inject the fragment to content frame
        Bundle b = getIntent().getExtras();


        RelicJsonWrapper relicsWrapper;
        int relicPosition;

        if ( b!= null && b.containsKey( BundleKeys.KEY_BUNDLE_RELICS_WRAPPER) && b.containsKey( BundleKeys.KEY_BUNDLE_RELIC_POSITION)){
            relicsWrapper = (RelicJsonWrapper) b.getSerializable( BundleKeys.KEY_BUNDLE_RELICS_WRAPPER);
            relicPosition = b.getInt( BundleKeys.KEY_BUNDLE_RELIC_POSITION);
        } else throw new UnsupportedOperationException( "no proper keys in bundle") ;

        //set fragment
        Fragment f = new RelicsDetailsPagerFragment();
        Bundle bF = new Bundle();
        bF.putSerializable( BundleKeys.KEY_BUNDLE_RELICS_WRAPPER, relicsWrapper);
        bF.putSerializable( BundleKeys.KEY_BUNDLE_RELIC_POSITION, relicPosition);

        f.setArguments( bF);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, f)
                .commit();
    }
}
