package transparencia.itai.com.transparenciadigital;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import static transparencia.itai.com.transparenciadigital.MainActivity.CambiarPantalla;
import static transparencia.itai.com.transparenciadigital.MainActivity.FormatoNombre;
import static transparencia.itai.com.transparenciadigital.MainActivity.HabilitarMenu;
import static transparencia.itai.com.transparenciadigital.MainActivity.OcultarSnack;
import static transparencia.itai.com.transparenciadigital.MainActivity.Snack;
import static transparencia.itai.com.transparenciadigital.MainActivity.c;
import static transparencia.itai.com.transparenciadigital.MainActivity.idSO;
import static transparencia.itai.com.transparenciadigital.MainActivity.ma;
import static transparencia.itai.com.transparenciadigital.MainActivity.navigationView;
import static transparencia.itai.com.transparenciadigital.MainActivity.nombresSO;
import static transparencia.itai.com.transparenciadigital.MainActivity.pantalla;
import static transparencia.itai.com.transparenciadigital.MainActivity.postDataParams;
import static transparencia.itai.com.transparenciadigital.MainActivity.preferences;
import static transparencia.itai.com.transparenciadigital.MainActivity.progressDialog;
import static transparencia.itai.com.transparenciadigital.MainActivity.toolbar;
import static transparencia.itai.com.transparenciadigital.MainActivity.txtEmailUsuario;
import static transparencia.itai.com.transparenciadigital.MainActivity.txtNombreUsuario;
import static transparencia.itai.com.transparenciadigital.MainActivity.usr;
import static transparencia.itai.com.transparenciadigital.MisSolicitudes.ActualizarListaPrimaria;
import static transparencia.itai.com.transparenciadigital.MisSolicitudes.lista2;
import static transparencia.itai.com.transparenciadigital.MisSolicitudes.lv1;
import static transparencia.itai.com.transparenciadigital.MisSolicitudes.lv2;
import static transparencia.itai.com.transparenciadigital.MisSolicitudes.opcion;
import static transparencia.itai.com.transparenciadigital.MisSolicitudes.solicitudes;
import static transparencia.itai.com.transparenciadigital.Sesion.LimpiarCampos;
import static transparencia.itai.com.transparenciadigital.Sesion.btnRegistro1;
import static transparencia.itai.com.transparenciadigital.Sesion.btnVolverRegistro;
import static transparencia.itai.com.transparenciadigital.Sesion.layoutInicioSesion;
import static transparencia.itai.com.transparenciadigital.Sesion.layoutRegistro1;
import static transparencia.itai.com.transparenciadigital.SujetosObligados.listas;

/**
 * Created by elyo_ on 19/06/2017.
 */

public class AsyncConsulta extends AsyncTask<String, Void, String> {
    int i=0;
    protected void onPreExecute(){
        progressDialog.show();

    }

