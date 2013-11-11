package pl.siemionczyk.otwartezabytki.helper;

import android.util.Log;
import pl.siemionczyk.otwartezabytki.BuildConfig;

/**
 * Created by michalsiemionczyk on 11/11/13.
 */
public class MyLog {

    public static void i(String TAG, String text){
        if ( BuildConfig.DEBUG == true){
            Log.i(TAG, text);
        }
    }
}
