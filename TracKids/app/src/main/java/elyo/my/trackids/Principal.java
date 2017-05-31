package elyo.my.trackids;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import com.facebook.login.LoginManager;

public class Principal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        Mapa.OnFragmentInteractionListener,
        ListaHijos.OnFragmentInteractionListener,
        MisUbicaciones.OnFragmentInteractionListener,
        Registro.OnFragmentInteractionListener,
        IniciarSesion.OnFragmentInteractionListener,
        IngresarClaves.OnFragmentInteractionListener

{

    public static Context c;
    public static byte pantalla=1;
    static SharedPreferences preferences;
    static int sesion=0; //0: ninguna 1:sesion 2: Facebook
    static Toolbar toolbar;
    static Usuario usuario;
    public static Handler responseHandler=null;
    static ProgressDialog p;
    Servicio servicio ;
    Menu menuPuntos;
    static MenuItem pin, correo;
    static NavigationView navigationView;
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

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        c=this;
        preferences= getSharedPreferences("preferencias",Context.MODE_PRIVATE);
        fragmentManager = getSupportFragmentManager();
        p=new ProgressDialog(c);
        servicio= new Servicio(c);

        try{
            navigationView.getMenu().getItem(0).setChecked(true);

            if(preferences.getInt("sesion",0)!=0)
            {
                toolbar.setVisibility(View.VISIBLE);
                //txtNombreUsuario.setText(preferences.getString("headernombreusuario","Nombre"));
                //txtEmailUsuario.setText(preferences.getString("headercorreo","alguien@example.com"));
                if(preferences.getInt("sesion",0)!=0) {
                    CargarUsuario();

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.content_principal,new Mapa()).commit();
            }
            else
            {
                toolbar.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.content_principal,new IniciarSesion()).commit();

            }
            responseHandler= new Handler(){
                public void handleMessage(Message msg)
                {
                    super.handleMessage(msg);
                    try
                    {
                        p.dismiss();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            };

        }
        catch (Exception ex)
        {
            //Toast.makeText( c, ex.getMessage(), Toast.LENGTH_SHORT).show();
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
        try {
            getMenuInflater().inflate(R.menu.principal, menu);
            //menuPuntos=menu;
            pin = menu.findItem(R.id.nav_pin);
            correo = menu.findItem(R.id.nav_correo);
            correo.setEnabled(false);
            pin.setEnabled(false);
            if (preferences.getInt("sesion", 0) != 0) {
                correo.setTitle("Usuario: " + usuario.usuario);
                pin.setTitle("Pin: " + usuario.pin);
            }
        }
        catch (Exception e)
        {
            Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id)
        {
            case R.id.nav_cerrarsesion:
                try {
                    preferences.edit().putInt("sesion", 0).commit();
                    LoginManager.getInstance().logOut();
                    PantallaInicioDeSesion();
                }
                catch (Exception ex)
                {

                }
                break;
        }
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
        if(preferences.getInt("sesion",0)!=0)
        {
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
                    PantallaAgregarHijo();
                    pantalla=3;
                    break;
                case R.id.nav_misubicaciones:
                    pantalla=4;
                    PantallaMisLugares();
                    break;
            }
            if(id==R.id.nav_salir) {
                pantalla=1;
                finish();
            }
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private static void QuitarSeleccionMenu() {
        for(byte i=0;i<navigationView.getMenu().size();i++){
            navigationView.getMenu().getItem(i).setChecked(false);
        }
    }
    public static void PantallaInicioDeSesion(){
        toolbar.setVisibility(View.GONE);
        transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.entrada,R.anim.salida);
        transaction.replace(R.id.content_principal,new IniciarSesion());
        transaction.commit();
        QuitarSeleccionMenu();
    }
    public static void PantallaMisLugares(){
        toolbar.setVisibility(View.VISIBLE);
        toolbar.setTitle("Mis lugares");
        transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.entrada,R.anim.salida);
        transaction.replace(R.id.content_principal,new MisUbicaciones());
        transaction.commit();
        QuitarSeleccionMenu();
        navigationView.getMenu().findItem(R.id.nav_misubicaciones).setChecked(true);
    }
    public static void PantallaRegistro(){
        try {
            toolbar.setVisibility(View.VISIBLE);
            toolbar.setTitle("Registro");
            transaction = fragmentManager.beginTransaction();
            transaction.setCustomAnimations(R.anim.entrada,R.anim.salida);
            transaction.replace(R.id.content_principal, new Registro());
            transaction.commit();
            QuitarSeleccionMenu();
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
        transaction.setCustomAnimations(R.anim.entrada,R.anim.salida);
        transaction.replace(R.id.content_principal,new Mapa());
        transaction.commit();
        QuitarSeleccionMenu();
        navigationView.getMenu().findItem(R.id.nav_inicio).setChecked(true);
    }
    public static void PantallaHijos(){
        toolbar.setVisibility(View.VISIBLE);
        toolbar.setTitle("Lista de hijos");
        transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.entrada,R.anim.salida);
        transaction.replace(R.id.content_principal,new ListaHijos());
        transaction.commit();
        QuitarSeleccionMenu();
        navigationView.getMenu().findItem(R.id.nav_hijos).setChecked(true);
    }
    public static void PantallaAgregarHijo(){
        try {
            toolbar.setVisibility(View.VISIBLE);
            toolbar.setTitle("Agregar hijo");
            transaction = fragmentManager.beginTransaction();
            transaction.setCustomAnimations(R.anim.entrada,R.anim.salida);
            transaction.replace(R.id.content_principal, new IngresarClaves());
            transaction.commit();
            QuitarSeleccionMenu();
            navigationView.getMenu().findItem(R.id.nav_agregarhijo).setChecked(true);
        }
        catch (Exception ex)
        {
            String s=ex.getMessage();
        }
    }




    public static void Mensaje(){
        p.setIndeterminate(true);
        p.setCancelable(false);
        p.setCanceledOnTouchOutside(false);
        p.setTitle("Espere");
        p.setMessage("Verificando sus credenciales");
        p.show();
    }
    public static void GuardarCuenta(final String nombres, final String apellidos, final String correo, final String contrasena, final String telefono, final String pin) {
        preferences.edit().putBoolean("exito", false).commit();
        Mensaje();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    ServiciosWeb sw = new ServiciosWeb();
                    if (sw.GuardarCuenta(nombres, apellidos, correo, contrasena, telefono, pin) == 1) {
                        preferences.edit().putBoolean("exito", true).commit();
                    }
                    responseHandler.sendEmptyMessage(0);
                } catch (Exception ex) {
                    String s = ex.getMessage();
                }
            }
        });
        t.start();

    }

    public static void ExisteCuenta(final String email, final String contra)
    {
        preferences.edit().putBoolean("procesoFinalizado",false).commit();
        preferences.edit().putBoolean("existe", false).commit();
        Thread t =new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ServiciosWeb sw = new ServiciosWeb();
                    if (sw.ExisteCuenta(email, contra) == 1) {
                        preferences.edit().putBoolean("existe", true).commit();
                    }
                    preferences.edit().putBoolean("procesoFinalizado",true).commit();
                }
                catch (Exception ex)
                {
                    String s=ex.getMessage();
                }
            }
        });
        t.start();
    }
    public static void CrearRegistroUltimaUbicacion(){
        preferences.edit().putBoolean("exito",false).commit();
        Mensaje();
        Thread t =new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    ServiciosWeb sw = new ServiciosWeb();
                    if (sw.CrearRegistroUltimaUbicacion(preferences.getString("idUsuario","-1")) == 1) {
                        preferences.edit().putBoolean("exito", true).commit();
                    }
                }
                catch (Exception ex)
                {
                    String s=ex.getMessage();
                }
            }
        });
        t.start();
    }

    public void CargarUsuario() {
        usuario = new Usuario(
                preferences.getString("correoUsuario", ""),
                preferences.getString("nombresUsuario", ""),
                preferences.getString("apellidosUsuario", ""),
                preferences.getString("telefonoUsuario", ""),
                preferences.getString("contrasenaUsuario", ""),
                preferences.getString("pinUsuario", "")
        );

    }
    public static void AgregarHijo(final String email,final String pin)
    {
        preferences.edit().putBoolean("existe", false).commit();
        Thread t =new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    ServiciosWeb sw = new ServiciosWeb();
                    if (sw.ExisteUsuario(email, pin) == 1) {
                        preferences.edit().putBoolean("existe", true).commit();
                    }
                    preferences.edit().putBoolean("procesoFinalizado",true).commit();
                }
                catch (Exception ex)
                {
                    String s=ex.getMessage();
                }
            }
        });
        t.start();
    }
    public static void CargarUbicacion(final String id, final double lat, final double lan, final String fecha)
    {
        Thread t =new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    ServiciosWeb sw = new ServiciosWeb();
                    if (sw.CargarUbicacion(id,lat,lan,fecha) == 1) {
                    }
                    preferences.edit().putBoolean("procesoFinalizado",true).commit();
                }
                catch (Exception ex)
                {
                    String s=ex.getMessage();

                }
                try {
                    this.finalize();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });
        t.start();
    }
    public static void CargarUltimaUbicacion(final String id, final double lat, final double lan)
    {
        Thread t =new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    ServiciosWeb sw = new ServiciosWeb();
                    if (sw.ActualizarRegistroUltimaUbicacion(id,lat,lan) == 1) {
                    }
                }
                catch (Exception ex)
                {
                    String s=ex.getMessage();

                }
                try {
                    this.finalize();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });
        t.start();
    }

    public static void ListaDeHijos(final String id)
    {
        preferences.edit().putBoolean("existe", false).commit();
        Thread t =new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ServiciosWeb sw = new ServiciosWeb();
                    if (sw.ListaDeHijos(id) == 1) {
                        preferences.edit().putBoolean("existe", true).commit();
                    }
                }
                catch (Exception ex)
                {
                    String s=ex.getMessage();
                }
            }
        });
        t.start();
    }
    public static void UltimaRutaConocida(final String id)
    {
        preferences.edit().putBoolean("existe", false).commit();
        Thread t =new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ServiciosWeb sw = new ServiciosWeb();
                    if(sw.UltimasUbicacionesHijo(id)==1)
                    {
                        preferences.edit().putBoolean("existe", true).commit();
                    }
                }
                catch (Exception ex)
                {
                    String s=ex.getMessage();
                }
            }
        });
        t.start();
    }
}
