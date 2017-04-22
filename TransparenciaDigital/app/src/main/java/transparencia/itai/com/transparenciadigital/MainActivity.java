package transparencia.itai.com.transparenciadigital;

import android.content.Context;
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
import android.view.MenuItem;
import android.view.View;
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


    static Context c;
    Toolbar toolbar;
    static DrawerLayout drawer;
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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        c=this;
        try{
            //navigationView.getMenu().getItem(0).setChecked(true);
            getSupportFragmentManager().beginTransaction().replace(R.id.content_principal,new Splash()).commit();
            toolbar.setVisibility(View.GONE);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    toolbar.setVisibility(View.VISIBLE);
                    //if hay sesion
                    //getSupportFragmentManager().beginTransaction().replace(R.id.content_principal, new MisSolicitudes()).commit();
                    //else
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_principal, new Sesion()).commit();
                }
            },2000);

            fragmentManager=getSupportFragmentManager();
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
            //drawer.openDrawer(GravityCompat.START);
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    static FragmentManager fragmentManager;
    static boolean sesion=false;
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(!sesion)
        {
            fragmentManager.beginTransaction().replace(R.id.content_principal,new Sesion()).commit();
            sesion=!sesion;
        }
        else
        {
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            if (id == R.id.nav_missolicitudes) {
                //Listado de solicitudes del usuario
                fragmentTransaction.replace(R.id.content_principal, new MisSolicitudes()).commit();
            }else if (id == R.id.nav_sujetosobligados) {
                // Handle the camera action
                fragmentTransaction.replace(R.id.content_principal,new SujetosObligados()).commit();
            }else if (id == R.id.nav_acceso) {
                //Solicitar acceso a informacion
                fragmentTransaction.replace(R.id.content_principal, new NuevaSolicitudAcceso()).commit();

            } else if (id == R.id.nav_denuncia) {
                //Solicitar recurso de revision
                fragmentTransaction.replace(R.id.content_principal, new NuevaSolicitudDenuncia()).commit();

            } else if(id==R.id.nav_registro){
                fragmentTransaction.replace(R.id.content_principal, new Registro()).commit();
            }
            else if(id==R.id.nav_salir){
                fragmentTransaction.commit();
                finish();

            }
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
            getSupportFragmentManager().beginTransaction().replace(R.id.content_principal, new Registro()).commit();

        } else if(id==R.id.action_cerrarsesion){
            sesion=!sesion;
            getSupportFragmentManager().beginTransaction().replace(R.id.content_principal, new Sesion()).commit();
        }

        return super.onOptionsItemSelected(item);
    }

}
