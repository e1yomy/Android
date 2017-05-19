package elyo.my.trackids;


import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class Principal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        Mapa.OnFragmentInteractionListener,
        ListaHijos.OnFragmentInteractionListener,
        MisUbicaciones.OnFragmentInteractionListener,
        Registro.OnFragmentInteractionListener,
        IniciarSesion.OnFragmentInteractionListener

{

    public static Context c;
    public static byte pantalla=1;
    static SharedPreferences preferences;
    static int sesion=0; //0: ninguna 1:sesion 2: Facebook
    static Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
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
        fragmentManager = getSupportFragmentManager();
        try{
            navigationView.getMenu().getItem(0).setChecked(true);

            preferences.edit().putInt("sesion",0).commit();
            if(preferences.getInt("sesion",0)!=0)
            {
                toolbar.setVisibility(View.VISIBLE);
                //txtNombreUsuario.setText(preferences.getString("headernombreusuario","Nombre"));
                //txtEmailUsuario.setText(preferences.getString("headercorreo","alguien@example.com"));
                //RecuperarDatosDeUsuario();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_principal,new Mapa()).commit();
            }
            else
            {
                toolbar.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.content_principal,new IniciarSesion()).commit();

            }

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
        //Servicio servicio = new Servicio(c);

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
    static FragmentTransaction transaction;
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        switch (id)
        {
            case R.id.nav_inicio:
                pantalla=1;
                PantallaMapa();
                break;
            case R.id.nav_hijos:
                pantalla=2;
                PantallaHijos();
                break;
            case R.id.nav_agregarhijo:
                pantalla=3;
                break;
            case R.id.nav_misubicaciones:
                pantalla=4;
                PantallaMisLugares();
                break;
            case R.id.nav_salir:
                finish();
                break;



        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public static void PantallaInicioDeSesion(){
        toolbar.setVisibility(View.GONE);
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content_principal,new IniciarSesion());
        transaction.commit();
    }
    public static void PantallaMisLugares(){
        toolbar.setVisibility(View.VISIBLE);
        toolbar.setTitle("Mis lugares");
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content_principal,new MisUbicaciones());
        transaction.commit();
    }
    public static void PantallaRegistro(){
        try {
            toolbar.setVisibility(View.VISIBLE);

            toolbar.setTitle("Registro");
            transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.content_principal, new Registro());
            transaction.commit();
        }
        catch (Exception ex)
        {
            String e=ex.getMessage();
        }
    }
    public static void PantallaMapa(){
        toolbar.setVisibility(View.VISIBLE);
        toolbar.setTitle("Mi mapa");
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content_principal,new Mapa());
        transaction.commit();
    }
    public static void PantallaHijos(){
        toolbar.setVisibility(View.VISIBLE);
        toolbar.setTitle("Lista de hijos");
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content_principal,new ListaHijos());
        transaction.commit();
    }


}
