package transparencia.itai.com.transparenciadigital;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import static transparencia.itai.com.transparenciadigital.MainActivity.FormatoNombre;
import static transparencia.itai.com.transparenciadigital.MainActivity.preferences;
import static transparencia.itai.com.transparenciadigital.MainActivity.usr;
import static transparencia.itai.com.transparenciadigital.MisSolicitudes.opcion;
import static transparencia.itai.com.transparenciadigital.MisSolicitudes.solicitudes;

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
    static List<String> nombresSO= new ArrayList<>();
    static List<String> idSO = new ArrayList<>();




    public int IniciarSesion(String usr, String pass){
        String data = "";
        BufferedReader reader=null;
        StringBuilder sb= new StringBuilder();
        URLConnection conn;
        OutputStreamWriter wr;

        try {
            //Indica url del webservice
            urlprevia=webService+"valida.php";
            direccion = new URL(urlprevia);
            //Datos a enviar en POST
            data = URLEncoder.encode("usu", "UTF-8")+ "=" + URLEncoder.encode(usr, "UTF-8");
            data += "&" + URLEncoder.encode("pas", "UTF-8") + "="+ URLEncoder.encode(pass, "UTF-8");
            data += "&" + URLEncoder.encode("pass", "UTF-8")+ "=" + URLEncoder.encode(contrasenaWS, "UTF-8");
            //Abrir conexion y envio de datos via POST
            conn= direccion.openConnection();
            conn.setDoOutput(true);
            conn.setConnectTimeout(3000);
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
            return 0;
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
                preferences.edit().putString("headernombreusuario",FormatoNombre(json.getJSONObject(0).getString("nombre")).split(" ")[0]+" "+ FormatoNombre(json.getJSONObject(0).getString("apellidoPaterno")).split(" ")[0]).commit();
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
                        json.getJSONObject(0).getString("idUsuario"),
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
            BufferedReader reader=null;
            StringBuilder sb= new StringBuilder();
            URLConnection conn;
            OutputStreamWriter wr;

            data = URLEncoder.encode("usu", "UTF-8")+ "=" + URLEncoder.encode(correo, "UTF-8");
            data += "&" + URLEncoder.encode("pas", "UTF-8") + "="+ URLEncoder.encode(contrasena, "UTF-8");
            data += "&" + URLEncoder.encode("nom", "UTF-8") + "="+ URLEncoder.encode(nombres, "UTF-8");
            data += "&" + URLEncoder.encode("paterno", "UTF-8") + "="+ URLEncoder.encode(paterno, "UTF-8");
            data += "&" + URLEncoder.encode("materno", "UTF-8") + "="+ URLEncoder.encode(materno, "UTF-8");
            data += "&" + URLEncoder.encode("calles", "UTF-8") + "="+ URLEncoder.encode(calle, "UTF-8");
            data += "&" + URLEncoder.encode("numeroExterior", "UTF-8") + "="+ URLEncoder.encode(noExterno, "UTF-8");
            data += "&" + URLEncoder.encode("numeroInterior", "UTF-8") + "="+ URLEncoder.encode(noInterno, "UTF-8");
            data += "&" + URLEncoder.encode("entreCalles", "UTF-8") + "="+ URLEncoder.encode(entreCalles, "UTF-8");
            data += "&" + URLEncoder.encode("colonia", "UTF-8") + "="+ URLEncoder.encode(colonia, "UTF-8");
            data += "&" + URLEncoder.encode("CP", "UTF-8") + "="+ URLEncoder.encode(cp, "UTF-8");
            data += "&" + URLEncoder.encode("entidad", "UTF-8") + "="+ URLEncoder.encode(entidadFederativa, "UTF-8");
            data += "&" + URLEncoder.encode("municipio", "UTF-8") + "="+ URLEncoder.encode(municipio, "UTF-8");
            data += "&" + URLEncoder.encode("telefono", "UTF-8") + "="+ URLEncoder.encode(telefono, "UTF-8");
            data += "&" + URLEncoder.encode("pass", "UTF-8") + "="+ URLEncoder.encode(contrasenaWS, "UTF-8");

            //Abrir conexion y envio de datos via POST
            conn= direccion.openConnection();
            conn.setDoOutput(true);
            conn.setConnectTimeout(3000);
            wr= new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
            //Obtener respuesta del servidor
            reader= new BufferedReader(new InputStreamReader(conn.getInputStream()));
            //Leer respuesta del servidor
            while ((linea=reader.readLine())!=null)
                sb.append(linea);
            return 1;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }
    public int ListarSujetos(){
        String data = "";
        BufferedReader reader=null;
        StringBuilder sb= new StringBuilder();
        URLConnection conn;
        OutputStreamWriter wr;

        try {
            //Indica url del webservice
            urlprevia=webService+"listarSujetos.php";
            direccion = new URL(urlprevia);
            //Datos a enviar en POST
            data = URLEncoder.encode("pass", "UTF-8")+ "=" + URLEncoder.encode(contrasenaWS, "UTF-8");
            //Abrir conexion y envio de datos via POST
            conn= direccion.openConnection();
            conn.setDoOutput(true);
            conn.setConnectTimeout(3000);
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
            return 0;
        }
        finally{
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //Envia la respuesta del servidor a obtener los datos, verificar que haya existencia, guardar datos y continuar;
        return GuardarDatosSujetosObligador(sb.toString());
    }
    private int GuardarDatosSujetosObligador(String s) {
        try {
            idSO.clear();
            nombresSO.clear();
            JSONArray json = new JSONArray(s);
            String str = "";
            int i=0;
            while (i<json.length()){
                //for (int j=0;j<json.getJSONObject(i).length();j++)
                {
                    idSO.add(json.getJSONObject(i).getString("idSuj"));
                    nombresSO.add(json.getJSONObject(i).getString("nombre"));
                }
                i++;
            }
            return 1;

        }
        catch (Exception e)
        {
            String a=e.getMessage();
        }
        return 0;
    }
    protected int CargarSolicitud(String fecha, String idUsuario, String idNofiticaciones, String idSujeto, String nombreSujeto, String descripcion, String idTipoDeEntrega)
    {
        String data = "";
        urlprevia=webService+"nuevaSolicitud.php";
        try {
            direccion= new URL(urlprevia);
            BufferedReader reader=null;
            StringBuilder sb= new StringBuilder();
            URLConnection conn;
            OutputStreamWriter wr;

            data = URLEncoder.encode("folio", "UTF-8")+ "=" + URLEncoder.encode("000", "UTF-8");
            data += "&" + URLEncoder.encode("fecha", "UTF-8") + "="+ URLEncoder.encode(fecha, "UTF-8");
            data += "&" + URLEncoder.encode("idUsuario", "UTF-8") + "="+ URLEncoder.encode(idUsuario, "UTF-8");
            data += "&" + URLEncoder.encode("idNotificaciones", "UTF-8") + "="+ URLEncoder.encode(idNofiticaciones, "UTF-8");
            data += "&" + URLEncoder.encode("idSujeto", "UTF-8") + "="+ URLEncoder.encode(idSujeto, "UTF-8");
            data += "&" + URLEncoder.encode("nombreSujeto", "UTF-8") + "="+ URLEncoder.encode(nombreSujeto, "UTF-8");
            data += "&" + URLEncoder.encode("descripcion", "UTF-8") + "="+ URLEncoder.encode(descripcion, "UTF-8");
            data += "&" + URLEncoder.encode("idTipoDeEntrega", "UTF-8") + "="+ URLEncoder.encode(idTipoDeEntrega, "UTF-8");

            data += "&" + URLEncoder.encode("pass", "UTF-8") + "="+ URLEncoder.encode(contrasenaWS, "UTF-8");

            //Abrir conexion y envio de datos via POST
            conn= direccion.openConnection();
            conn.setDoOutput(true);
            conn.setConnectTimeout(3000);
            wr= new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
            //Obtener respuesta del servidor
            reader= new BufferedReader(new InputStreamReader(conn.getInputStream()));
            //Leer respuesta del servidor
            while ((linea=reader.readLine())!=null)
                sb.append(linea);
            return 1;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    public int ListarSolicitudes(){
        String data = "";
        BufferedReader reader=null;
        StringBuilder sb= new StringBuilder();
        URLConnection conn;
        OutputStreamWriter wr;

        try {
            //Indica url del webservice
            urlprevia=webService+"listarSolicitud.php";
            direccion = new URL(urlprevia);
            //Datos a enviar en POST
            switch (opcion)
            {
                case 0:
                    data = URLEncoder.encode("id", "UTF-8")+ "=" + URLEncoder.encode("idAcceso", "UTF-8");
                    data += "&" + URLEncoder.encode("tabla", "UTF-8") + "="+ URLEncoder.encode("solAcceso", "UTF-8");
                    break;
                case 1:
                    data = URLEncoder.encode("id", "UTF-8")+ "=" + URLEncoder.encode("idRecurso", "UTF-8");
                    data += "&" + URLEncoder.encode("tabla", "UTF-8") + "="+ URLEncoder.encode("recRevision", "UTF-8");
                    break;
                case 2:
                    data = URLEncoder.encode("id", "UTF-8")+ "=" + URLEncoder.encode("idDemanda", "UTF-8");
                    data += "&" + URLEncoder.encode("tabla", "UTF-8") + "="+ URLEncoder.encode("demIncumplimiento", "UTF-8");
                    break;
            }
            data += "&" + URLEncoder.encode("idUsuario", "UTF-8") + "="+ URLEncoder.encode(usr.getId(), "UTF-8");
            data += "&" + URLEncoder.encode("pass", "UTF-8") + "="+ URLEncoder.encode(contrasenaWS, "UTF-8");
            //Abrir conexion y envio de datos via POST
            conn= direccion.openConnection();
            conn.setDoOutput(true);
            conn.setConnectTimeout(3000);
            wr= new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
            //Obtener respuesta del servidor
            reader= new BufferedReader(new InputStreamReader(conn.getInputStream()));
            //Leer respuesta del servidor
            while ((linea=reader.readLine())!=null)
                sb.append(linea);

        }
        catch (UnknownHostException e)
        {
            return 0;
        }
        catch (Exception ex){
            String e=ex.getMessage();
            return 0;
        }
        finally{
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //Envia la respuesta del servidor a obtener los datos, verificar que haya existencia, guardar datos y continuar;
        return GuardarDatosSolicitudes(sb.toString());
    }
    private int GuardarDatosSolicitudes(String s) {
        try {
            String t="";
            int ti=opcion;
            switch (opcion)
            {
                case 0:
                    t="idAcceso";
                    break;
                case 1:
                    t="idRecurso";
                    break;
                case 2:
                    t="idDemanda";
                    break;
            }
            solicitudes.clear();
            JSONArray json = new JSONArray(s);
            String str = "";
            int i=0;
            while (i<json.length()){
                //for (int j=0;j<json.getJSONObject(i).length();j++)
                {
                    JSONObject j = json.getJSONObject(i);
                    solicitudes.add(new SolicitudItem(opcion,j.getInt(t),j.getString("nombreSujeto"),j.getString("fecha").split(" ")[0],2));
                }
                i++;
            }
            return 1;

        }
        catch (Exception e)
        {
            String a=e.getMessage();
        }
        return 0;
    }

    public int ListarSolicitudesDeSujetoObligado(String idSujeto){
        String data = "";
        BufferedReader reader=null;
        StringBuilder sb= new StringBuilder();
        URLConnection conn;
        OutputStreamWriter wr;

        try {
            //Indica url del webservice
            urlprevia=webService+"listarSolicitudSujeto.php";
            direccion = new URL(urlprevia);
            //Datos a enviar en POST
            data = URLEncoder.encode("idSujeto", "UTF-8") + "="+ URLEncoder.encode(idSujeto, "UTF-8");
            data += "&" + URLEncoder.encode("pass", "UTF-8") + "="+ URLEncoder.encode(contrasenaWS, "UTF-8");
            //Abrir conexion y envio de datos via POST
            conn= direccion.openConnection();
            conn.setDoOutput(true);
            conn.setConnectTimeout(3000);
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
            return 0;
        }
        finally{
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //Envia la respuesta del servidor a obtener los datos, verificar que haya existencia, guardar datos y continuar;
        return GuardarDatosSolicitudesPorSujetoObligado(sb.toString());
    }
    private int GuardarDatosSolicitudesPorSujetoObligado(String s) {
        try {
            solicitudes.clear();
            JSONArray json = new JSONArray(s);
            String str = "";
            int i=0;
            while (i<json.length()){
                //for (int j=0;j<json.getJSONObject(i).length();j++)
                {
                    JSONObject j = json.getJSONObject(i);
                    solicitudes.add(new SolicitudItem(0,j.getInt("idAcceso"),j.getString("nombreSujeto"),j.getString("fecha").split(" ")[0],3));
                }
                i++;
            }
            return 1;

        }
        catch (Exception e)
        {
            String a=e.getMessage();
        }
        return 0;
    }
    protected int CargarRecurso(String id, String s, String s1, String s2, String toString, String string, String toString1, int i, String fecha)
    {
        String data = "";
        urlprevia=webService+"nuevoRecurso.php";
        try {
            direccion= new URL(urlprevia);
            BufferedReader reader=null;
            StringBuilder sb= new StringBuilder();
            URLConnection conn;
            OutputStreamWriter wr;

            data = URLEncoder.encode("idUsuario", "UTF-8")+ "=" + URLEncoder.encode(id, "UTF-8");
            data += "&" + URLEncoder.encode("folio", "UTF-8") + "="+ URLEncoder.encode(s, "UTF-8");
            data += "&" + URLEncoder.encode("idTipoDeEntrega", "UTF-8") + "="+ URLEncoder.encode(s1, "UTF-8");
            data += "&" + URLEncoder.encode("idSujeto", "UTF-8") + "="+ URLEncoder.encode(s2, "UTF-8");
            data += "&" + URLEncoder.encode("nombreSujeto", "UTF-8") + "="+ URLEncoder.encode(toString, "UTF-8");
            data += "&" + URLEncoder.encode("causa", "UTF-8") + "="+ URLEncoder.encode(string, "UTF-8");
            data += "&" + URLEncoder.encode("motivo", "UTF-8") + "="+ URLEncoder.encode(toString1, "UTF-8");
            data += "&" + URLEncoder.encode("pruebas", "UTF-8") + "="+ URLEncoder.encode(String.valueOf(i), "UTF-8");
            data += "&" + URLEncoder.encode("fecha", "UTF-8") + "="+ URLEncoder.encode(fecha, "UTF-8");

            data += "&" + URLEncoder.encode("pass", "UTF-8") + "="+ URLEncoder.encode(contrasenaWS, "UTF-8");

            //Abrir conexion y envio de datos via POST
            conn= direccion.openConnection();
            conn.setDoOutput(true);
            conn.setConnectTimeout(3000);
            wr= new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
            //Obtener respuesta del servidor
            reader= new BufferedReader(new InputStreamReader(conn.getInputStream()));
            //Leer respuesta del servidor
            while ((linea=reader.readLine())!=null)
                sb.append(linea);
            return 1;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
