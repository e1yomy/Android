package elyo.my.trackids;


import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class Principal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        Mapa.OnFragmentInteractionListener,
        ListaHijos.OnFragmentInteractionListener,
        MisUbicaciones.OnFragmentInteractionListener

{

    public static Context c;
    public static byte padre;
    public static byte pantalla=1;
    static SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        c=this;
        preferences= getSharedPreferences("preferencias",Context.MODE_PRIVATE);
        try{
            navigationView.getMenu().getItem(0).setChecked(true);
            getSupportFragmentManager().beginTransaction().replace(R.id.content_principal,new Mapa()).commit();
        }
        catch (Exception ex)
        {
            Toast.makeText( c, ex.getMessage(), Toast.LENGTH_SHORT).show();
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
        getMenuInflater().inflate(R.menu.principal, menu);
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



    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    static FragmentManager fragmentManager;
    FragmentTransaction transaction;
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        switch (id)
        {
            case R.id.nav_inicio:
                pantalla=1;
                transaction.replace(R.id.content_principal,new Mapa());
                break;
            case R.id.nav_hijos:
                pantalla=2;
                transaction.replace(R.id.content_principal,new ListaHijos());
                break;
            case R.id.nav_agregarhijo:
                pantalla=3;
                break;
            case R.id.nav_misubicaciones:
                pantalla=4;
                transaction.replace(R.id.content_principal,new MisUbicaciones());
                break;
            case R.id.nav_salir:
                break;



        }
        transaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