    @Override
    protected String doInBackground(String... arg0) {
        try {
            //if (ma.VerificarConexion())
            {
                URL url = new URL("http://pruebastec.890m.com/finales/datos.php"); // here is your URL path
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "application/json");
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(postDataParams.toString());
                writer.flush();
                writer.close();
                os.close();
                int responseCode = conn.getResponseCode();
                InputStream inputStream;
                // get stream
                if (conn.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                    inputStream = conn.getInputStream();
                } else {
                    inputStream = conn.getErrorStream();
                }
                // parse stream
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp, response = "";
                while ((temp = bufferedReader.readLine()) != null) {
                    response += temp;
                }
                conn.disconnect();
                return response;
            }

        }
        catch (UnknownHostException uh)
        {
            return "UH";
        }
        catch (SocketTimeoutException to)
        {
            return "TO";
        }
        catch (final Exception e) {
            return "Exception: " + e.getMessage();
        }
        finally{

        }
    }

    protected void onPostExecute(String result) {
        //Debug.waitForDebugger();
        if(result.equals("UH"))
            Snack("No se ha podido conectar con la plataforma");
        else if(result.equals("TO"))
            Snack("Se ha sobrepasado el tiempo de espera");
        else if(!result.equals("")) {
            try {
                i = 0;
                switch (postDataParams.get("funcion").toString()) {
                    case "registro":
                        Registro(result);
                        break;
                    case "nuevaSolicitud":
                        NuevaSolicitud(result);
                        break;
                    case "nuevoRecurso":
                        NuevoRecurso(result);
                        break;
                    case "nuevaDenuncia":
                        NuevaDenuncia(result);
                        break;
                    case "acceso":
                        Acceso(result);
                        break;
                    case "misSolicitudes":
                        ListaDeSolicitudes(result);
                        break;
                    case "listarSujetos":
                        ListarSujetos(result);
                        break;
                    case "datosSolicitud":
                        DatosSolicitud(result);
                        break;
                    case "listarSolicitudSujetos":
                        SolicitudesSujeto(result);
                        break;

                }
            } catch (JSONException e) {
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            } catch (Exception e) {
                Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Snack("Verificar la conexión");
        }
        progressDialog.dismiss();
    }

    private void NuevaSolicitud(String result) {
        if(result.equals("true"))
        {
            //Solicitud enviada
            ma.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Snack("Solicitud enviada");
                        CambiarPantalla(new MisSolicitudes(),1);
                    }
                    catch (Exception ex)
                    {
                        String s= ex.getMessage();
                    }
                }
            });
        }
        else
        {
            Snack("Ha ocurrido un problema y su Solicitud no pudo ser enviada");
        }
    }

    private void NuevoRecurso(String result) {
        if(result.equals("true"))
        {
            //Solicitud enviada
            ma.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Snack("Recurso de Revisión enviado");
                        CambiarPantalla(new MisSolicitudes(),1);
                    }
                    catch (Exception ex)
                    {
                        String s= ex.getMessage();
                    }
                }
            });
        }
        else
        {
            Snack("Ha ocurrido un problema y su Recurso no pudo ser enviado");
        }
    }

    private void NuevaDenuncia(String result) {
        if(result.equals("true"))
        {
            //Solicitud enviada
            ma.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Snack("Denuncia enviada");
                        CambiarPantalla(new MisSolicitudes(),1);
                    }
                    catch (Exception ex)
                    {
                        String s= ex.getMessage();
                    }
                }
            });
        }
        else
        {
            Snack("Ha ocurrido un problema y su Denuncia no pudo ser enviada");
        }
    }

    private void ListaDeSolicitudes(String result) {
        try {
            solicitudes.clear();
            String t = "";
            String titulo="";
            switch (opcion) {
                case 0:
                    t = "idAcceso";
                    titulo="Solicitudes de Acceso";
                    break;
                case 1:
                    t = "idRecurso";
                    titulo="Recursos de Revisión";
                    break;
                case 2:
                    t = "idDemanda";
                    titulo="Denuncias por Incumplimiento";
                    break;
            }
            JSONArray j1;
            try{j1 = new JSONArray(result);}
            catch (Exception e)
            {
                Snack("No se han encontrado "+titulo);
                return;
            }
                while (i < j1.length()) {
                    {
                        JSONObject j = j1.getJSONObject(i);
                        solicitudes.add(new SolicitudItem(opcion, j.getInt(t), j.getString("nombreSujeto"), j.getString("fecha").split(" ")[0], 2));
                    }
                    i++;
                }
                lv1.post(new Runnable() {
                    @Override
                    public void run() {
                        ActualizarListaPrimaria();
                         OcultarSnack();
                    }
                });

        } catch (Exception e) {
        }
    }

    private void ListarSujetos(String result) {
        try {
            idSO.clear();
            nombresSO.clear();
            if(result.contains("NA"))
            {
                Snack("No se han encontrado Sujetos Obligados");
                return;
            }
            JSONArray jsonArray=new JSONArray(result);
            while (i<jsonArray.length()) {
                idSO.add(jsonArray.getJSONObject(i).getString("idSuj"));
                nombresSO.add(jsonArray.getJSONObject(i).getString("nombre"));
                i++;
            }
        }
        catch (Exception e)
        {
            String a=e.getMessage();
        }
    }

    private void SolicitudesSujeto(String result) {
        try {
            solicitudes.clear();
            if(result.contains("NA"))
            {
                Snack("No se han encontrado Solicitudes para el Sujeto Obligado seleccionado");
                return;
            }
            JSONArray json = new JSONArray(result);
                i = 0;
                while (i < json.length()) {
                    {
                        JSONObject j = json.getJSONObject(i);
                        solicitudes.add(new SolicitudItem(0, j.getInt("idAcceso"), j.getString("nombreSujeto"), j.getString("fecha").split(" ")[0], 3));
                    }
                    i++;
                }
                AdaptadorLista adaptadorLista = new AdaptadorLista(c, solicitudes);
                listas.get(1).setAdapter(adaptadorLista);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void DatosSolicitud(String result) {
        try {
            JSONObject js = new JSONObject(result);
            if(pantalla==1) {
                lista2.clear();
                switch (opcion) {
                    case 0:
                        lista2.add("Solicitud de Acceso a la Información");
                        lista2.add("Folio: " + js.getString("folio"));
                        lista2.add("Fecha: " + js.getString("fecha").split(" ")[0]);
                        lista2.add("Sujeto Obligado: " + js.getString("nombreSujeto"));
                        lista2.add("Descripción: " + js.getString("descripcion"));
                        break;
                    case 1:
                        lista2.add("Recurso de Revisión");
                        lista2.add("Folio: " + js.getString("folio"));
                        lista2.add("Fecha: " + js.getString("fecha").split(" ")[0]);
                        lista2.add("Sujeto Obligado: " + js.getString("nombreSujeto"));
                        lista2.add("Causa: " + js.getString("causa"));
                        lista2.add("Motivo: " + js.getString("motivo"));
                        lista2.add("Pruebas(folio de solicitud): " + js.getString("pruebas"));
                        break;
                    case 2:
                        lista2.add("Denuncia por Incumplimiento de Obligaciones de Transparencia");
                        lista2.add("Folio: " + js.getString("folio"));
                        lista2.add("Fecha: " + js.getString("fecha").split(" ")[0]);
                        lista2.add("Sujeto Obligado: " + js.getString("nombreSujeto"));
                        lista2.add("Descripción: " + js.getString("descripcion"));
                        break;
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(c, android.R.layout.simple_list_item_1, lista2);
                lv2.setAdapter(arrayAdapter);
            }
            else if(pantalla==5)
            {
                List<String> l = new ArrayList<String>();
                l.add("Solicitud de Acceso a la Información");
                l.add("Folio: " + js.getString("folio"));
                l.add("Fecha: " + js.getString("fecha").split(" ")[0]);
                l.add("Sujeto Obligado: " + js.getString("nombreSujeto"));
                l.add("Descripción: " + js.getString("descripcion"));
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(c, android.R.layout.simple_list_item_1, l);
                listas.get(2).setAdapter(arrayAdapter);
            }
        } catch (Exception e) {

        }
    }

    private void Acceso(String result) {
        try {
            if(result.contains("incorrectos"))
            {
                Snack("Correo electrónico o contraseña incorrectos");
                return;
            }
            JSONObject json = new JSONObject(result);
            /////////////
            preferences.edit().putString("headernombreusuario", FormatoNombre(json.getString("nombre")).split(" ")[0] + " " + FormatoNombre(json.getString("apellidoPaterno")).split(" ")[0]).commit();
            preferences.edit().putString("headercorreo", json.getString("correo")).commit();
            preferences.edit().putString("idUsuario", json.getString("idUsuario")).commit();
            preferences.edit().putString("correo", json.getString("correo")).commit();
            preferences.edit().putString("contrasena", json.getString("contrasena")).commit();
            preferences.edit().putString("nombre", json.getString("nombre")).commit();
            preferences.edit().putString("apellidoPaterno", json.getString("apellidoPaterno")).commit();
            preferences.edit().putString("apellidoMaterno", json.getString("apellidoMaterno")).commit();
            preferences.edit().putString("calle", json.getString("calle")).commit();
            preferences.edit().putString("numeroExterior", json.getString("numeroExterior")).commit();
            preferences.edit().putString("numeroInterior", json.getString("numeroInterior")).commit();
            preferences.edit().putString("entreCalles", json.getString("entreCalles")).commit();
            preferences.edit().putString("colonia", json.getString("colonia")).commit();
            preferences.edit().putString("CP", json.getString("CP")).commit();
            preferences.edit().putString("entidad", json.getString("entidad")).commit();
            preferences.edit().putString("municipio", json.getString("municipio")).commit();
            preferences.edit().putString("telefono", json.getString("telefono")).commit();
            usr = new Usuario(
                    json.getString("idUsuario"),
                    json.getString("correo"),
                    json.getString("contrasena"),
                    json.getString("nombre"),
                    json.getString("apellidoPaterno"),
                    json.getString("apellidoMaterno"),
                    json.getString("calle"),
                    json.getString("numeroExterior"),
                    json.getString("numeroInterior"),
                    json.getString("entreCalles"),
                    json.getString("colonia"),
                    json.getString("CP"),
                    json.getString("entidad"),
                    json.getString("municipio"),
                    json.getString("telefono"));
            /////////////
            toolbar.setVisibility(View.VISIBLE);
            preferences.edit().putBoolean("sesion", true).commit();
            HabilitarMenu(preferences.getBoolean("sesion", false));
            navigationView.getMenu().getItem(0).setChecked(true);
            txtNombreUsuario.setText(preferences.getString("headernombreusuario", "Nombre"));
            txtEmailUsuario.setText(preferences.getString("headercorreo", "alguien@example.com"));
            CambiarPantalla(new MisSolicitudes(),1);
        }
        catch (Exception e){

        }
    }

    public void Registro(String result){
        if(result.equals("true"))
        {
            //Registro exitoso
            btnVolverRegistro.hide();
            btnRegistro1.hide();
            LimpiarCampos();
            layoutInicioSesion.setVisibility(View.VISIBLE);
            layoutRegistro1.setVisibility(View.GONE);
            Snack("Usuario registrado, ahora puedes iniciar sesión");
        }
        else
        {
            //Registro fallido
            Snack("Algo ha salido mal y su usuario no se ha podido guardar");

        }
    }


}
