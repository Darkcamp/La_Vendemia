package vendemia.fragments;


import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sanzlibrary1_0_1.ReadAndWriteData;
import com.example.sanzlibrary1_0_1.SaveSharedPreference;
import com.vendimia.sanz.lavendimia.GlobalSetGet;
import com.vendimia.sanz.lavendimia.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;


/**
 * A simple {@link Fragment} subclass.
 */
public class fragmnt_Configuracion extends Fragment {
    GlobalSetGet GSG = GlobalSetGet.getInstance();
    EditText taza,eng,plazo;
    View miView;
    Button save,cancel;
    String pMa;
    String a,b,c;
    int tag = 0;
    ReadAndWriteData rw = new ReadAndWriteData(getContext());

    public static fragmnt_Configuracion newInstance(){
        fragmnt_Configuracion fragment = new fragmnt_Configuracion();
        return fragment;
    }

    public fragmnt_Configuracion() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        miView = inflater.inflate(R.layout.fragmnt_configuracion, container, false);
        taza = (EditText) miView.findViewById(R.id.Tfina);
        save = (Button) miView.findViewById(R.id.guardar_config);
        eng = (EditText) miView.findViewById(R.id.engacheconfi);
        plazo=(EditText) miView.findViewById(R.id.plazoconfig);
        taza.setText(GSG.getTasaF());
        eng.setText(GSG.getEnganche());
        plazo.setText(GSG.getpMaximo());
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveconfi();
            }
        });
        return miView;
    }


    public void saveconfi(){
        String tasa = taza.getText().toString();
        String enga = eng.getText().toString();
        String plazom = plazo.getText().toString();

        if(tasa.equals("") || enga.equals("") || plazom.equals("")){
            Toast.makeText(getActivity(), "No es posible continuar, hay campos vacios.", Toast.LENGTH_SHORT).show();
            if(tasa.equals("")){taza.setError("Campo obligatorio!");}
            if(enga.equals("")){eng.setError("Campo obligatorio!");}
        }else{
            try {
                JSONObject obj = new JSONObject();
                obj.put("tasa", tasa);
                obj.put("enganche", enga);
                obj.put("plazoM", plazom);
                GSG.setTasaF(tasa);
                GSG.setpMaximo(plazom);
                GSG.setEnganche(enga);
                plazo.setText(plazom);
                rw.Write(obj.toString(),"data.Vendimia");
                SaveSharedPreference.setUserName(getActivity(), "ventas");
                Toast.makeText(getActivity(), "Bien Hecho, La configuración ha sido registrada", Toast.LENGTH_SHORT).show();
            }catch (JSONException e ){e.getMessage();}
        }

    }

}
