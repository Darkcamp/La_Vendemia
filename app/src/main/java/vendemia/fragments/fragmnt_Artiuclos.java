package vendemia.fragments;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sanzlibrary1_0_1.AsynResponse;
import com.example.sanzlibrary1_0_1.ServerRequest;
import com.vendimia.sanz.lavendimia.CardViewArticulo;
import com.vendimia.sanz.lavendimia.Edit_articulo;
import com.vendimia.sanz.lavendimia.GlobalSetGet;
import com.vendimia.sanz.lavendimia.R;
import com.vendimia.sanz.lavendimia.cardViewDistribute;
import com.vendimia.sanz.lavendimia.registro_Articulo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class fragmnt_Artiuclos extends Fragment implements AsynResponse{
    GlobalSetGet GSG = GlobalSetGet.getInstance();
    List<cardViewDistribute> result;
    View myView;
    RecyclerView recList;
    AsyncTaskRunner myTask;
    public static fragmnt_Artiuclos newInstance(){
        fragmnt_Artiuclos fragment = new fragmnt_Artiuclos();
        return fragment;
    }

    public fragmnt_Artiuclos() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragmnt_artiuclos, container, false);
        FloatingActionButton fab = (FloatingActionButton) myView.findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent nuevaVenta = new Intent(getActivity(),registro_Articulo.class);
                startActivity(nuevaVenta);
            }
        });
        return myView;
    }

    public void response(){
      //pedimo al servidor la lista de articulos
        ServerRequest ser = new ServerRequest(getActivity(), this);
        ser.execute(GSG.getURL()+"articulo.php");
    }
    @Override
    public void onResume() {
        super.onResume();
        response();
    }
    @Override
    public void onStop() {
        super.onStop();
        myTask.cancel(true);
    }
//al igual que el apartdo de lcientes, aqui se hace un pequeño truco para extraer el id del cliente ultimo
    //y mandar al adapter los items correspondientes
    private List<cardViewDistribute> createList(int size, String json) {
        String JSONCliente,fecha,JSONProduct;
        JSONObject jsonObject = null;
        int var1;
        try {
            jsonObject = new JSONObject(json);
            JSONProduct = jsonObject.getString("id_client");
            JSONArray foli = new JSONArray(JSONProduct);
            for (int i = 0; i < foli.length(); i++) {
                JSONObject folit = foli.getJSONObject(i);
                var1 = (Integer.parseInt(folit.getString("id_client"))+1);
                GSG.setFinalProducto(""+var1);
            }
            jsonObject = new JSONObject(json);
            JSONCliente = jsonObject.getString("jsonclient");
            JSONArray jsonarray = new JSONArray(JSONCliente);
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobject = jsonarray.getJSONObject(i);
                cardViewDistribute ci = new cardViewDistribute();
                ci.clave_articulo = jsonobject.getString("id_Productos");
                ci.descripcionArt = jsonobject.getString("Descripcion");
                ci.JsonAr = jsonobject.toString();
                result.add(ci);
            }
        }catch(JSONException e){
            Log.d("json",e.getMessage());}


        return result;
    }

    public void initializeRecyclerView(String s) {
        recList = (RecyclerView) myView.findViewById(R.id.rvAlertas);
        recList.setHasFixedSize(true);
        result = new ArrayList<>();
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        CardViewArticulo ca = new CardViewArticulo(createList(100,s));
        recList.setAdapter(ca);
        RecyclerView.ItemAnimator animator = recList.getItemAnimator();
        animator.setAddDuration(1000);
        animator.setRemoveDuration(500);
    }

    @Override
    public void postInternetCheck(Boolean aBoolean) {

    }
    @Override
    public void postServeRequest(String s) {
        Log.d("asycTask",s);
        if(s.equals("")){
            Toast.makeText(getActivity(), "No hay respuesta del servidor", Toast.LENGTH_LONG).show();
        }else{
            initializeRecyclerView(s);
            myTask = new AsyncTaskRunner();
            myTask.execute("0");

        }
    }
    //tarea para recuperar los artiuclos y esperamos el onclick para lansar modificar articulos
    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
        Handler handler = new Handler();

        @Override
        protected String doInBackground(String... params) {
            // publishProgress("Sleeping..."); // Calls onProgressUpdate()
            Log.d("task", "execute in back ground");
            try {
                // Do your long operations here and return the result
                int time = Integer.parseInt(params[0]);
                // Sleeping for given time period
                Thread.sleep(0);
                resp = "Slept for " + time + " milliseconds";
                handler.postDelayed(r, 300);

            } catch (InterruptedException e) {
                Log.d("task", "error:" + e.getMessage());
                resp = e.getMessage();
            } catch (Exception e) {
                Log.d("task", "errorE:" + e.getMessage());
                resp = e.getMessage();
            }
            return resp;
        }

        final Runnable r = new Runnable() {
            public void run() {
                if(GSG.getTc2() == 1) {
                    try{
                        Intent edit = new Intent(getActivity(),Edit_articulo.class);
                        edit.putExtra("data", GSG.getJSONProductos());
                        startActivity(edit);
                        GSG.setTag2(0);}catch (Exception e){
                        Log.d("error ",e.getMessage());

                    }
                }
                Log.d("task", "Task is running");
                handler.postDelayed(this, 300);

            }
        };

        @Override
        protected void onPostExecute(String result) {

            Log.d("task", "execute on Post");

        }

        protected void onPreExecute() {
            // Things to be done before execution of long running operation. For
            // example showing ProgessDialog
            Log.d("task", "execute on pre");
        }

        @Override
        protected void onProgressUpdate(String... text) {
            Log.d("task", "execute on Update");

        }
    }

}
