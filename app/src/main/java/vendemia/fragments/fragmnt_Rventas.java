package vendemia.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vendimia.sanz.lavendimia.R;
import com.vendimia.sanz.lavendimia.UpdateActionBarTitleFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class fragmnt_Rventas extends Fragment {


    public fragmnt_Rventas() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mListener != null) {
            mListener.onFragmentInteraction("Registro de Ventas");
        }
        return inflater.inflate(R.layout.fragmnt_rventas, container, false);
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

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(String title);
    }


}
