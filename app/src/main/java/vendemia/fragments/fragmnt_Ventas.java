package vendemia.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sanzlibrary1_0_1.AsynResponse;
import com.example.sanzlibrary1_0_1.ServerRequest;
import com.vendimia.sanz.lavendimia.GlobalSetGet;
import com.vendimia.sanz.lavendimia.R;
import com.vendimia.sanz.lavendimia.RegistroVentas;
import com.vendimia.sanz.lavendimia.CardViewAdapter;
import com.vendimia.sanz.lavendimia.cardViewDistribute;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class fragmnt_Ventas extends Fragment implements View.OnClickListener,AsynResponse {


    public String JSONdata;
    View miView;
    List<cardViewDistribute> result;
    RecyclerView recList;
    GlobalSetGet GSG = GlobalSetGet.getInstance();
    public static fragmnt_Ventas newInstance(){
        fragmnt_Ventas fragment = new fragmnt_Ventas();
        return fragment;
    }
    public fragmnt_Ventas() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        miView = inflater.inflate(R.layout.fragmnt_ventas,container,false);
        FloatingActionButton fab = (FloatingActionButton) miView.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent newsales = new Intent(getActivity(),RegistroVentas.class);
                startActivity(newsales);
            }
        });
        return miView;
    }
    @Override
    public void onClick(View view) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        fragmnt_Rventas llf = new fragmnt_Rventas();
        ft.replace(R.id.fragment_start, llf);
        ft.commit();

    }

    public void initializeRecyclerView(String s) {
        recList = (RecyclerView) miView.findViewById(R.id.rvAlertas);
        recList.setHasFixedSize(true);
        result = new ArrayList<>();
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        CardViewAdapter ca = new CardViewAdapter(createList(100, s));
        recList.setAdapter(ca);
        RecyclerView.ItemAnimator animator = recList.getItemAnimator();
        animator.setAddDuration(1000);
        animator.setRemoveDuration(500);
    }

    public void response(){
        ServerRequest srv = new ServerRequest(getActivity(), this);
        srv.execute(GSG.getURL() + "ventas_activas.php");
    }


    private List<cardViewDistribute> createList(int size, String json) {
        String JSONCliente,JSONfolio;
        JSONObject jsonObject = null;
        int var1;
        try {
            jsonObject = new JSONObject(json);
            JSONfolio = jsonObject.getString("foliof");
            JSONArray foli = new JSONArray(JSONfolio);
            for (int i = 0; i < foli.length(); i++) {
                JSONObject folit = foli.getJSONObject(i);
                var1 = (Integer.parseInt(folit.getString("folio"))+1);
                GSG.setFinalFolio(""+var1);
            }

            JSONCliente = jsonObject.getString("vactivas");
            JSONArray jsonarray = new JSONArray(JSONCliente);
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobject = jsonarray.getJSONObject(i);
                cardViewDistribute cvd = new cardViewDistribute();
                String[] fecha = jsonobject.getString("Fecha").split(" ");
                cvd.folio = jsonobject.getString("Folios");
                cvd.clave = jsonobject.getString("Codigo_Cliente");
                cvd.total = jsonobject.getString("Total");
                cvd.nombre = jsonobject.getString("Nombre");
                cvd.fecha = fecha[0];
                result.add(cvd);
            }
        }catch(JSONException e){
            Log.d("json",e.getMessage());}


        return result;
    }


    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        response();
    }
    @Override
    public void postInternetCheck(Boolean aBoolean) {

    }

    @Override
    public void postServeRequest(String s) {
        if(s.equals("")){
            Toast.makeText(getActivity(), "Problema al conectar al servidor!.", Toast.LENGTH_LONG).show();
        }else{

            initializeRecyclerView(s);
        }


    }
}
