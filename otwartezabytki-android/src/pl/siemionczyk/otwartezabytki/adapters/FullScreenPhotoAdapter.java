package pl.siemionczyk.otwartezabytki.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.androidquery.AQuery;

import pl.siemionczyk.otwartezabytki.rest.OtwarteZabytkiClient;

/**
 * Created by michalsiemionczyk on 09/12/13.
 * Generic adapters
 */
public class FullScreenPhotoAdapter extends PagerAdapter {

    Context mContext;

    int mPagesNr;

    /** Ids of drawables for consequent pages, should be provided in constructor*/
    String[] mPhotoUrls;

    public FullScreenPhotoAdapter( Context context, int pagesNr, String[] photosUrls ){
        this.mContext = context;
        this.mPagesNr = pagesNr;
        this.mPhotoUrls = photosUrls;

    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position)
    {
        AQuery aq = new AQuery( collection);

        String photoUrl = mPhotoUrls [ position];

        ImageView imageVIew = new ImageView( mContext );

        aq.id( imageVIew).image( OtwarteZabytkiClient.HOST + photoUrl);

        collection.addView( imageVIew, 0 );

        return imageVIew;
    }
    @Override
    public void destroyItem(ViewGroup collection, int position, Object view)
    {
        collection.removeView((View ) view);
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == ((View) arg1);
    }

    @Override
    public int getCount() {
        return this.mPagesNr;
    }
}