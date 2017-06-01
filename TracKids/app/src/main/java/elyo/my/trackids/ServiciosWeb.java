package elyo.my.trackids;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static elyo.my.trackids.ListaHijos.listaHijos;
import static elyo.my.trackids.Principal.preferences;
import static elyo.my.trackids.Principal.usuario;

/**
 * Created by elyo_ on 19/05/2017.
 */

public class ServiciosWeb {
    String webService="http://pruebastec.890m.com/servicios/";
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
    double lat,lan;
    static List<LatLng> puntos= new ArrayList<>();
    static List<String> fechaPuntos= new ArrayList<>();
    public int ExisteCuenta(String  email,String con){
        try {
            email=Crypto.Encriptar(email);
            con=Crypto.MD5(con);
            urlprevia=webService+"inicio.php";
            direccion = new URL(urlprevia);
            data = URLEncoder.encode("usu", "UTF-8")+ "=" + URLEncoder.encode(email, "UTF-8");
            data += "&" + URLEncoder.encode("pas", "UTF-8") + "="+ URLEncoder.encode(con, "UTF-8");
            data += "&" + URLEncoder.encode("pass", "UTF-8")+ "=" + URLEncoder.encode(contrasenaWS, "UTF-8");
            //Abrir conexion y envio de datos via POST
            conn= direccion.openConnection();

            conn.setDoOutput(true);
            wr= new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
            reader= new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((linea=reader.readLine())!=null)
                sb.append(linea);
            JSONArray json=new JSONArray(sb.toString());
            if (json.length()>0)
            {
                preferences.edit()
                        .putString("idUsuario", json.getJSONObject(0).getString("idUs"))
                        .putString("correoUsuario", Crypto.Desencriptar(json.getJSONObject(0).getString("nomUs")))
                        .putString("nombresUsuario", Crypto.Desencriptar(json.getJSONObject(0).getString("nombres")))
                        .putString("apellidosUsuario", Crypto.Desencriptar(json.getJSONObject(0).getString("apellidos")))
                        .putString("telefonoUsuario", Crypto.Desencriptar(json.getJSONObject(0).getString("telefono")))
                        .putString("contrasenaUsuario", Crypto.Desencriptar(json.getJSONObject(0).getString("contrasena")))
                        .putString("pinUsuario", json.getJSONObject(0).getString("pin"))
                        .commit();
                usuario =new Usuario(
                        Crypto.Desencriptar(json.getJSONObject(0).getString("nomUs")),
                        Crypto.Desencriptar(json.getJSONObject(0).getString("nombres")),
                        Crypto.Desencriptar(json.getJSONObject(0).getString("apellidos")),
                        Crypto.Desencriptar(json.getJSONObject(0).getString("telefono")),
                        Crypto.Desencriptar(json.getJSONObject(0).getString("contrasena")),
                        json.getJSONObject(0).getString("pin"));
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
        return 0;
    }

    public int GuardarCuenta(String nombres, String apellidos, String correo, String contrasena, String telefono, String pin) {
        try {
            nombres=Crypto.Encriptar(nombres);
            apellidos=Crypto.Encriptar(apellidos);
            correo=Crypto.Encriptar(correo);
            contrasena= Crypto.MD5(contrasena);
            telefono=Crypto.Encriptar(telefono);
            urlprevia = webService + "registroUsuarios.php";
            direccion = new URL(urlprevia);
            data =        URLEncoder.encode("usu", "UTF-8") + "=" + URLEncoder.encode(correo, "UTF-8");
            data += "&"+  URLEncoder.encode("nom", "UTF-8") + "=" + URLEncoder.encode(nombres, "UTF-8");
            data += "&" + URLEncoder.encode("ape", "UTF-8") + "=" + URLEncoder.encode(apellidos, "UTF-8");
            data += "&" + URLEncoder.encode("tel", "UTF-8") + "=" + URLEncoder.encode(telefono, "UTF-8");
            data += "&" + URLEncoder.encode("pas", "UTF-8") + "=" + URLEncoder.encode(contrasena, "UTF-8");
            data += "&" + URLEncoder.encode("pin", "UTF-8") + "=" + URLEncoder.encode(pin, "UTF-8");

            data += "&" + URLEncoder.encode("pass", "UTF-8") + "=" + URLEncoder.encode(contrasenaWS, "UTF-8");
            conn = direccion.openConnection();
            conn.setDoOutput(true);
            wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((linea = reader.readLine()) != null)
                sb.append(linea);
            return 1;
        } catch (Exception ex) {
            String s= ex.getMessage();
                    return 0;
        }
    }
    public int CrearRegistroUltimaUbicacion(String id) {
        try {
            urlprevia = webService + "crearDatosUltimaUbicacion.php";
            direccion = new URL(urlprevia);
            data =        URLEncoder.encode("idUs", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
            data += "&" + URLEncoder.encode("latitud", "UTF-8") + "=" + URLEncoder.encode("0", "UTF-8");
            data += "&" + URLEncoder.encode("longitud", "UTF-8") + "=" + URLEncoder.encode("0", "UTF-8");
            data += "&" + URLEncoder.encode("pass", "UTF-8") + "=" + URLEncoder.encode(contrasenaWS, "UTF-8");
            conn = direccion.openConnection();
            conn.setDoOutput(true);
            wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((linea = reader.readLine()) != null)
                sb.append(linea);
            return 1;
        } catch (Exception ex) {
            String s= ex.getMessage();
            return 0;
        }
    }
    public int ActualizarRegistroUltimaUbicacion(String id, double lat, double lan) {
        try {
            urlprevia = webService + "actualizarUltimaUbicacion.php";
            direccion = new URL(urlprevia);
            data =        URLEncoder.encode("idUs", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
            data +=       URLEncoder.encode("latitutd", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(lat), "UTF-8");
            data +=       URLEncoder.encode("longitud", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(lan), "UTF-8");

            data += "&" + URLEncoder.encode("pass", "UTF-8") + "=" + URLEncoder.encode(contrasenaWS, "UTF-8");
            conn = direccion.openConnection();
            conn.setDoOutput(true);
            wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((linea = reader.readLine()) != null)
                sb.append(linea);
            return 1;
        } catch (Exception ex) {
            String s= ex.getMessage();
            return 0;
        }
    }

    public int ExisteUsuario(String email, String pin) {
        try {
            email=Crypto.Encriptar(email);
            urlprevia=webService+"conexion.php";
            direccion = new URL(urlprevia);
            data = URLEncoder.encode("usu", "UTF-8")+ "=" + URLEncoder.encode(email, "UTF-8");
            data += "&" + URLEncoder.encode("pin", "UTF-8") + "="+ URLEncoder.encode(pin, "UTF-8");
            data += "&" + URLEncoder.encode("pass", "UTF-8")+ "=" + URLEncoder.encode(contrasenaWS, "UTF-8");
            conn= direccion.openConnection();
            conn.setDoOutput(true);
            wr= new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
            reader= new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((linea=reader.readLine())!=null)
                sb.append(linea);
            JSONArray json=new JSONArray(sb.toString());
            if (json.length()>0)
            {
                return CrearConexion(preferences.getString("idUsuario",""),json.getJSONObject(0).getString("idUs"));
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
        return 0;
    }

    private int CrearConexion(String id, String idUs) {
        try {
            urlprevia = webService + "conexion2.php";
            direccion = new URL(urlprevia);
            data =        URLEncoder.encode("padre", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
            data += "&"+  URLEncoder.encode("hijo", "UTF-8") + "=" + URLEncoder.encode(idUs, "UTF-8");
            data += "&" + URLEncoder.encode("pass", "UTF-8") + "=" + URLEncoder.encode(contrasenaWS, "UTF-8");
            conn = direccion.openConnection();
            conn.setDoOutput(true);
            wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((linea = reader.readLine()) != null)
                sb.append(linea);

            return 1;
        } catch (Exception ex) {
            String s= ex.getMessage();
            return 0;
        }

    }

    private int EliminarCuenta(String emil, String con)
    {
        return 1;
    }

    protected int CargarUbicacion(String id, double la, double lo, String fecha) {
        try {
            String lat= Crypto.Encriptar(String.valueOf(la));
            String lon=Crypto.Encriptar(String.valueOf(lo));
            urlprevia = webService + "ubicacion.php";
            direccion = new URL(urlprevia);
            data = URLEncoder.encode("idUs", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
            data += "&" + URLEncoder.encode("latitud", "UTF-8") + "=" + URLEncoder.encode(lat, "UTF-8");
            data += "&" + URLEncoder.encode("longitud", "UTF-8") + "=" + URLEncoder.encode(lon, "UTF-8");
            data += "&" + URLEncoder.encode("fecha", "UTF-8") + "=" + URLEncoder.encode(fecha, "UTF-8");
            data += "&" + URLEncoder.encode("pass", "UTF-8") + "=" + URLEncoder.encode(contrasenaWS, "UTF-8");
            conn = direccion.openConnection();
            conn.setDoOutput(true);
            wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((linea = reader.readLine()) != null)
                sb.append(linea);

            return 1;
        } catch (Exception ex) {
            String s = ex.getMessage();
            return 0;
        }
    }
    protected int ListaDeHijos(String id)
    {

        try {
            urlprevia=webService+"listarHijos.php";
            direccion = new URL(urlprevia);
            data = URLEncoder.encode("idUs", "UTF-8")+ "=" + URLEncoder.encode(id, "UTF-8");
            data += "&" + URLEncoder.encode("pass", "UTF-8")+ "=" + URLEncoder.encode(contrasenaWS, "UTF-8");
            conn= direccion.openConnection();
            conn.setDoOutput(true);
            wr= new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
            reader= new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((linea=reader.readLine())!=null)
                sb.append(linea);
            JSONArray json=new JSONArray(sb.toString());
            if (json.length()>0)
            {
                    listaHijos.clear();
                JSONObject j;
                for(int i=0;i<json.length();i++)
                {
                    j = json.getJSONObject(i);
                    UltimaUbicacionHijo(j.getString("idUs"));
                    listaHijos.add(new Hijo(j.getString("idUs"),Crypto.Desencriptar(j.getString("nombres")),String.valueOf(lat),String.valueOf(lan),Crypto.Desencriptar(j.getString("telefono"))));
                }
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
        return 0;
    }
    private void UltimaUbicacionHijo(String id){
        try {
            urlprevia=webService+"ultimaUbicacion.php";
            direccion = new URL(urlprevia);
            String consulta = "select latitud, longitud from `ubicaciones` where idUs = "+id+" order by idUb DESC LIMIT 1";
            data = URLEncoder.encode("consulta", "UTF-8")+ "=" + URLEncoder.encode(consulta, "UTF-8");
            data += "&" + URLEncoder.encode("pass", "UTF-8")+ "=" + URLEncoder.encode(contrasenaWS, "UTF-8");
            conn= direccion.openConnection();
            conn.setDoOutput(true);
            wr= new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
            reader= new BufferedReader(new InputStreamReader(conn.getInputStream()));
            linea="";
            sb=new StringBuilder();
            while ((linea=reader.readLine())!=null)
                sb.append(linea);
            JSONArray json=new JSONArray(sb.toString());
            if (json.length()>0)
            {
                lat = Double.parseDouble(Crypto.Desencriptar(json.getJSONObject(0).getString("latitud")));
                lan = Double.parseDouble(Crypto.Desencriptar(json.getJSONObject(0).getString("longitud")));
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
    }

    protected int UltimasUbicacionesHijo(String id){
        try {
            puntos.clear();
            fechaPuntos.clear();
            urlprevia=webService+"ultimaUbicacion.php";
            direccion = new URL(urlprevia);
            String consulta = "select latitud, longitud, fecha from `ubicaciones` where idUs = "+id+" order by idUb DESC LIMIT 30";
            data = URLEncoder.encode("consulta", "UTF-8")+ "=" + URLEncoder.encode(consulta, "UTF-8");
            data += "&" + URLEncoder.encode("pass", "UTF-8")+ "=" + URLEncoder.encode(contrasenaWS, "UTF-8");
            conn= direccion.openConnection();
            conn.setDoOutput(true);
            wr= new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
            reader= new BufferedReader(new InputStreamReader(conn.getInputStream()));
            linea="";
            sb=new StringBuilder();
            while ((linea=reader.readLine())!=null)
                sb.append(linea);

            JSONArray json=new JSONArray(sb.toString());
            for(int i=0;i<json.length();i++)
            {
                lat = Double.parseDouble(Crypto.Desencriptar(json.getJSONObject(i).getString("latitud")));
                lan = Double.parseDouble(Crypto.Desencriptar(json.getJSONObject(i).getString("longitud")));
                puntos.add(new LatLng(lat,lan));
                fechaPuntos.add(json.getJSONObject(i).getString("fecha").split(" ")[1]);
            }

            return 1;
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
    }

    protected int MostrarRutaFecha(String id, String fecha){
        try {
            puntos.clear();
            fechaPuntos.clear();
            urlprevia=webService+"ultimaUbicacion.php";
            direccion = new URL(urlprevia);
            String consulta = "select latitud, longitud, fecha from `ubicaciones` where idUs = "+id+" and fecha like '"+fecha+"%' order by idUb ASC";
            data = URLEncoder.encode("consulta", "UTF-8")+ "=" + URLEncoder.encode(consulta, "UTF-8");
            data += "&" + URLEncoder.encode("pass", "UTF-8")+ "=" + URLEncoder.encode(contrasenaWS, "UTF-8");
            conn= direccion.openConnection();

            conn.setDoOutput(true);
            wr= new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
            reader= new BufferedReader(new InputStreamReader(conn.getInputStream()));
            linea="";
            sb=new StringBuilder();
            while ((linea=reader.readLine())!=null)
                sb.append(linea);

            JSONArray json=new JSONArray(sb.toString());
            if (json.length()==0)
                return 0;
            for(int i=0;i<json.length();i++)
            {
                lat = Double.parseDouble(Crypto.Desencriptar(json.getJSONObject(i).getString("latitud")));
                lan = Double.parseDouble(Crypto.Desencriptar(json.getJSONObject(i).getString("longitud")));
                puntos.add(new LatLng(lat,lan));
                fechaPuntos.add(json.getJSONObject(i).getString("fecha").split(" ")[1]);
            }

            return 1;
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
    }
    protected int EliminarHijo(String padre, String hijo){
        try {
            puntos.clear();
            fechaPuntos.clear();
            urlprevia=webService+"eliminarHijo.php";
            direccion = new URL(urlprevia);
            data = URLEncoder.encode("idUs", "UTF-8")+ "=" + URLEncoder.encode(padre, "UTF-8");
            data += "&" + URLEncoder.encode("idHijo", "UTF-8")+ "=" + URLEncoder.encode(hijo, "UTF-8");
            data += "&" + URLEncoder.encode("pass", "UTF-8")+ "=" + URLEncoder.encode(contrasenaWS, "UTF-8");
            conn= direccion.openConnection();
            conn.setDoOutput(true);
            wr= new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
            reader= new BufferedReader(new InputStreamReader(conn.getInputStream()));
            linea="";
            sb=new StringBuilder();
            while ((linea=reader.readLine())!=null)
                sb.append(linea);
            return 1;
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
    }
}

