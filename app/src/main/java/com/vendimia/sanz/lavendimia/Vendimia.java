package com.vendimia.sanz.lavendimia;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


import com.example.sanzlibrary1_0_1.AsynResponse;
import com.example.sanzlibrary1_0_1.ServerRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import vendemia.fragments.fragmnt_Artiuclos;
import vendemia.fragments.fragmnt_Clientes;
import vendemia.fragments.fragmnt_Configuracion;
import vendemia.fragments.fragmnt_Ventas;
import vendemia.fragments.startFragment;

public class Vendimia extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,UpdateActionBarTitleFragment.OnFragmentInteractionListener,AsynResponse {
    //variable para el manejo de los fragmentos
    FragmentManager manager = getSupportFragmentManager();
    //varibles de la libreria sanz para manejarlas en los distintos metodos
    HashMap postData;
    ServerRequest petecion;
    String URL ="http://chali23.000webhostapp.com/Vendemia/";
    String JSONCliente,JSONProduct,fecha;
    //variables json
    JSONObject jsonObject = null;
    GlobalSetGet GSG = GlobalSetGet.getInstance();
    Fragment clientes ;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendimia);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


      manager.beginTransaction().replace(R.id.fragment_start,new startFragment()).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        alldata();
        //susitui cuando hagas el config
        if(getIntent().getStringExtra("data") != null) {
            String data = getIntent().getStringExtra("data");
            try {
                JSONObject date = new JSONObject(data);
                GSG.setTasaF(date.getString("tasa"));
                GSG.setEnganche(date.getString("enganche"));
                GSG.setpMaximo(date.getString("plazoM"));
                Log.d("Confi", date.toString());
            } catch (JSONException e) {
                e.getMessage();
            }
        }
    }
    public void alldata(){
         GSG.setURL(URL);
        postData = new HashMap();
        petecion = new ServerRequest(this, this);
        postData.put("action", "all");
        petecion.setSendData(postData);
        try {
        petecion.execute(URL + "autocomplete.php");
            Log.d("xd",URL+"autocomplete.php");
    }catch (Exception e){
        Log.d("error cl",e.getMessage());
    }


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.vendimia, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        //para el cambio de fragmentos

        //para cambair el titulo del barr
        String title = "";

        switch (id) {
            case R.id.nav_sales:

                manager.beginTransaction()
                        .replace(R.id.fragment_start,
                                new fragmnt_Ventas(),
                                "ventas").commit();

                title = "Ventas";

                break;
            case R.id.nav_client:
                manager.beginTransaction()
                        .replace(R.id.fragment_start,
                                new fragmnt_Clientes(),
                                "clientes").commit();

                title = "Clientes";

                break;
            case R.id.nav_articles:
                manager.beginTransaction()
                        .replace(R.id.fragment_start,
                                new fragmnt_Artiuclos(),
                                "articulos").commit();

                title = "Articulos";

                break;

            case R.id.nav_settings:
                manager.beginTransaction()
                        .replace(R.id.fragment_start,
                                new fragmnt_Configuracion(),
                                "configurarion").commit();

                title = "Configuracion";

                break;

        }
        getSupportActionBar().setTitle(title);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onFragmentInteraction(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void postInternetCheck(Boolean aBoolean) {

    }

    @Override
    public void postServeRequest(String s) {
        Log.d("resquest",s);
        try {
            jsonObject = new JSONObject(s);
            JSONCliente = jsonObject.getString("jsonclient");
            GSG.setJSONClientes(JSONCliente);
            JSONProduct = jsonObject.getString("jsonproduct");
            GSG.setJSONProductos(JSONProduct);
            fecha = jsonObject.getString("fecha");
            GSG.setFecha(fecha);

        } catch (Exception e) {
            Log.d("error servidor",e.getMessage());
        }

    }
}
