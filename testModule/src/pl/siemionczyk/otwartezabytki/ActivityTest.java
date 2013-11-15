package pl.siemionczyk.otwartezabytki;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by michalsiemionczyk on 14/11/13.
 */
public class ActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    FragmentActivity mActivity;

    @Override
    protected void setUp () throws Exception {
//        super.setUp();

        mActivity = getActivity();

        Instrumentation mInstr = getInstrumentation();

//        mInstr.

    }

    @Override
    protected void runTest () throws Throwable {

        Resources r = mActivity.getResources();
        assertNotNull( r );

//        Instrumentation.

        //mActivity.fini
    }

    public ActivityTest ( Class<MainActivity> activityClass ) {
        super( activityClass );
    }
}
