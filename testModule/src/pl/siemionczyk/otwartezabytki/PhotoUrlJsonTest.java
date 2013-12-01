package pl.siemionczyk.otwartezabytki;

import android.test.AndroidTestCase;
import pl.siemionczyk.otwartezabytki.rest.PhotoUrlJson;

/**
 * Created by michalsiemionczyk on 14/11/13.
 */
public class PhotoUrlJsonTest extends AndroidTestCase {



    public void testThis(){
        PhotoUrlJson p = new PhotoUrlJson();
        p.url = "lalala";

        assertEquals( p.url, "lalala" );
//
//        MockContext a = new MockContext();
//
//        a.setTheme( R.style.AppBaseTheme );
//
//        assertEquals(a.getTheme(),  R.style.AppBaseTheme );

    }
}
