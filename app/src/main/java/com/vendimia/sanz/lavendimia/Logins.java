package com.vendimia.sanz.lavendimia;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sanzlibrary1_0_1.ReadAndWriteData;
import com.example.sanzlibrary1_0_1.SaveSharedPreference;
import com.kosalgeek.genasync12.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

/**
 * Created by sanz on 6/12/16.
 * ok esta es una idea mas, al igua si el cliente que ingrea en la la compra no existe
 * deplejar la ventana de agregar cliente vale.
 */

public class Logins extends AppCompatActivity {
    GlobalSetGet GSG = GlobalSetGet.getInstance();
    public static JSONObject jsonData;
    public EditText tsFi,enga,plazos;
    String pMa;
    public Button nx;
    public Boolean bdatos = false;
    ReadAndWriteData rw = new ReadAndWriteData(this);
    private boolean displayMsg = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (SaveSharedPreference.getUserName(Logins.this).length() == 0) {
            SetUp();
        } else
        {
            try{
                jsonData = new JSONObject(rw.Read("data.Vendimia"));
                Intent itent = new Intent(this, Vendimia.class);
                itent.putExtra("data", jsonData.toString());
                startActivity(itent);
                finish();
            }
            catch (Exception e){
                e.printStackTrace();
                SaveSharedPreference.clearUserName(this);
                Toast.makeText(this, "Error inesperado.", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }
    private void SetUp() {
        setContentView(R.layout.primero_pasos);

        nx = (Button)findViewById(R.id.guardar_configs);
        nx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configure();
            }
        });



        try {
            jsonData = new JSONObject(rw.Read("data.Vendimia"));
        } catch (Exception e) {
            e.printStackTrace();
            if(displayMsg) {
                displayMsg = false;
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                TextView msg = new TextView(this);
                msg.setText("Bienvenido a La Vendimia Movil.\n" +
                        " esta aplicación le ayudara a manejar sus productos." +
                        "para ello es nesesario que configure algunas opciones." +
                        "si decide no configurar el sistema se configurara por defecto " +
                        "tenga encuenta, que puede modificar estas configuraciones mas adelante. ");
                msg.setTextSize(20);
                msg.setGravity(Gravity.CENTER_HORIZONTAL);
                AlertDialog.Builder builder = alertDialogBuilder
                        .setTitle("La Vendimia")
                        .setView(msg)
                        .setCancelable(true)
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                JSONObject obj = new JSONObject();
                                try {
                                    obj.put("tasa", "2.8");
                                    obj.put("enganche", "20");
                                    obj.put("plazoM", "12");
                                    GSG.setTasaF("2.8");
                                    GSG.setEnganche("20");
                                    GSG.setpMaximo("12");
                                    rw.Write(obj.toString(),"data.Vendimia");
                                   iniciar();
                                    displayMsg=true;
                                    dialog.dismiss();
                                } catch (JSONException e1) {
                                    e1.printStackTrace();
                                }


                            }
                        })
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                bdatos = true;
                                displayMsg = true;
                                dialog.dismiss();

                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
    }



}

    private void iniciar() {
        Intent itent = new Intent(this, Vendimia.class);

        itent.putExtra("data", rw.Read("data.Vendimia"));
        startActivity(itent);
        finish();
    }

    private void configure() {
        tsFi = (EditText)findViewById(R.id.Tfinas);
        plazos = (EditText)findViewById(R.id.plazoconfigs);
        enga = (EditText)findViewById(R.id.engacheconfis);
        String tasa = tsFi.getText().toString();
        String engan = enga.getText().toString();
        String pla = plazos.getText().toString();

        if(tasa.equals("") || engan.equals("") || pla.equals("")){

            if(tasa.equals("")){
                tsFi.setError("No deje campos de texto en blanco");
            }else if(engan.equals("")){
                enga.setError("No deje campos de texto en blanco");
            }
            else if(pla.equals("")){
                plazos.setError("No deje campos de texto en blanco");
            }
        }else{
            GSG.setTasaF(tasa);
            GSG.setEnganche(engan);
            GSG.setpMaximo(pla);
            try {
                JSONObject obj = new JSONObject();
                obj.put("tasa", tasa);
                obj.put("enganche", engan);
                obj.put("plazoM", pla);
                rw.Write(obj.toString(),"data.Vendimia");
                SaveSharedPreference.setUserName(this, "ventas");
               iniciar();
            }catch(JSONException e){Log.d("json", e.getMessage());}

        }

    }


}
