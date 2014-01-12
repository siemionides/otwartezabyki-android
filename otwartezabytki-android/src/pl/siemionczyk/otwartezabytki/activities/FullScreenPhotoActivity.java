package pl.siemionczyk.otwartezabytki.activities;

/**
 * Created by michalsiemionczyk on 05/01/14.
 */

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import pl.siemionczyk.otwartezabytki.BundleKeys;
import pl.siemionczyk.otwartezabytki.R;
import pl.siemionczyk.otwartezabytki.adapters.FullScreenPhotoAdapter;

public class FullScreenPhotoActivity extends Activity {

    public final static String TAG = "FullScreenPhotoActivity";

    ViewPager viewPager;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout._at_fullscreen_gallery );


        String[] photosUrls;
        int nrOfCurrentPhoto = -1;

        //from bundle get nr of pictures
        //get urls to photos
        Bundle b = getIntent().getExtras();

        if (b != null && b.containsKey(BundleKeys.KEY_BUNDLE_PHOTOS_URLS_ARRAY)
                && b.containsKey(BundleKeys.KEY_BUNDLE_PHOTOS_NR)) {
            photosUrls = getIntent().getStringArrayExtra(BundleKeys.KEY_BUNDLE_PHOTOS_URLS_ARRAY);
            nrOfCurrentPhoto = getIntent().getIntExtra(BundleKeys.KEY_BUNDLE_PHOTOS_NR, -1);
        } else {
            throw new UnsupportedOperationException("should provide array of urls + nr of current photo;");
        }


        //inject views
        viewPager = ( ViewPager) findViewById( R.id.tutorial_pager );
//        tvMessage = ( TextView) findViewById( R.id.tutorial_explore_message );
//        layoutUnderstand = ( RelativeLayout) findViewById( R.id.understand_layout );

        //hide Action Bar
        getActionBar().hide();
//        getSupportActionBar().hide();

//        setButtonListeners( );


        FullScreenPhotoAdapter mPagerExploreAdapter;
        mPagerExploreAdapter = new FullScreenPhotoAdapter( this, photosUrls.length, photosUrls);

        viewPager.setAdapter( mPagerExploreAdapter );
        viewPager.setOffscreenPageLimit( 1 );
//        viewPager.setCurrentItem( nrOfCurrentPhoto );
    }

//    private void setButtonListeners () {
//        layoutUnderstand.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick ( View v ) {
//                onUnderStandClick();
//            }
//        } );
//    }

//    public void onUnderStandClick(){
//        MyLog.i( TAG, "clicked - method only:" + viewPager.getCurrentItem() + " , " + (PAGES_NR-1));
//        if ( viewPager.getCurrentItem() == PAGES_NR - 1){
//
//            finish();
//            Toast.makeText( this, "clicked", Toast.LENGTH_LONG );
//        }
//    }

//    private String[] getMessagesForPages( ){
//
//        String[] messages = new String[] {
//                getString( R.string.tutorial_explore_1 ),
//                getString( R.string.tutorial_explore_2 ),
//                getString( R.string.tutorial_explore_3 )
//        };
//
//        return messages;
//    }

    @Override
    protected void onSaveInstanceState ( Bundle outState ) {
        super.onSaveInstanceState( outState );

//        outState.putInt( KEY_PAGE_NR, viewPager.getCurrentItem() );
    }
}