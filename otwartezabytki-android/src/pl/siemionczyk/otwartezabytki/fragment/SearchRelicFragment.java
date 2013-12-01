package pl.siemionczyk.otwartezabytki.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import pl.siemionczyk.otwartezabytki.R;

/**
 * Created by majkeliusz on 7/7/13.
 */
public class SearchRelicFragment extends Fragment {

    public static String TAG = "SearchRelicFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View containerView = inflater.inflate(R.layout._ft_screen_wyszukaj,
                container, false);


        return containerView;
    }









}
