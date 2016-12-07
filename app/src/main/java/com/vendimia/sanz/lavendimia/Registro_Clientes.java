package com.vendimia.sanz.lavendimia;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sanzlibrary1_0_1.AsynResponse;
import com.example.sanzlibrary1_0_1.ServerRequest;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Random;

public class Registro_Clientes extends AppCompatActivity implements AsynResponse{


    String clave;
    TextView clav,fech;
    EditText nombre,AP,AM,rfc;
    Button saved;
    GlobalSetGet GSG = GlobalSetGet.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_clientes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        clav = (TextView) findViewById(R.id.claveC);

        nombre = (EditText) findViewById(R.id.RC_name);
        AP = (EditText) findViewById(R.id.RC_Ap);
        AM = (EditText) findViewById(R.id.RC_Am);
        rfc = (EditText) findViewById(R.id.RC_RFC);

        saved = (Button) findViewById(R.id.save);

       rfc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (rfc.getText().toString().matches("[A-Z]{4}[0-9]{6}[A-Z0-9]{3}") && s.length() > 0) {
                    saved.setVisibility(View.VISIBLE);
                } else {
                    rfc.setError("RFC invalido.");
                    saved.setVisibility(View.INVISIBLE);
                }
            }


        });

       String[] fecha =GSG.getFecha().split("-");
      fech = (TextView) findViewById(R.id.fecha);
        fech.setText("Fecha: "+fecha[0] + "/" + fecha[1] + "/" + fecha[2]);
        clave = GSG.getFinalCliente();
        clav.append(" "+clave);

    }


    public void alert(){

        AlertDialog alert = null;
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Esta seguro de salir de la pantalla actual?")
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
                }).setTitle("Alerta!").setIcon(android.R.drawable.ic_menu_report_image).create();
        alert = builder.create();
        alert.show();
    }

    public void registrarCliente(View v){
        String nom = nombre.getText().toString();
        String App = AP.getText().toString();
        String Apm = AM.getText().toString();
        String rf = rfc.getText().toString();
        if(nom.equals("") || App.equals("") || Apm.equals("") || rf.equals("")){
            Toast.makeText(this, "No es posible continuar, hay campos vacios.", Toast.LENGTH_SHORT).show();
            if(nom.equals("")){nombre.setError("Campo obligatorio!");}
            if(App.equals("")){AP.setError("Campo obligatorio!");}
            if(Apm.equals("")){AM.setError("Campo obligatorio!");}
            if(rf.equals("")){rfc.setError("Campo obligatorio!");}
        }else{
            HashMap postData = new HashMap();
            ServerRequest ser = new ServerRequest(this, this);
            postData.put("nombre",nom);
            postData.put("App",App);
            postData.put("Apm",Apm);
            postData.put("RFC",rf);
            ser.setSendData(postData);
            ser.execute(GSG.getURL()+"registro_clientes.php");
        }

    }

    public void close(View v){
        alert();
    }
    @Override
    public void onBackPressed()
    {
        alert();
    }

    public void postInternetCheck(Boolean aBoolean) {

    }

    @Override
    public void postServeRequest(String s) {
        String st[] = s.split("-");
      switch (st[0]){
          case "0":
              nombre.setText("");
              Toast.makeText(this, "Bien Hecho. El cliente ha sido registrado correctamente", Toast.LENGTH_SHORT).show();
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
