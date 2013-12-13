//package pl.siemionczyk.otwartezabytki;
//
//import android.app.AlertDialog;
//import android.content.Context;
//import android.graphics.drawable.Drawable;
//
//import com.google.android.maps.ItemizedOverlay;
//import com.google.android.maps.OverlayItem;
//
//import java.util.ArrayList;
//
///**
// * Created by michalsiemionczyk on 11/12/13.
// */
//public class RelicsItemizedOverlay extends ItemizedOverlay<OverlayItem> {
//
//    private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
//
//    private Context mContext;
//
//    public RelicsItemizedOverlay(Drawable drawable) {
//        super(boundCenterBottom(drawable));
//    }
//
//    public RelicsItemizedOverlay(Drawable defaultMarker, Context context) {
//        super(boundCenterBottom(defaultMarker));
//        mContext = context;
//    }
//
//    @Override
//    protected OverlayItem createItem(int i) {
//        return mOverlays.get(i);
//    }
//
//    public void addOverlay(OverlayItem overlay) {
//        mOverlays.add(overlay);
//        populate();
//    }
//
//
//    @Override
//    public int size() {
//        return mOverlays.size();
//    }
//
//    @Override
//    protected boolean onTap(int index) {
//        OverlayItem item = mOverlays.get(index);
//        AlertD  ialog.Builder dialog = new AlertDialog.Builder(mContext);
//        dialog.setTitle(item.getTitle());
//        dialog.setMessage(item.getSnippet());
//        dialog.show();
//        return true;
//    }
//}
