package vendemia.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.vendimia.sanz.lavendimia.R;
import com.vendimia.sanz.lavendimia.UpdateActionBarTitleFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class fragmnt_Ventas extends Fragment implements View.OnClickListener {


    public fragmnt_Ventas() {
        // Required empty public constructor
    }
   Button btn_n_venta;

     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
       View miVista = inflater.inflate(R.layout.fragmnt_ventas,container,false);
         btn_n_venta = (Button)miVista.findViewById(R.id.btn_nventa);
         btn_n_venta.setOnClickListener(this);
        return miVista;
    }


    @Override
    public void onClick(View view) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        fragmnt_Rventas llf = new fragmnt_Rventas();
        ft.replace(R.id.fragment_start, llf);
        ft.commit();

    }

}
