package pl.siemionczyk.otwartezabytki.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import pl.siemionczyk.otwartezabytki.BundleKeys;
import pl.siemionczyk.otwartezabytki.R;
import pl.siemionczyk.otwartezabytki.activities.MainActivity;
import pl.siemionczyk.otwartezabytki.activities.MapActivity;
import pl.siemionczyk.otwartezabytki.helper.HelperToolkit;
import pl.siemionczyk.otwartezabytki.rest.RelicJsonWrapper;
import pl.siemionczyk.otwartezabytki.rest.relicjson.RelicJson;

/**
 * Created by michalsiemionczyk on 14/12/13.
 */
public class RelicsDetailsPagerFragment extends Fragment {



    ViewPager mPager;

    ScreenSlidePagerAdapter mPagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout._ft_relic_details_pager, container, false);

        //get wrapper from bundle
        if ( !getArguments().containsKey(BundleKeys.KEY_BUNDLE_RELICS_WRAPPER) || !getArguments().containsKey( BundleKeys.KEY_BUNDLE_RELIC_POSITION)){
            throw new UnsupportedOperationException( "This fragment should be provied bundle with relics!");
        }
        RelicJsonWrapper relicsW = ( RelicJsonWrapper) getArguments().getSerializable( BundleKeys.KEY_BUNDLE_RELICS_WRAPPER);
        int currentRelics = getArguments().getInt(  BundleKeys.KEY_BUNDLE_RELIC_POSITION);



        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) rootView.findViewById(R.id.relics_pager);
        mPagerAdapter = new ScreenSlidePagerAdapter( getFragmentManager(), relicsW.relics);
        mPager.setAdapter(mPagerAdapter);

        mPager.setCurrentItem( currentRelics);

        //set activity title
        getActivity().setTitle( R.string.title_single_relic_details);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_relic_details, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if ( item.getItemId() == R.id.action_map){


            //show just a single relic map activity
            int i = mPager.getCurrentItem();
            RelicJson relic = mPagerAdapter.getRelics().get( i);

            Intent intent = new Intent( getActivity(), MapActivity.class);
            intent.putExtra(BundleKeys.KEY_BUNDLE_SINGLE_RELIC_JSON, relic);

            startActivity( intent);

            return true;

        }

        return super.onOptionsItemSelected(item);
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
            b.putSerializable( BundleKeys.KEY_BUNDLE_SINGLE_RELIC_JSON, mRelics.get( position));
            f.setArguments( b );

            return f;
        }

        @Override
        public int getCount() {
            return mRelics.size();
        }
         public ArrayList<RelicJson> getRelics(){
            return this.mRelics;
        }
    }

}
