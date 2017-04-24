package transparencia.itai.com.transparenciadigital;

import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static transparencia.itai.com.transparenciadigital.MainActivity.preferences;

/**
 * Created by elyo_ on 23/04/2017.
 */

public class Conexion {

    URL direccion=null;
    String urlprevia="";
    final String webService= "http://pruebastec.890m.com/webservices/";
    String linea="";
    int respuesta=0;
    StringBuilder resul=new StringBuilder();
    HttpURLConnection conection;
    static ArrayList<String> listaDeDatos;

    public int ConexionCorrecta(String url){
        try {
            direccion= new URL(url);
            conection = (HttpURLConnection) direccion.openConnection();
            respuesta = conection.getResponseCode();
            resul = new StringBuilder();
            if(respuesta==HttpURLConnection.HTTP_OK)
                return 1;
            else
                return 0;
        }
        catch (Exception ex){
            return 0;
        }

    }
    public int IniciarSesion(String usuario, String contrasena){
        urlprevia=webService+"valida.php?"+"usu="+usuario+"&pas="+contrasena;

         if(ConexionCorrecta(urlprevia)==1);
        {
            try {
                InputStream in = new BufferedInputStream(conection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                while ((linea = reader.readLine()) != null)
                    resul.append(linea);
                //crear objeto Usuario con los datos del resultado de la consulta.
            }
            catch (Exception ex){
                String s = ex.getMessage();
            }
        }
        //Else de mensaje de error por falta de red
        return obtenerDatosJson(resul.toString());
    }
    public int obtenerDatosJson(String response){
        int res =0;
        listaDeDatos= new ArrayList<>();
        try {
            JSONArray json=new JSONArray(response);
            String str="";
            if (json.length()>0){
                res=1;
                preferences.edit().putString("usuario",json.getJSONObject(0).getString("nombre")+" "+ json.getJSONObject(0).getString("apellidoPaterno"));
                listaDeDatos.add(json.getJSONObject(0).getString("idUsuario"));
                listaDeDatos.add(json.getJSONObject(0).getString("idRol"));
                listaDeDatos.add(json.getJSONObject(0).getString("correo"));
                listaDeDatos.add(json.getJSONObject(0).getString("contrasena"));
                listaDeDatos.add(json.getJSONObject(0).getString("nombre"));
                listaDeDatos.add(json.getJSONObject(0).getString("apellidoPaterno"));
                listaDeDatos.add(json.getJSONObject(0).getString("apellidoMaterno"));
                listaDeDatos.add(json.getJSONObject(0).getString("calle"));
                listaDeDatos.add(json.getJSONObject(0).getString("numeroExterior"));
                listaDeDatos.add(json.getJSONObject(0).getString("numeroInterior"));
                listaDeDatos.add(json.getJSONObject(0).getString("entreCalles"));
                listaDeDatos.add(json.getJSONObject(0).getString("colonia"));
                listaDeDatos.add(json.getJSONObject(0).getString("CP"));
                listaDeDatos.add(json.getJSONObject(0).getString("entidad"));
                listaDeDatos.add(json.getJSONObject(0).getString("municipio"));
                listaDeDatos.add(json.getJSONObject(0).getString("telefono"));
            }

        }catch (Exception e){
            Log.e(null,e.getMessage(),e.getCause());
        }
        return  res;
    }
    public int RegistrarUsuario(String id, String rol, String correo, String contrasena, String nombres, String paterno, String materno, String calle, String noExterno, String noInterno, String entreCalles, String colonia, String cp, String entidadFederativa, String municipio, String telefono)
    {
        urlprevia=webService+"registro.php?"+"";

        if(ConexionCorrecta(urlprevia)==1);
        {
            try{

            }
            catch (Exception ex)
            {
                String exx=ex.getMessage();
            }
        }

        return 1;
    }

}
