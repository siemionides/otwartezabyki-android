package pl.siemionczyk.otwartezabytki.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import pl.siemionczyk.otwartezabytki.R;
import pl.siemionczyk.otwartezabytki.activities.MainActivity;
import pl.siemionczyk.otwartezabytki.rest.RelicJsonWrapper;
import pl.siemionczyk.otwartezabytki.rest.relicjson.RelicJson;

/**
 * Created by michalsiemionczyk on 14/12/13.
 */
public class RelicsDetailsPagerFragment extends Fragment {

    public final static String KEY_BUNDLE_RELICS = "relicc bundle";
    public final static String KEY_BUNDLE_RELIC_POSITION = "relicc position";


    ViewPager mPager;

    ScreenSlidePagerAdapter mPagerAdapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout._ft_relic_details_pager, container, false);

        //get wrapper from bundle
        if ( !getArguments().containsKey( KEY_BUNDLE_RELICS) || !getArguments().containsKey( KEY_BUNDLE_RELIC_POSITION)){
            throw new UnsupportedOperationException( "This fragment should be provied bundle with relics!");
        }
        RelicJsonWrapper relicsW = ( RelicJsonWrapper) getArguments().getSerializable( KEY_BUNDLE_RELICS);
        int currentRelics = getArguments().getInt(  KEY_BUNDLE_RELIC_POSITION);



        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) rootView.findViewById(R.id.relics_pager);
        mPagerAdapter = new ScreenSlidePagerAdapter( getFragmentManager(), relicsW.relics);
        mPager.setAdapter(mPagerAdapter);

        mPager.setCurrentItem( currentRelics);

        return rootView;
    }


    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        ArrayList<RelicJson> mRelics;


        public ScreenSlidePagerAdapter(FragmentManager fm, ArrayList<RelicJson> relics) {
            super(fm);
            this.mRelics = relics;
        }



        @Override
        public Fragment getItem(int position) {
            RelicDetailsFragment f = new RelicDetailsFragment();
            Bundle b = new Bundle();
            b.putSerializable( MainActivity.FRAGMENT_BUNDLE_RELIC_JSON_KEY, mRelics.get( position));
            f.setArguments( b );

            return f;
        }

        @Override
        public int getCount() {
            return mRelics.size();
        }
    }

}
