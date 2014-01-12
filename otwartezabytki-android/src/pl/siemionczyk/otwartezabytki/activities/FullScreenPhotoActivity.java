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
    TextView tvMessage;
    RelativeLayout layoutUnderstand;

    private final int PAGES_NR = 3;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout._at_fullscreen_gallery );


        //from bundle get nr of pictures
        //get urls to photos
        Bundle b = getIntent().getExtras();

        if ( b != null && b.containsKey(BundleKeys.KEY_BUNDLE_PHOTOS_URLS_ARRAY) && b.containsKey( BundleKeys.KEY_BUNDLE_PHOTOS_NR))

        getIntent().getStringArrayExtra( Bu)


        //inject views
        viewPager = ( ViewPager) findViewById( R.id.tutorial_pager );
//        tvMessage = ( TextView) findViewById( R.id.tutorial_explore_message );
//        layoutUnderstand = ( RelativeLayout) findViewById( R.id.understand_layout );

        //hide Action Bar
        getActionBar().hide();
//        getSupportActionBar().hide();

//        setButtonListeners( );


        FullScreenPhotoAdapter mPagerExploreAdapter;
        mPagerExploreAdapter = new FullScreenPhotoAdapter( this, 3,
                new int[]{
                        R.drawable.tutorial_s_przegladaj1,
                        R.drawable.tutorial_s_przegladaj2,
                        R.drawable.tutorial_s_przegladaj3
                } );

        viewPager.setAdapter( mPagerExploreAdapter );
        viewPager.setOffscreenPageLimit( 0 );
        viewPager.setCurrentItem( 0 );


        configureIndicator( indicator );
        configureIndicatorOnPageChangeListener( indicator, tvMessage, viewPager,
                getMessagesForPages(), PAGES_NR, layoutUnderstand );

        //configure indiator
        indicator.setViewPager(viewPager);


        //configure message "understand" to remain during on conf changes
        if ( savedInstanceState != null){

            int bundle_page_id = savedInstanceState.getInt( KEY_PAGE_NR );
            MyLog.i( TAG, "bundle not null, " + savedInstanceState.getInt( KEY_PAGE_NR ) );
            if ( bundle_page_id == PAGES_NR - 1){
                ViewHelper.setAlpha(layoutUnderstand,  1 );
            }
        } else {
            MyLog.i( TAG, "bundle is null!" );
        }
    }

    private void setButtonListeners () {
        layoutUnderstand.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick ( View v ) {
                onUnderStandClick();
            }
        } );
    }

    public void onUnderStandClick(){
        MyLog.i( TAG, "clicked - method only:" + viewPager.getCurrentItem() + " , " + (PAGES_NR-1));
        if ( viewPager.getCurrentItem() == PAGES_NR - 1){

            finish();
            Toast.makeText( this, "clicked", Toast.LENGTH_LONG );
        }
    }

    private String[] getMessagesForPages( ){

        String[] messages = new String[] {
                getString( R.string.tutorial_explore_1 ),
                getString( R.string.tutorial_explore_2 ),
                getString( R.string.tutorial_explore_3 )
        };

        return messages;
    }

    @Override
    protected void onSaveInstanceState ( Bundle outState ) {
        super.onSaveInstanceState( outState );

        outState.putInt( KEY_PAGE_NR, viewPager.getCurrentItem() );
    }
}