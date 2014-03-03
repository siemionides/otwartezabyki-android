package pl.siemionczyk.otwartezabytki.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import pl.siemionczyk.otwartezabytki.R;

/**
 * Created by michalsiemionczyk on 12/02/14.
 */
public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }
}