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
public class FavouriteRelicsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//

        View view = inflater.inflate(R.layout._ft_screen_favorite,
                container, false);


        return view;
    }
}
