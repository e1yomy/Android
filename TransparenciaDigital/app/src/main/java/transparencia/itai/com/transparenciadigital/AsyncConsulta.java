package transparencia.itai.com.transparenciadigital;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import static transparencia.itai.com.transparenciadigital.MainActivity.CambiarPantalla;
import static transparencia.itai.com.transparenciadigital.MainActivity.FormatoNombre;
import static transparencia.itai.com.transparenciadigital.MainActivity.HabilitarMenu;
import static transparencia.itai.com.transparenciadigital.MainActivity.c;
import static transparencia.itai.com.transparenciadigital.MainActivity.navigationView;
import static transparencia.itai.com.transparenciadigital.MainActivity.pantalla;
import static transparencia.itai.com.transparenciadigital.MainActivity.postDataParams;
import static transparencia.itai.com.transparenciadigital.MainActivity.preferences;
import static transparencia.itai.com.transparenciadigital.MainActivity.toolbar;
import static transparencia.itai.com.transparenciadigital.MainActivity.txtEmailUsuario;
import static transparencia.itai.com.transparenciadigital.MainActivity.txtNombreUsuario;
import static transparencia.itai.com.transparenciadigital.MainActivity.usr;

/**
 * Created by elyo_ on 19/06/2017.
 */

public class AsyncConsulta extends AsyncTask<String, Void, String> {

    protected void onPreExecute(){}

    @Override
    protected String doInBackground(String... arg0) {

        try {

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
            int responseCode=conn.getResponseCode();
            InputStream inputStream;
            // get stream
            if (conn.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                inputStream = conn.getInputStream();
            }
            else {
                inputStream = conn.getErrorStream();
            }
            // parse stream
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String temp, response = "";
            while ((temp = bufferedReader.readLine()) != null) {
                response += temp;
            }

            return response;

        }
        catch(final Exception e){
            return new String("Exception: " + e.getMessage());
        }

    }

    protected void onPostExecute(String result) {
        //Debug.waitForDebugger();

        Toast.makeText(c, result,
                Toast.LENGTH_LONG).show();
        try {
            JSONObject json=new JSONObject(result);
            //Getting JSON Array node
            switch (postDataParams.get("funcion").toString())
            {
                case "acceso":
                    /////////////
                    preferences.edit().putString("headernombreusuario",FormatoNombre(json.getString("nombre")).split(" ")[0]+" "+ FormatoNombre(json.getString("apellidoPaterno")).split(" ")[0]).commit();
                    preferences.edit().putString("headercorreo",json.getString("correo")).commit();
                    preferences.edit().putString("idUsuario",json.getString("idUsuario")).commit();
                    preferences.edit().putString("correo",json.getString("correo")).commit();
                    preferences.edit().putString("contrasena",json.getString("contrasena")).commit();
                    preferences.edit().putString("nombre",json.getString("nombre")).commit();
                    preferences.edit().putString("apellidoPaterno",json.getString("apellidoPaterno")).commit();
                    preferences.edit().putString("apellidoMaterno",json.getString("apellidoMaterno")).commit();
                    preferences.edit().putString("calle",json.getString("calle")).commit();
                    preferences.edit().putString("numeroExterior",json.getString("numeroExterior")).commit();
                    preferences.edit().putString("numeroInterior",json.getString("numeroInterior")).commit();
                    preferences.edit().putString("entreCalles",json.getString("entreCalles")).commit();
                    preferences.edit().putString("colonia",json.getString("colonia")).commit();
                    preferences.edit().putString("CP",json.getString("CP")).commit();
                    preferences.edit().putString("entidad",json.getString("entidad")).commit();
                    preferences.edit().putString("municipio",json.getString("municipio")).commit();
                    preferences.edit().putString("telefono",json.getString("telefono")).commit();
                    usr=new Usuario(
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
                    txtNombreUsuario.setText(preferences.getString("headernombreusuario","Nombre"));
                    txtEmailUsuario.setText(preferences.getString("headercorreo","alguien@example.com"));
                    CambiarPantalla(new MisSolicitudes());
                    pantalla=1;
                    break;
            }
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }


    }
}
