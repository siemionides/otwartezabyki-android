package pl.siemionczyk.otwartezabytki;

import android.test.InstrumentationTestCase;
import junit.framework.Assert;
import pl.siemionczyk.otwartezabytki.helper.HelperToolkit;

/**
 * This is a simple framework for a test of an Application.  See
 * {@link android.test.ApplicationTestCase ApplicationTestCase} for more information on
 * how to write and extend Application tests.
 * <p/>
 * To run this test, you can type:
 * adb shell am instrument -w \
 * -e class com.mojeleczenie.activities.TutorialActivityTest \
 * com.mojeleczenie.tests/android.test.InstrumentationTestRunner
 */
public class HelperClassTest extends InstrumentationTestCase {

    public HelperClassTest () {
        super();
    }

    public void testChangeTimeUnits() throws Exception {

        //3, 2 , 1 , 0
        Assert.assertEquals( HelperToolkit.returnFive(), 5);          //year


    }

    public void testMethod(){
        Assert.assertEquals (true, true);
    }
}
