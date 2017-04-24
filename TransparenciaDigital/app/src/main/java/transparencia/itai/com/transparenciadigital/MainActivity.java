package transparencia.itai.com.transparenciadigital;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        Splash.OnFragmentInteractionListener,
        Sesion.OnFragmentInteractionListener,
        NuevaSolicitudAcceso.OnFragmentInteractionListener,
        NuevaSolicitudRecurso.OnFragmentInteractionListener,
        NuevaSolicitudDenuncia.OnFragmentInteractionListener,
        MisSolicitudes.OnFragmentInteractionListener,
        Registro.OnFragmentInteractionListener,
        SujetosObligados.OnFragmentInteractionListener

{

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            drawer.openDrawer(GravityCompat.START);
            //super.onBackPressed();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    static FragmentManager fragmentManager; //Administrador de fragmentos
    FragmentTransaction fragmentTransaction;
    static boolean sesion=false;
    static Context c; //Variable de Contexto para mostrar Toast
    static Toolbar toolbar;  //Para modificar las opr de titulo
    static DrawerLayout drawer;
    static MenuItem misDatos, cerrarSesion;
    static SharedPreferences preferences;
    static NavigationView navigationView;
    static TextView txtNombreUsuario, txtEmailUsuario,txtNoSolicitudes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header= navigationView.getHeaderView(0);

        txtNombreUsuario= (TextView)header.findViewById(R.id.txtNombreUsuario);
        txtEmailUsuario= (TextView)header.findViewById(R.id.txtEmailUsuario);
        txtNoSolicitudes= (TextView)header.findViewById(R.id.txtNoSolicitudes);

        preferences= getSharedPreferences("preferencias",Context.MODE_PRIVATE);
        c=this;
        try{
            //navigationView.getMenu().getItem(0).setChecked(true);
            getSupportFragmentManager().beginTransaction().replace(R.id.content_principal,new Splash()).commit();
            toolbar.setVisibility(View.GONE);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    if(preferences.getBoolean("sesion",false))
                    {
                        toolbar.setVisibility(View.VISIBLE);
                        navigationView.getMenu().getItem(0).setChecked(true);
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_principal, new MisSolicitudes()).commit();
                    }
                    else
                    {
                        toolbar.setVisibility(View.GONE);
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_principal, new Sesion()).commit();
                    }
                }
            },2000);

            fragmentManager=getSupportFragmentManager();
        }
        catch (Exception ex)
        {
            Toast.makeText( c, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        fragmentTransaction=fragmentManager.beginTransaction();
        if(!preferences.getBoolean("sesion",false))
        {
            //fragmentManager.beginTransaction().replace(R.id.content_principal,new Sesion()).commit();

            if(id==R.id.nav_salir) {
                finish();
            }

        }
        else
        {
            if (id == R.id.nav_missolicitudes) {
                //Listado de solicitudes del usuario
                fragmentTransaction.replace(R.id.content_principal, new MisSolicitudes()).commit();
                navigationView.getMenu().findItem(id).setChecked(true);
            }else if (id == R.id.nav_sujetosobligados) {
                // Handle the camera action
                fragmentTransaction.replace(R.id.content_principal,new SujetosObligados()).commit();
                navigationView.getMenu().findItem(id).setChecked(true);
            }else if (id == R.id.nav_acceso) {
                //Solicitar acceso a informacion
                fragmentTransaction.replace(R.id.content_principal, new NuevaSolicitudAcceso()).commit();
                navigationView.getMenu().findItem(id).setChecked(true);

            } else if (id == R.id.nav_denuncia) {
                //Solicitar recurso de revision
                fragmentTransaction.replace(R.id.content_principal, new NuevaSolicitudDenuncia()).commit();
                navigationView.getMenu().findItem(id).setChecked(true);

            } else if(id==R.id.nav_registro){
                fragmentTransaction.replace(R.id.content_principal, new Registro()).commit();
            }

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    private void QuitarSeleccionMenu() {
        for(byte i=0;i<navigationView.getMenu().size();i++){
            navigationView.getMenu().getItem(i).setChecked(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //return true;
        }
        else if(id==R.id.action_misdatos){
            QuitarSeleccionMenu();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_principal, new Registro()).commit();

        } else if(id==R.id.action_cerrarsesion){
            preferences.edit().putBoolean("sesion",false).commit();
            HabilitarMenu(preferences.getBoolean("sesion",false));
            QuitarSeleccionMenu();

            txtNombreUsuario.setText("");
            txtEmailUsuario.setText("");
            txtNoSolicitudes.setText("");

            getSupportFragmentManager().beginTransaction().replace(R.id.content_principal, new Sesion()).commit();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        misDatos=menu.getItem(1);
        cerrarSesion=menu.getItem(2);
        HabilitarMenu(preferences.getBoolean("sesion",false));

        return true;
    }

    public static void HabilitarMenu(boolean boo)
    {
        misDatos.setEnabled(boo);
        cerrarSesion.setEnabled(boo);
    }
    static byte ini=0; //Puede sustituirse por un boolean

    //Funcion que se encarga de verificar que la cuenta que se ha ingresado sea valida
    //
    public static void IniciarSesion(final String cuenta, final String contra){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Conexion conexion = new Conexion();
                    if(conexion.IniciarSesion(cuenta,contra)==1) {
                        toolbar.setVisibility(View.GONE);
                        ini=1;
                    }
                }
                catch (Exception ex)
                {
                    String s= ex.getMessage();
                }
            }
        }).start();
        try
        {
            //Se asigna un tiempo de espera hasta que la conexion y verificacion de datos haya terminado
            //De no haber devuelto resultado favorable en cinco segundos, el proceso termina.
            int tiempo=0;
            while(ini!=1) {
                Thread.sleep(100);
                tiempo+=100;
                if(tiempo>5000) {
                    //Mensaje de que no se encuentra el usuario
                    break;
                }
            }
        } catch (InterruptedException e) {e.printStackTrace();}

        if(ini==1)
        {

            preferences.edit().putBoolean("sesion", true).commit();
            HabilitarMenu(preferences.getBoolean("sesion", false));
            navigationView.getMenu().getItem(0).setChecked(true);
            txtNombreUsuario.setText(Conexion.listaDeDatos.get(4)+" "+ Conexion.listaDeDatos.get(5) + " "+  Conexion.listaDeDatos.get(5) );
            txtEmailUsuario.setText("alguien@ejemplo.com");
            txtNoSolicitudes.setText("20" + " " + "solicitudes realizadas");
            fragmentManager.beginTransaction().replace(R.id.content_principal, new MisSolicitudes()).commit();
            ini=0;
        }
    }

}
