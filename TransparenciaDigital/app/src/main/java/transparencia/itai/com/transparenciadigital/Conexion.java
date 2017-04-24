package transparencia.itai.com.transparenciadigital;

import android.util.Log;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
            if (json.length()>0){
                res=1;
            }

        }catch (Exception e){
            Log.e(null,e.getMessage(),e.getCause());

        }
        return  res;
    }
    

}
