package elyo.my.trackids;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import static elyo.my.trackids.Principal.preferences;
import static elyo.my.trackids.Principal.usuario;

/**
 * Created by elyo_ on 19/05/2017.
 */

public class ServiciosWeb {
    String webService="http://pruebastec.890m.com/servicios";
    String urlprevia="";
    URL direccion=null;
    String linea="";
    String contrasenaWS="patito";
    String data = "";
    BufferedReader reader=null;
    StringBuilder sb= new StringBuilder();
    URLConnection conn;
    OutputStreamWriter wr;
    int res=0;
    public int ExisteCuenta(String email,String con)
    {
        try {
            //Indica url del webservice
            urlprevia=webService+"/inicio.php";
            direccion = new URL(urlprevia);
            //Datos a enviar en POST
            data = URLEncoder.encode("usu", "UTF-8")+ "=" + URLEncoder.encode(email, "UTF-8");
            data += "&" + URLEncoder.encode("pas", "UTF-8") + "="+ URLEncoder.encode(con, "UTF-8");
            data += "&" + URLEncoder.encode("pass", "UTF-8")+ "=" + URLEncoder.encode(contrasenaWS, "UTF-8");
            //Abrir conexion y envio de datos via POST
            conn= direccion.openConnection();
            conn.setDoOutput(true);
            wr= new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
            //Obtener respuesta del servidor
            reader= new BufferedReader(new InputStreamReader(conn.getInputStream()));
            //Leer respuesta del servidor
            while ((linea=reader.readLine())!=null)
                sb.append(linea);

            JSONArray json=new JSONArray(sb.toString());
            if (json.length()>0)
            {

                preferences.edit()
                        .putString("idUsuario",json.getJSONObject(0).getString("idUs"))
                        .putString("correoUsuario",json.getJSONObject(0).getString("nomUs"))
                        .putString("nombresUsuario",json.getJSONObject(0).getString("nombres"))
                        .putString("apellidosUsuario",json.getJSONObject(0).getString("apellidos"))
                        .putString("telefonoUsuario",json.getJSONObject(0).getString("telefono"))
                        .commit();
                usuario =new Usuario(
                        json.getJSONObject(0).getString("idUs"),
                        json.getJSONObject(0).getString("nomUs"),
                        json.getJSONObject(0).getString("nombres"),
                        json.getJSONObject(0).getString("apellidos"),
                        json.getJSONObject(0).getString("telefono"),
                        json.getJSONObject(0).getString("contrasena"));
                return 1;
            }
            else
            {
                return 0;
            }


        } catch (Exception ex){
            String e=ex.getMessage();
        }
        finally{
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return 1;
    }
}
