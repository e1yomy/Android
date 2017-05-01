package transparencia.itai.com.transparenciadigital;

import android.util.Log;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
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
    final String contrasenaWS="patito";

    public ArrayList<String> GetSolicitudes(String usuario){
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
    public int IniciarSesion(String usr, String pass){
        String data = "";
        BufferedReader reader=null;
        StringBuilder sb= new StringBuilder();
        URLConnection conn;
        OutputStreamWriter wr;

        try {
            //Indica url del webservice
            urlprevia=webService+"iniciarsesion.php";
            direccion = new URL(urlprevia);
            //Datos a enviar en POST
            data = URLEncoder.encode("usuario", "UTF-8")+ "=" + URLEncoder.encode(usr, "UTF-8");
            data += "&" + URLEncoder.encode("contrasena", "UTF-8") + "="+ URLEncoder.encode(pass, "UTF-8");
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
        //Envia la respuesta del servidor a obtener los datos, verificar que haya existencia, guardar datos y continuar;
        return obtenerDatosInicioSesionJson(sb.toString());
    }
    public int obtenerDatosInicioSesionJson(String response){
        int res =0;
        try {
            JSONArray json=new JSONArray(response);
            String str="";
            if (json.length()>0){
                res=1;
                preferences.edit().putString("headernombreusuario",FormatoNombre(json.getJSONObject(0).getString("nombre"))+" "+ FormatoNombre(json.getJSONObject(0).getString("apellidoPaterno"))).commit();
                preferences.edit().putString("headercorreo",json.getJSONObject(0).getString("correo")).commit();

                preferences.edit().putString("idUsuario",json.getJSONObject(0).getString("idUsuario")).commit();

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

    public int RegistrarUsuario( String correo, String contrasena, String nombres, String paterno, String materno, String calle, String noExterno, String noInterno, String entreCalles, String colonia, String cp, String entidadFederativa, String municipio, String telefono)
    {
        String data = "";
        urlprevia=webService+"registro.php";
        try {
            direccion= new URL(urlprevia);

            data = URLEncoder.encode("", "UTF-8")+ "=" + URLEncoder.encode(correo, "UTF-8");
            data += "&" + URLEncoder.encode("contrasena", "UTF-8") + "="+ URLEncoder.encode(contrasena, "UTF-8");
            data += "&" + URLEncoder.encode("contrasena", "UTF-8") + "="+ URLEncoder.encode(nombres, "UTF-8");
            data += "&" + URLEncoder.encode("contrasena", "UTF-8") + "="+ URLEncoder.encode(paterno, "UTF-8");
            data += "&" + URLEncoder.encode("contrasena", "UTF-8") + "="+ URLEncoder.encode(materno, "UTF-8");
            data += "&" + URLEncoder.encode("contrasena", "UTF-8") + "="+ URLEncoder.encode(calle, "UTF-8");
            data += "&" + URLEncoder.encode("contrasena", "UTF-8") + "="+ URLEncoder.encode(noExterno, "UTF-8");
            data += "&" + URLEncoder.encode("contrasena", "UTF-8") + "="+ URLEncoder.encode(noInterno, "UTF-8");
            data += "&" + URLEncoder.encode("contrasena", "UTF-8") + "="+ URLEncoder.encode(entreCalles, "UTF-8");
            data += "&" + URLEncoder.encode("contrasena", "UTF-8") + "="+ URLEncoder.encode(colonia, "UTF-8");
            data += "&" + URLEncoder.encode("contrasena", "UTF-8") + "="+ URLEncoder.encode(cp, "UTF-8");
            data += "&" + URLEncoder.encode("contrasena", "UTF-8") + "="+ URLEncoder.encode(entidadFederativa, "UTF-8");
            data += "&" + URLEncoder.encode("contrasena", "UTF-8") + "="+ URLEncoder.encode(municipio, "UTF-8");
            data += "&" + URLEncoder.encode("contrasena", "UTF-8") + "="+ URLEncoder.encode(telefono, "UTF-8");
            data += "&" + URLEncoder.encode("pass", "UTF-8")+ "=" + URLEncoder.encode(contrasenaWS, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

}
