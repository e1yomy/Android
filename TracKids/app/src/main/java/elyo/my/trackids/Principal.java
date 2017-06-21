package elyo.my.trackids;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
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

import org.json.JSONObject;

import static elyo.my.trackids.ListaHijos.ActualizarLista;
import static elyo.my.trackids.ListaHijos.b;
import static elyo.my.trackids.ListaHijos.listaHijos;
import static elyo.my.trackids.ListaHijos.lv;
import static elyo.my.trackids.Mapa.DibujarRuta;
import static elyo.my.trackids.Registro.campos;

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
    static View parentLayout;
    static Principal pr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        pr=Principal.this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        parentLayout = findViewById(R.id.content_principal);
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
                CargarUsuario();
                switch (pantalla)
                {
                    case 1:
                        PantallaMapa();
                        break;
                    case 2:
                        PantallaHijos();
                        break;
                    case 3:
                        PantallaAgregarHijo();
                        break;
                    case 4:
                        PantallaMisLugares();
                        break;
                }
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
            //super.onBackPressed();
            drawer.openDrawer(GravityCompat.START);
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
                    correo.setTitle("Usuario");
                    pin.setTitle("Pin");
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
        if(id==R.id.nav_salir) {
            pantalla=1;
            finish();
        }
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
                    pantalla=3;
                    PantallaAgregarHijo();
                    break;
                case R.id.nav_misubicaciones:
                    pantalla=4;
                    PantallaMisLugares();
                    break;
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
        toolbar.post(new Runnable() {
            @Override
            public void run() {
                toolbar.setTitle("Mis lugares");
            }
        });
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
            toolbar.post(new Runnable() {
                @Override
                public void run() {
                    toolbar.setTitle("Registro");
                }
            });
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
        toolbar.post(new Runnable() {
            @Override
            public void run() {
                toolbar.setTitle("Mi mapa");
            }
        });
        transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.entrada,R.anim.salida);
        transaction.replace(R.id.content_principal,new Mapa());
        transaction.commit();
        QuitarSeleccionMenu();
        navigationView.getMenu().findItem(R.id.nav_inicio).setChecked(true);
    }
    public static void PantallaHijos(){
        toolbar.setVisibility(View.VISIBLE);
        toolbar.post(new Runnable() {
            @Override
            public void run() {
                toolbar.setTitle("Lista de hijos");
            }
        });
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
            toolbar.post(new Runnable() {
                @Override
                public void run() {
                    toolbar.setTitle("Agregar hijo");
                }
            });
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

    /*public static void Mensaje(){
        p.setIndeterminate(true);
        p.setCancelable(false);
        p.setCanceledOnTouchOutside(false);
        p.setTitle("Espere");
        p.setMessage("Verificando sus credenciales");
        p.show();
    }*/
    public static void GuardarCuenta(final String nombres, final String apellidos, final String correo, final String contrasena, final String telefono, final String pin)
    {
        preferences.edit().putBoolean("exito", false).commit();
        //Mensaje();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    ServiciosWeb sw = new ServiciosWeb();
                    if (sw.GuardarCuenta(nombres, apellidos, correo, contrasena, telefono, pin) == 1) {
                        preferences.edit().putBoolean("exito", true).commit();
                        /////////////
                        if (preferences.getInt("sesion", -1) == 3) {
                            pantalla = 1;
                            //cargarDatos De usuario en preferences
                            ExisteCuenta(campos.get(2).getText().toString(),preferences.getString("contrasenaUsuario",""));
                            preferences.edit().putInt("sesion", 2).commit();
                            PantallaMapa();
                            Snackbar.make(parentLayout,"Cuenta creada.",Snackbar.LENGTH_SHORT).show();

                        }
                        else {
                            if (preferences.getBoolean("exito", false))
                            {
                                preferences.edit().putInt("sesion", 0).commit();
                                Snackbar.make(parentLayout,"Cuenta creada, ahora puedes iniciar sesion.",Snackbar.LENGTH_SHORT).show();
                                ExisteCuenta(campos.get(2).getText().toString(),campos.get(3).getText().toString());
                                PantallaInicioDeSesion();
                            }
                            else
                            {
                                /////
                                Snackbar.make(parentLayout,"Algo ha salido mal, intente nuevamente, de no funcionar, reinicie la aplicación.",Snackbar.LENGTH_SHORT).show();
                            }
                        }
                        /////////////
                    }
                    else {
                        Snackbar.make(parentLayout,"Algo ha salido mal, intente nuevamente, de no funcionar, reinicie la aplicación.",Snackbar.LENGTH_SHORT).show();
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
                        ///////////////
                        pantalla = 1;
                        preferences.edit().putInt("sesion", 1).commit();
                        pr.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                correo.setTitle("Usuario: "+usuario.usuario);
                                pin.setTitle("Pin: "+usuario.pin);
                            }
                        });
                        toolbar.post(new Runnable() {
                            @Override
                            public void run() {
                                toolbar.setVisibility(View.VISIBLE);
                            }
                        });
                        PantallaMapa();
                        ///////////////
                    }
                    else
                    {
                        Snackbar.make(parentLayout,"Usuario o contraseña incorrectos.",Snackbar.LENGTH_SHORT).show();
                    }
                    preferences.edit().putBoolean("procesoFinalizado",true).commit();
                }
                catch (Exception ex)
                {
                    Snackbar.make(parentLayout,ex.getMessage(),Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        t.start();
    }
    public static void ExisteCuentaFacebook(final String email, final String contra, final JSONObject object)
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
                        ////////////
                        pantalla = 1;
                        preferences.edit().putInt("sesion", 2).commit();
                        pr.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                correo.setTitle("Usuario: "+usuario.usuario);
                                pin.setTitle("Pin: "+usuario.pin);
                            }
                        });
                        toolbar.post(new Runnable() {
                            @Override
                            public void run() {
                                toolbar.setVisibility(View.VISIBLE);
                            }
                        });
                        PantallaMapa();

                        ////////////
                    }
                    else {
                        preferences.edit().putInt("sesion", 3).commit();
                        preferences.edit()
                                .putString("correoUsuario", object.getString("email"))
                                .putString("nombresUsuario", object.getString("first_name"))
                                .putString("apellidosUsuario", object.getString("last_name"))
                                .putString("contrasenaUsuario", object.getString("id"))
                                .commit();
                        PantallaRegistro();
                    }
                    preferences.edit().putBoolean("procesoFinalizado",true).commit();
                }
                catch (Exception ex)
                {
                    Snackbar.make(parentLayout,ex.getMessage(),Snackbar.LENGTH_SHORT).show();
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
                        Snackbar.make(parentLayout,"Conexión exitosa.",Snackbar.LENGTH_SHORT).show();
                            PantallaHijos();
                    }
                    else
                        Snackbar.make(parentLayout,"Usuario o Pin incorrectos.",Snackbar.LENGTH_SHORT).show();
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
    /*
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
    */
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
                        final AdaptadorLista adaptadorLista = new AdaptadorLista(listaHijos, c, b);
                        lv.post(new Runnable() {
                            @Override
                            public void run() {
                                lv.setAdapter(null);
                                lv.setAdapter(adaptadorLista);
                            }
                        });

                        if(listaHijos.size()==0)
                            Snackbar.make(parentLayout,"No se han encontrado conexiones. Agrega a alguien e intenta nuevamente.",Snackbar.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Snackbar.make(parentLayout,"Algo ha salido mal, intente nuevamente, de no funcionar, reinicie la aplicación.",Snackbar.LENGTH_SHORT).show();
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
                        pr.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                DibujarRuta();
                            }
                        });
                    }
                    else
                    {
                        Snackbar.make(parentLayout,"Algo ha salido mal, intente nuevamente, de no funcionar, reinicie la aplicación.",Snackbar.LENGTH_SHORT).show();
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
    public static void MostrarRutaDeFecha(final String id, final String fecha)
    {
        preferences.edit().putBoolean("existe", false).commit();
        Thread t =new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ServiciosWeb sw = new ServiciosWeb();
                    if(sw.MostrarRutaFecha(id,fecha)==1)
                    {
                        preferences.edit().putBoolean("existe", true).commit();
                        pr.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                DibujarRuta();
                            }
                        });
                    }
                    else
                    {
                        Snackbar.make(parentLayout,"No hay registros para esta fecha.",Snackbar.LENGTH_LONG).show();
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
    public static void EliminarHijo(final String padre, final String hijo)
    {
        Thread t =new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ServiciosWeb sw = new ServiciosWeb();
                    if(sw.EliminarHijo(padre,hijo)==1)
                    {
                        ActualizarLista();
                        Snackbar.make(parentLayout,"Conexión eliminada.",Snackbar.LENGTH_LONG).show();
                    }
                    else
                    {

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
