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

import static transparencia.itai.com.transparenciadigital.MainActivity.FormatoNombre;
import static transparencia.itai.com.transparenciadigital.MainActivity.preferences;
import static transparencia.itai.com.transparenciadigital.MainActivity.usr;

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
        try {
            JSONArray json=new JSONArray(response);
            String str="";
            if (json.length()>0){
                res=1;
                preferences.edit().putString("headernombreusuario",FormatoNombre(json.getJSONObject(0).getString("nombre"))+" "+ FormatoNombre(json.getJSONObject(0).getString("apellidoPaterno"))).commit();
                preferences.edit().putString("headercorreo",json.getJSONObject(0).getString("correo")).commit();

                preferences.edit().putString("idUsuario",json.getJSONObject(0).getString("idUsuario")).commit();
                preferences.edit().putString("idRol",json.getJSONObject(0).getString("idRol")).commit();
                preferences.edit().putString("correo",json.getJSONObject(0).getString("correo")).commit();
                preferences.edit().putString("contrasena",json.getJSONObject(0).getString("contrasena")).commit();
                preferences.edit().putString("nombre",json.getJSONObject(0).getString("nombre")).commit();
                preferences.edit().putString("apellidoPaterno",json.getJSONObject(0).getString("apellidoPaterno")).commit();
                preferences.edit().putString("apellidoMaterno",json.getJSONObject(0).getString("apellidoMaterno")).commit();
                preferences.edit().putString("calle",json.getJSONObject(0).getString("calle")).commit();
                preferences.edit().putString("numeroExterior",json.getJSONObject(0).getString("numeroExterior")).commit();
                preferences.edit().putString("numeroInterior",json.getJSONObject(0).getString("numeroInterior")).commit();
                preferences.edit().putString("entreCalles",json.getJSONObject(0).getString("entreCalles")).commit();
                preferences.edit().putString("colonia",json.getJSONObject(0).getString("colonia")).commit();
                preferences.edit().putString("CP",json.getJSONObject(0).getString("CP")).commit();
                preferences.edit().putString("entidad",json.getJSONObject(0).getString("entidad")).commit();
                preferences.edit().putString("municipio",json.getJSONObject(0).getString("municipio")).commit();
                preferences.edit().putString("telefono",json.getJSONObject(0).getString("telefono")).commit();
                usr=new Usuario(
                        json.getJSONObject(0).getString("idUsuario"),
                        json.getJSONObject(0).getString("idRol"),
                        json.getJSONObject(0).getString("correo"),
                        json.getJSONObject(0).getString("contrasena"),
                        json.getJSONObject(0).getString("nombre"),
                        json.getJSONObject(0).getString("apellidoPaterno"),
                        json.getJSONObject(0).getString("apellidoMaterno"),
                        json.getJSONObject(0).getString("calle"),
                        json.getJSONObject(0).getString("numeroExterior"),
                        json.getJSONObject(0).getString("numeroInterior"),
                        json.getJSONObject(0).getString("entreCalles"),
                        json.getJSONObject(0).getString("colonia"),
                        json.getJSONObject(0).getString("CP"),
                        json.getJSONObject(0).getString("entidad"),
                        json.getJSONObject(0).getString("municipio"),
                        json.getJSONObject(0).getString("telefono"));
            }

        }catch (Exception e){
            Log.e(null,e.getMessage(),e.getCause());
        }
        return  res;
    }
    public ArrayList<String> GetSolicitudes(String usuario)
    {
        urlprevia=webService+"";

        if(ConexionCorrecta(urlprevia)==1);
        {
            try {
                InputStream in = new BufferedInputStream(conection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                while ((linea = reader.readLine()) != null)
                    resul.append(linea);
            }
            catch (Exception ex){
                String s = ex.getMessage();
            }
        }
        //Else de mensaje de error por falta de red
        return ObtenerListaDeSolicitudes(resul.toString());

    }
    public ArrayList<String> ObtenerListaDeSolicitudes(String response)
    {
        ArrayList<String> lista= new ArrayList<>();
        try{
            JSONArray json=new JSONArray(response);
            for(int i=0; i<json.length();i++){
                //Ya que se agregue el campo titulo a la tabla de solicitudes, se podra hacer uso de esta funcion
                //lista.add(json.getJSONObject(i).getString("titulo"));
            }
        }
        catch (Exception ex)
        {
            String exx=ex.getMessage();
        }


        return  lista;
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
