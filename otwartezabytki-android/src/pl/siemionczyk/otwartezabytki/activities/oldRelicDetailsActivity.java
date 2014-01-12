package pl.siemionczyk.otwartezabytki.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;
import android.widget.TextView;

import pl.siemionczyk.otwartezabytki.R;
import pl.siemionczyk.otwartezabytki.fragment.RelicDetailsFragment;
import pl.siemionczyk.otwartezabytki.helper.MyLog;

import java.lang.reflect.Method;

/**
 * Created by michalsiemionczyk on 19/11/13.
 */
public class oldRelicDetailsActivity extends FragmentActivity {

    private static final String TAG = "oldRelicDetailsActivity";

//    @InjectView( R.id.content_frame ) Fra mLocationLayout;

//    @InjectView( R.id.tv_location_content )
//    TextView mTextView;


    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout._at_relic_details);

        //inject views
//        Views.inject( this);

        getSupportFragmentManager().beginTransaction()
                .replace( R.id.content_frame, new RelicDetailsFragment() )
                .commit();
    }

    private int dpToPx(int dp)
    {
        float density = getApplicationContext().getResources().getDisplayMetrics().density;
        return Math.round((float)dp * density);
    }

    private static void expand(final View v) {
        //v.getPaddingBottom()

        v.measure( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int targtetHeight = v.getMeasuredHeight();
        MyLog.i( TAG, "targetedHeight:" + targtetHeight );
        MyLog.i( TAG, "heightCurrent:" +         v.getHeight()
        );

//        v.getLayoutParams().height = v.getHeight();
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : (int) ( ((targtetHeight ) * interpolatedTime));

                MyLog.i( TAG, "currentHeight:" + v.getLayoutParams().height );
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(5*  targtetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    private static int measureViewHeight( View view2Expand, View view2Measure ) {
        try {
            Method m = view2Measure.getClass().getDeclaredMethod("onMeasure", int.class, int.class);
            m.setAccessible(true);

            m.invoke(view2Measure,
                    View.MeasureSpec.makeMeasureSpec( view2Expand.getWidth(), View.MeasureSpec.AT_MOST ),
                    View.MeasureSpec.makeMeasureSpec( 0, View.MeasureSpec.UNSPECIFIED ));
        } catch (Exception e) {
            return -1;
        }

        int measuredHeight = view2Measure.getMeasuredHeight();
        return measuredHeight;
    }

    static public void expandOrCollapse( View view2Expand, View view2Measure,
                                         int collapsedHeight) {
        if (view2Expand.getHeight() < collapsedHeight)
            return;

        int measuredHeight = measureViewHeight(view2Expand, view2Measure);

        if (measuredHeight < collapsedHeight)
            measuredHeight = collapsedHeight;

        final int startHeight = view2Expand.getHeight();
        final int finishHeight = startHeight <= collapsedHeight ?
                measuredHeight : collapsedHeight;

        view2Expand.startAnimation(new ExpandAnimation(view2Expand, startHeight, finishHeight));
    }

    private static class ExpandAnimation  extends Animation {
        private final View _view;
        private final int _startHeight;
        private final int _finishHeight;

        public ExpandAnimation( View view, int startHeight, int finishHeight ) {
            _view = view;
            _startHeight = startHeight;
            _finishHeight = finishHeight;
            setDuration(220);
        }

        @Override
        protected void applyTransformation( float interpolatedTime, Transformation t ) {
            final int newHeight = (int)((_finishHeight - _startHeight) * interpolatedTime + _startHeight);
            _view.getLayoutParams().height = newHeight;
            _view.requestLayout();
        }

        @Override
        public void initialize( int width, int height, int parentWidth, int parentHeight ) {
            super.initialize(width, height, parentWidth, parentHeight);
        }

        @Override
        public boolean willChangeBounds( ) {
            return true;
        }
    };
}
