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
                preferences.edit().putString("usuario",json.getJSONObject(0).getString("nombre")+" "+ json.getJSONObject(0).getString("apellidoPaterno")).commit();
                preferences.edit().putString("correo",json.getJSONObject(0).getString("correo")).commit();
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
