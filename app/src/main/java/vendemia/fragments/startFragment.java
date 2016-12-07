package vendemia.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vendimia.sanz.lavendimia.R;

/**
 * Created by sanz on 2/12/16.
 */

public class startFragment extends Fragment{

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fview= inflater.inflate(R.layout.fragment_start, container, false);
                return fview;
    }



}
