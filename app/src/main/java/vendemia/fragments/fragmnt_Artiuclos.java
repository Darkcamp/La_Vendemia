package vendemia.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vendimia.sanz.lavendimia.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class fragmnt_Artiuclos extends Fragment {


    public fragmnt_Artiuclos() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragmnt_artiuclos, container, false);
    }

}