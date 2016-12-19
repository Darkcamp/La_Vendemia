package com.vendimia.sanz.lavendimia;


import android.os.Bundle;
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

public class RegistroVentas extends AppCompatActivity implements AsynResponse{
//una de las clases mas relvoltosa

    //instancias
    GlobalSetGet GSG = GlobalSetGet.getInstance();
    List<cardViewDistribute> result;
    formuls fm = new formuls();
    private DatoJson JSONParser = null;
    ServerRequest sr;
    //variables de layout
    TextView tv_rfc,tv_fecha;
    AutoCompleteTextView cliente, articulo;
    ImageButton btn_add;
    RecyclerView RV_contenido;
    LinearLayout engaches,abonos,next,end;
    RadioButton rbtn_3,rbtn_6,rbtn_9,rbtn_12;
    Button save;
    TextView enga_engache,enga_bonificacion,enga_total;
    TextView abonos_d3,abonos_d6,abonos_d9,abonos_d12,pagaa_d3,pagaa_d6,pagaa_d9,pagaa_d12,ahorra_3,ahorra_6,ahorra_9,ahorra_12;
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
    double engache,bonifiacion_engache,total;
    double aux_engache,aux_tasita,aux_importe;
    int aux_pm;
    //globales ne la clase
    double total_adeudo,precio_contado;
    double radio_meses;
    double tpaga3,tpaga6,tpaga9,tpaga12;


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
       //layaoust
        engaches = (LinearLayout) findViewById(R.id.ly_enganche);
        abonos =(LinearLayout) findViewById(R.id.abonos);
        end =(LinearLayout) findViewById(R.id.YL_finabtn);
        next = (LinearLayout) findViewById(R.id.ly_next);
        save = (Button) findViewById(R.id.lyf_save);
        save.setEnabled(false);
        TextView folventa = (TextView)findViewById(R.id.fol);
        folventa.append(" "+GSG.getFinalFolio());



