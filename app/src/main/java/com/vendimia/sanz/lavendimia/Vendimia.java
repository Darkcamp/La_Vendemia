package com.vendimia.sanz.lavendimia;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import dbvendimia.bdVendimia;
import vendemia.fragments.fragmnt_Rventas;
import vendemia.fragments.fragmnt_Ventas;
import vendemia.fragments.startFragment;

public class Vendimia extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,UpdateActionBarTitleFragment.OnFragmentInteractionListener {
    //variable para el manejo de los fragmentos
    FragmentManager manager = getSupportFragmentManager();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendimia);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        usuarios();

      manager.beginTransaction().replace(R.id.fragment_start,new startFragment()).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void usuarios() {
            bdVendimia bd = new bdVendimia(this);
            bd.insertClient("juan","colin","duran","qwert1234qwe");
           bd.insertClient("mario","suñiga","duran","qwerty1235jh");
           bd.insertProduct("xdxdf","sillaop", (float) 123.45,3);
        bd.insertProduct("holi","sillaops", (float) 123.45,3);

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
                                new fragmnt_Ventas(),
                                "clientes").commit();

                title = "Clientes";

                break;
            case R.id.nav_articles:
                manager.beginTransaction()
                        .replace(R.id.fragment_start,
                                new fragmnt_Ventas(),
                                "articulos").commit();

                title = "Articulos";

                break;

            case R.id.nav_settings:
                manager.beginTransaction()
                        .replace(R.id.fragment_start,
                                new fragmnt_Ventas(),
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
}
