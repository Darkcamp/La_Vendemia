package com.vendimia.sanz.lavendimia;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sanzlibrary1_0_1.AsynResponse;
import com.example.sanzlibrary1_0_1.ServerRequest;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Random;

public class Edit_articulo extends AppCompatActivity implements AsynResponse{

    String clave;
    TextView clav,fech;
    EditText des,precio,modelo,existencia;
    Button saved;
    GlobalSetGet GSG = GlobalSetGet.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_articulo);
        des = (EditText) findViewById(R.id.RA_DsArt);
        precio = (EditText) findViewById(R.id.RA_precio);
        modelo = (EditText) findViewById(R.id.RA_modelo);
        existencia = (EditText) findViewById(R.id.RA_existe);
        clav = (TextView) findViewById(R.id.claveA);
        saved = (Button) findViewById(R.id.guardadito);
        String[] fecha =GSG.getFecha().split("-");
        fech = (TextView) findViewById(R.id.fecha);
        fech.setText("Fecha: "+fecha[0] + "/" + fecha[1] + "/" + fecha[2]);
        clave = GSG.getFinalProduct();
        try{
       //     clav.append(" "+clave);
        }catch (Exception e){
            Log.d("articulos error",e.getMessage());

        }
    //

        String data = getIntent().getStringExtra("data");
       try {
            JSONObject date = new JSONObject(data);
            existencia.setText(date.getString("Existencia").toString());
            des.setText(date.getString("Descripcion"));
            precio.setText(date.getString("Precio"));
            modelo.setText(date.getString("Modelo"));
            Log.d("json", date.toString());
           clave = date.getString("id_Productos");
            clav.setText("clave:"+clave);

        } catch (JSONException e) {
            e.getMessage();
        }


    }


    public void alert(){

        AlertDialog alert = null;
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Esta seguro de salir?")
                .setCancelable(false)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        finish();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                }).setTitle("Alerta!").setIcon(android.R.drawable.ic_dialog_alert).create();
        alert = builder.create();
        alert.show();
    }

    public void guardararticuloaa(View v){
        String desc = des.getText().toString();
        String pre = precio.getText().toString();
        String mod = modelo.getText().toString();
        String exis = existencia.getText().toString();
        if(des.equals("") || pre.equals("") || mod.equals("") || exis.equals("") || exis.equals("0")){
            Toast.makeText(this, "No es posible continuar, hay campos vacios.", Toast.LENGTH_SHORT).show();
            if(desc.equals("")){des.setError("Campo obligatorio!");}
            if(pre.equals("")){precio.setError("Campo obligatorio!");}
            if(mod.equals("")){modelo.setError("Campo obligatorio!");}
            if(exis.equals("")){existencia.setError("Campo obligatorio!");}
            if(exis.equals("0")){existencia.setError("No puede agregar articulo sin existencia!");}
        }else{
            HashMap postData = new HashMap();
            try {
                //llamo mi libreria y acedo ala clase server resquest apra enviar parametros atraves del metodo post
                ServerRequest ser = new ServerRequest(this, this);
                postData.put("Descripcion", desc);
                postData.put("Modelo", pre);
                postData.put("Precio", mod);
                postData.put("Existencia", exis);
                postData.put("id",clave);
                ser.setSendData(postData);
                ser.execute(GSG.getURL() + "modificar_articulo.php");
            }catch(Exception e){
                Log.d("asdE",e.getMessage());
            }
        }

    }

    @Override
    public void onBackPressed()
    {
        alert();
    }

    public void close(View v){
        alert();
    }


    @Override
    public void postInternetCheck(Boolean aBoolean) {

    }

    @Override
    public void postServeRequest(String s) {
        String st[] = s.split("-");
        switch (st[0]){
            case "0":

                Toast.makeText(this, "Bien Hecho. El Articulo  ha sido actualizado correctamente", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case "1":
                Toast.makeText(this, "Error inesperado", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this, "Sin respuesta del servidor", Toast.LENGTH_SHORT).show();
                break;
        }

    }
}
