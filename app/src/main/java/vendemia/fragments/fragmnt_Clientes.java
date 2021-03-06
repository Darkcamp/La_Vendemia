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
import com.vendimia.sanz.lavendimia.Edit_usuario;
import com.vendimia.sanz.lavendimia.GlobalSetGet;
import com.vendimia.sanz.lavendimia.R;
import com.vendimia.sanz.lavendimia.Registro_Clientes;
import com.vendimia.sanz.lavendimia.CardViewClientes;
import com.vendimia.sanz.lavendimia.cardViewDistribute;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



public class fragmnt_Clientes extends Fragment implements AsynResponse {
    //instcia clases
    GlobalSetGet GSG = GlobalSetGet.getInstance();
    List<cardViewDistribute> result;
    View miView;
    RecyclerView recList;
    cardViewDistribute cvd = new cardViewDistribute();
    AsyncTaskRunner myTask;

    public fragmnt_Clientes() {
        // Required empty public constructor
    }

    public static fragmnt_Clientes newInstance() {
        fragmnt_Clientes fragment = new fragmnt_Clientes();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        miView = inflater.inflate(R.layout.activity_clientes, container, false);

        FloatingActionButton fab = (FloatingActionButton) miView.findViewById(R.id.fabCliente);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nuevaVenta = new Intent(getActivity(), Registro_Clientes.class);
                startActivity(nuevaVenta);
            }
        });
        return miView;
    }

    public void response() {
        //pedimos al servidor que nos de la lista de clientes
        ServerRequest ser = new ServerRequest(getActivity(), this);
        ser.execute(GSG.getURL() + "clientes.php");

    }
    //envio del json al cardview para que los adapte 1 por 1
    private List<cardViewDistribute> createList(int size, String json) {
        String JSONCliente, JSONclient;
        JSONObject jsonObject = null;
        int var1;
        try {
            //metodo para sacar el id del ultimo cliente registrado y asi sumarle uno al proximo que se registr
            //esto solo es visual por que la bd esta configurada con un auto increment
            jsonObject = new JSONObject(json);
            JSONclient = jsonObject.getString("id_client");
            JSONArray foli = new JSONArray(JSONclient);
            for (int i = 0; i < foli.length(); i++) {
                JSONObject folit = foli.getJSONObject(i);
                var1 = (Integer.parseInt(folit.getString("id_client"))+1);
                GSG.setFinalCliente(""+var1);
            }
           //a qui se pasan cada item al card adapter
            jsonObject = new JSONObject(json);
            JSONCliente = jsonObject.getString("jsonclient");
            JSONArray jsonarray = new JSONArray(JSONCliente);
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobject = jsonarray.getJSONObject(i);
                cardViewDistribute cvd = new cardViewDistribute();//sin esta se creara un bucle del ultimo cliente
                cvd.clave_cliente = jsonobject.getString("id_client");
                String nombre = jsonobject.getString("nombre") + " "
                        + jsonobject.getString("apellidoP")
                        + " " + jsonobject.getString("apellidoM");
                cvd.nombre_cliente = nombre;
                cvd.JsonCl = jsonobject.toString();
                result.add(cvd);
            }
        } catch (JSONException e) {
            Log.d("Clientes CVD JSon", e.getMessage());
        }
        return result;
    }
//se inicializa que recive se usara
    public void initializeRecyclerView(String s) {
        recList = (RecyclerView) miView.findViewById(R.id.rvAlertas);
        recList.setHasFixedSize(true);
        result = new ArrayList<>();
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        CardViewClientes ca = new CardViewClientes(createList(100, s));
        recList.setAdapter(ca);
        RecyclerView.ItemAnimator animator = recList.getItemAnimator();
        animator.setAddDuration(1000);
        animator.setRemoveDuration(500);
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
    @Override
    public void postInternetCheck(Boolean aBoolean) {
    }

    @Override
    public void postServeRequest(String s) {
        if (s.equals("")) {
            Toast.makeText(getActivity(), "No hay respuesta del servidor", Toast.LENGTH_LONG).show();
        } else {
            initializeRecyclerView(s);
            myTask = new AsyncTaskRunner();
            myTask.execute("0");
        }
    }


    //tarea para recuperar los clientes, y estar ala espera del onclick para modificar o visulizar el cliente.
    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
        Handler handler = new Handler();

        @Override
        protected String doInBackground(String... params) {

            try {
                int time = Integer.parseInt(params[0]);
               //para saber el tiempoq ue esta dormida la atarea
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
                if(GSG.getTagC() == 1) {
                try{
                    Intent edit = new Intent(getActivity(),Edit_usuario.class);
                    edit.putExtra("data", GSG.getJSONClientes());
                    startActivity(edit);
                    GSG.setTagC(0);}catch (Exception e){
                    Toast.makeText(getActivity(),"Ubo un error",Toast.LENGTH_LONG);

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