        autocomplet();
    }
    public void autocomplet(){
 /// este metodo uso para ambos auto complet, para facilitarme lo del rfc se lo añadi al cliente con un "-"
        // de esta amnera lo estraijo mas rapido
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
        //desde aqui se empieza con los productos
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
        double d ;
//no vamos ala parte de cadview donde se pone el picio dle artiuclo selecionado
                try{

            decimal = new DecimalFormat("0.00");
            JSONObject jsonobject = new JSONObject(json);
            cardViewDistribute cvd = new cardViewDistribute();
            cvd.descripcion = jsonobject.getString("Descripcion");
            GSG.setArticulos(jsonobject.getString("Descripcion"));
            cvd.modelo = jsonobject.getString("Precio"); //cambiar depues a Modelo, pro precio y viseversa.
            cvd.cantidad = 1;
            cvd.precio = jsonobject.getString("Modelo");//co
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
            sr= new ServerRequest(this,this);
            sr.setSendData(postData);
            sr.execute(GSG.getURL()+"articulos.php");
        }

    }
    public void settable(String result){
//llamamos ala tabla para crealar y creale una lista de 100 items (como reserva d mmrai


        RV_contenido = (RecyclerView) findViewById(R.id.RView_a2);
        RV_contenido.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this.getApplication());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        RV_contenido.setLayoutManager(llm);
        CardViewNuevaVenta cAdapt = new CardViewNuevaVenta(crearLista(100,result));
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
        String st[] = s.split("-");
         if(st[0].equals("0")){
             Toast.makeText(this, "Bien Hecho, Tu venta ha sido registrada correctamente", Toast.LENGTH_LONG).show();

             finish();
         }else if(st[0].equals("1")) {
             Toast.makeText(this, "El artículo seleccionado no cuenta con existencia, favor de verificar.", Toast.LENGTH_SHORT).show();
         }else if(st[0].equals("2")) {
             Toast.makeText(this, "Error inesperado", Toast.LENGTH_SHORT).show();
         }
         else if(st[0].equals("")) {
             Toast.makeText(this, "Error en el servidor", Toast.LENGTH_SHORT).show();
         }
         else {
            settable(s);
            postAddprodcut();
        }



    }
    public void reload(View v){
  // para volver a cargar los precios , usamos este emtodo para que cuando cambien la cantida se modifiquen.
        postAddprodcut();
    }
    public void postAddprodcut(){
      //metodos para agregar el producto
        decimal = new DecimalFormat("0.00");
        engaches.setVisibility(View.VISIBLE);
        next.setVisibility(View.VISIBLE);
        //tv-lyaout engache
        enga_bonificacion = (TextView)findViewById(R.id.lyEng_rvrengache);
        enga_total = (TextView)findViewById(R.id.lyEng_rv_bengache);
        enga_engache = (TextView)findViewById(R.id.lyEng_rv_total);
        aux_engache =Double.parseDouble(GSG.getEnganche());
        aux_importe = GSG.getCantidad();
        aux_tasita = Double.parseDouble(GSG.getTasaF());
        aux_pm = Integer.parseInt(GSG.getpMaximo());
         engache = fm.engache(aux_engache,aux_importe);
        bonifiacion_engache = fm.bonificacionEn(engache,aux_tasita,aux_pm);
        total = fm.totalAdeudo(aux_importe,engache,bonifiacion_engache);
        total_adeudo = total;
        enga_total.setText(String.valueOf(bonifiacion_engache));//2,3,1
        enga_bonificacion.setText(String.valueOf(engache));
        enga_engache.setText(String.valueOf(total));
    }
    public void next(View v){
// este metodo es para incializar los item de cunaod le des siguientes y donde se hacen las formulas
             DecimalFormat decimal = new DecimalFormat("0.00");
             abonos.setVisibility(View.VISIBLE);
             next.setVisibility(View.INVISIBLE);
             end.setVisibility(View.VISIBLE);
             aux_engache = Double.parseDouble(GSG.getEnganche());
             aux_importe = GSG.getCantidad();
             aux_tasita = Double.parseDouble(GSG.getTasaF());
             aux_pm = Integer.parseInt(GSG.getpMaximo());
             abonos_d3 = (TextView) findViewById(R.id.Ab_resu3);
             abonos_d6 = (TextView) findViewById(R.id.Ab_resu6);
             abonos_d9 = (TextView) findViewById(R.id.Ab_resu9);
             abonos_d12 = (TextView) findViewById(R.id.Ab_resu12);
             pagaa_d3 = (TextView) findViewById(R.id.ab_pagart3);
             pagaa_d6 = (TextView) findViewById(R.id.ab_pagart6);
             pagaa_d9 = (TextView) findViewById(R.id.ab_pagart9);
             pagaa_d12 = (TextView) findViewById(R.id.ab_pagart12);
             ahorra_3 = (TextView) findViewById(R.id.ahorra_3);
             ahorra_6 = (TextView) findViewById(R.id.ahorra_6);
             ahorra_9 = (TextView) findViewById(R.id.ahorra_9);
             ahorra_12 = (TextView) findViewById(R.id.ahorra_12);
             precio_contado = fm.preciocontado(total_adeudo, aux_tasita, aux_pm);
             tpaga3 = fm.total_pagar(precio_contado, aux_tasita, 3);
             tpaga6 = fm.total_pagar(precio_contado, aux_tasita, 6);
             tpaga9 = fm.total_pagar(precio_contado, aux_tasita, 9);
             tpaga12 = fm.total_pagar(precio_contado, aux_tasita, 12);
             pagaa_d3.append(" " + tpaga3);
             pagaa_d6.append(" " + tpaga6);
             pagaa_d9.append(" " + tpaga9);
             pagaa_d12.append(" " + tpaga12);
             abonos_d3.setText("$" + decimal.format(tpaga3 / 3));
             abonos_d6.setText("$" + decimal.format(tpaga3 / 6));
             abonos_d12.setText("$" + decimal.format(tpaga3 / 9));
             abonos_d9.setText("$" + decimal.format(tpaga3 / 12));
             ahorra_3.append(" $" + decimal.format(total_adeudo - tpaga3));
             ahorra_6.append(" $" + decimal.format(total_adeudo - tpaga6));
             ahorra_9.append(" $" + decimal.format(total_adeudo - tpaga9));
             ahorra_12.append(" $" + decimal.format(total_adeudo - tpaga12));
             rbtn_3 = (RadioButton) findViewById(R.id.ab_rbtn3);
             rbtn_6 = (RadioButton) findViewById(R.id.ab_rbtn6);
             rbtn_9 = (RadioButton) findViewById(R.id.ab_rbtn9);
             rbtn_12 = (RadioButton) findViewById(R.id.ab_rbtn12);
         }


    public void Cancelar(View v){
        abonos.setVisibility(View.INVISIBLE);
        next.setVisibility(View.INVISIBLE);
        engaches.setVisibility(View.INVISIBLE);
        end.setVisibility(View.INVISIBLE);

        settable("");
    }
    public void radib(View v){
       //para saber que button tenemos check o a cuantos meses queremos pagar
        boolean ifcheck = ((RadioButton) v).isChecked();
        save.setEnabled(true);
        // cual tienes check
        switch(v.getId()) {

            case R.id.ab_rbtn3:
                if(ifcheck)
                    rbtn_6.setChecked(false);
                    rbtn_9.setChecked(false);
                    rbtn_12.setChecked(false);
                radio_meses=tpaga3;
                break;
            case R.id.ab_rbtn6:
                if(ifcheck)
                    rbtn_3.setChecked(false);
                    rbtn_9.setChecked(false);
                    rbtn_12.setChecked(false);
                radio_meses=tpaga6;
                break;
            case R.id.ab_rbtn9:
                if(ifcheck)
                    rbtn_3.setChecked(false);
                    rbtn_6.setChecked(false);
                    rbtn_12.setChecked(false);
                radio_meses=tpaga9;
                break;
            case R.id.ab_rbtn12:
                if(ifcheck)
                    rbtn_3.setChecked(false);
                    rbtn_6.setChecked(false);
                    rbtn_9.setChecked(false);
                radio_meses=tpaga12;
                break;
        }

    }
    public void guardarventa(View v){
  //para sacar la venta. ok aqui fue un error mio, pues al crear el texview d RFC yo agrego rfc : desde codigo
        //por lo tanto tengo que separalo por la misma palabras
        String sentrfc = tv_rfc.getText().toString();
        String srfc[] = sentrfc.split("RFC: ");

        int ex = Integer.parseInt(GSG.getCantexistente())-1;
        String art = GSG.getArticulos();
        String deuda =String.valueOf(radio_meses);
        String clienten = GSG.getClientes();

        if(sentrfc.equals("") || art.equals("") ){
            //msg aqui...

        }else{
            HashMap postData = new HashMap();
            sr= new ServerRequest(this,this);
            postData.put("rfc", srfc[1]);
            //postData.put("fecha",fecha);
            postData.put("articulo", art);
            postData.put("existencia",""+ex);
            postData.put("deuda", deuda);
            sr.setSendData(postData);
            try{
                sr.execute(GSG.getURL()+"ventas.php");
            }
            catch (Exception e){
            Log.d("error al eviar",e.getMessage());}
        }


    }

}

