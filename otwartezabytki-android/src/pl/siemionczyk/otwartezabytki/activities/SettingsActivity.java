package pl.siemionczyk.otwartezabytki.activities;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import pl.siemionczyk.otwartezabytki.R;

/**
 * Created by michalsiemionczyk on 12/02/14.
 */
public class SettingsActivity extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);


        setTitle( "Ustawienia");
    }
}
