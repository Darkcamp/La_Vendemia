package com.vendimia.sanz.lavendimia;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sanzlibrary1_0_1.AsynResponse;
import com.example.sanzlibrary1_0_1.ServerRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class RegistroVentas extends AppCompatActivity implements AsynResponse {


    //instancias
    GlobalSetGet GSG = GlobalSetGet.getInstance();
    List<cardViewDistribute> result;

    //variables de layout
    TextView tv_rfc,tv_fecha;
    AutoCompleteTextView cliente, articulo;
    ImageButton btn_add;
    RecyclerView RV_contenido;
    LinearLayout eng,abono,end,next,l3,l6,l9,l12;
    TextView a3,b3,c3,d3,a6,b6,c6,d6,a9,b9,c9,d9,a12,b12,c12,d12,exis;
    RadioButton e3,e6,e9,e12;

    //variables de la clase
    DecimalFormat decimal;
    String JSONClientes,nombre,JSONProducts;
    String[] fecha;
    final List<String> array_cliente = new ArrayList<String>();
    final List<String> array_procut = new ArrayList<String>();
    JSONArray JSA=null,JSA2 = null;
    JSONObject JSO=null, JSO2= null;
    ArrayAdapter<String> adapter=null,adapter2=null;
    Double aun_importe,pMeses,meses3,meses6,meses9,meses12;
    String catidadExistente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_ventas);
        cliente = (AutoCompleteTextView) findViewById(R.id.AutoCTV_Cliente);
        articulo = (AutoCompleteTextView)findViewById(R.id.AutoCTV_articulo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btn_add=(ImageButton)findViewById(R.id.btn_agregar);
        tv_rfc =(TextView) findViewById(R.id.TV_rfc);
        tv_fecha=(TextView) findViewById(R.id.TV_fe);


        autocomplet();
    }
    public void autocomplet(){

        JSONClientes = GSG.getJSONClientes();
        JSONProducts = GSG.getJSONProductos();
        fecha = GSG.getFecha().split("-");
        tv_fecha.setText("Fecha: "+fecha[0] + "/" + fecha[1] + "/" + fecha[2]);

        try {
            JSA = new JSONArray(JSONClientes.toString());
            for(int i=0;i<=JSA.length();i++){
                JSO = JSA.getJSONObject(i);
                array_cliente.add(JSO.getString("nombre")+" "+JSO.getString("apellidoP")+" "+JSO.getString("apellidoM")
                        +"-"+JSO.getString("rfc"));
                adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, array_cliente);
                cliente.setAdapter(adapter);
                cliente.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String str[] = ((TextView)view).getText().toString().split("-");
                        tv_rfc.setText("RFC: "+str[1]);
                        cliente.setText(str[0]);
                        nombre = str[0];
                    }
                });
            }

        }
        catch(JSONException e){
            e.getMessage();

        }

        try {
            JSA2= new JSONArray(JSONProducts.toString());
            for (int l = 0; l <= JSA2.length(); l++) {
                JSO2 = JSA2.getJSONObject(l);
                array_procut.add(JSO2.getString("descripcion"));
                adapter2 = new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, array_procut);
                articulo.setAdapter(adapter2);
                articulo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                });
            }

        }catch(Exception w){Log.d("error en producto",w.getMessage());}

    }

    private List<cardViewDistribute> crearLista(int size, String json){
        result = new ArrayList<>();
                try{

            decimal = new DecimalFormat("0.00");
            JSONObject jsonobject = new JSONObject(json);
            cardViewDistribute cvd = new cardViewDistribute();
            cvd.descripcion = jsonobject.getString("Descripcion");
            GSG.setArticulos(jsonobject.getString("Descripcion"));
            cvd.modelo = jsonobject.getString("Modelo");
            cvd.cantidad = 1;
            cvd.precio = jsonobject.getString("Precio");
            double pre = Double.parseDouble(jsonobject.getString("Precio"));
            double importe = pre * cvd.cantidad;
            cvd.importe = decimal.format(importe) + "";
            aun_importe = importe;
            GSG.setCantidad(aun_importe);
            catidadExistente=jsonobject.getString("Existencia");
            GSG.setCantexistente(catidadExistente);
            result.add(cvd);


        }catch(JSONException e){
            Log.d("json",e.getMessage());}


        return result;
    }
    public void agregarProduct(View v){
        if(cliente.getText().toString().equals("") || articulo.getText().toString().equals("")) {
            if(articulo.getText().toString().equals("")){
                articulo.setError("Campos vacios!");
            }
            if(cliente.getText().toString().equals("")){
                cliente.setError("Campos vacios!");
            }
        }else{
            HashMap postData = new HashMap();
            postData.put("action","consultar");
            postData.put("contenido", articulo.getText().toString());
            ServerRequest sr = new ServerRequest(this,this);
            sr.setSendData(postData);
            sr.execute(GSG.getURL()+"articulos.php");
        }

    }
    public void settable(String result){


        RV_contenido = (RecyclerView) findViewById(R.id.RView_a2);
        RV_contenido.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this.getApplication());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        RV_contenido.setLayoutManager(llm);
        cardViewAlertasAdapter2 cAdapt = new cardViewAlertasAdapter2(crearLista(100,result));
        RV_contenido.setAdapter(cAdapt);
        RecyclerView.ItemAnimator animator = RV_contenido.getItemAnimator();
        animator.setAddDuration(1000);
        animator.setRemoveDuration(500);

    }

    @Override
    public void postInternetCheck(Boolean aBoolean) {

    }

    @Override
    public void postServeRequest(String s) {
        settable(s);


    }

}

