package vendemia.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;

import com.vendimia.sanz.lavendimia.R;
import com.vendimia.sanz.lavendimia.UpdateActionBarTitleFragment;


public class fragmnt_Rventas extends Fragment {


    public fragmnt_Rventas() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (mListener != null) {
            mListener.onFragmentInteraction("Registro de Ventas");
        }
        View mmivista= inflater.inflate(R.layout.fragmnt_rventas, container, false);
        return mmivista;
    }
    private UpdateActionBarTitleFragment.OnFragmentInteractionListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (UpdateActionBarTitleFragment.OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


}
