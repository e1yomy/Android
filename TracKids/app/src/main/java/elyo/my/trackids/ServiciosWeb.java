package elyo.my.trackids;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

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
    public int ExisteCuenta(String email,String con){
        try {
            //Indica url del webservice
            urlprevia=webService+"inicio.php";
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
                        .putString("contrasenaUsuario",json.getJSONObject(0).getString("contrasena"))
                        .putString("pinUsuario",json.getJSONObject(0).getString("pin"))
                        .commit();
                usuario =new Usuario(
                        json.getJSONObject(0).getString("nomUs"),
                        json.getJSONObject(0).getString("nombres"),
                        json.getJSONObject(0).getString("apellidos"),
                        json.getJSONObject(0).getString("telefono"),
                        json.getJSONObject(0).getString("contrasena"),
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
            //Indica url del webservice
            urlprevia = webService + "registroUsuarios.php";
            direccion = new URL(urlprevia);
            //Datos a enviar en POST
            data =        URLEncoder.encode("usu", "UTF-8") + "=" + URLEncoder.encode(correo, "UTF-8");
            data += "&"+  URLEncoder.encode("nom", "UTF-8") + "=" + URLEncoder.encode(nombres, "UTF-8");
            data += "&" + URLEncoder.encode("ape", "UTF-8") + "=" + URLEncoder.encode(apellidos, "UTF-8");
            data += "&" + URLEncoder.encode("tel", "UTF-8") + "=" + URLEncoder.encode(telefono, "UTF-8");
            data += "&" + URLEncoder.encode("pas", "UTF-8") + "=" + URLEncoder.encode(contrasena, "UTF-8");
            data += "&" + URLEncoder.encode("pin", "UTF-8") + "=" + URLEncoder.encode(pin, "UTF-8");

            data += "&" + URLEncoder.encode("pass", "UTF-8") + "=" + URLEncoder.encode(contrasenaWS, "UTF-8");
            //Abrir conexion y envio de datos via POST
            conn = direccion.openConnection();
            conn.setDoOutput(true);
            wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
            //Obtener respuesta del servidor
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            //Leer respuesta del servidor
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
            //Indica url del webservice
            urlprevia = webService + "crearDatosUltimaUbicacion.php";
            direccion = new URL(urlprevia);
            //Datos a enviar en POST
            data =        URLEncoder.encode("idUs", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
            data += "&" + URLEncoder.encode("latitud", "UTF-8") + "=" + URLEncoder.encode("0", "UTF-8");
            data += "&" + URLEncoder.encode("longitud", "UTF-8") + "=" + URLEncoder.encode("0", "UTF-8");
            data += "&" + URLEncoder.encode("pass", "UTF-8") + "=" + URLEncoder.encode(contrasenaWS, "UTF-8");
            //Abrir conexion y envio de datos via POST
            conn = direccion.openConnection();
            conn.setDoOutput(true);
            wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
            //Obtener respuesta del servidor
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            //Leer respuesta del servidor
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
            //Indica url del webservice
            urlprevia = webService + "actualizarUltimaUbicacion.php";
            direccion = new URL(urlprevia);
            //Datos a enviar en POST
            data =        URLEncoder.encode("idUs", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
            data += "&" + URLEncoder.encode("latitutd", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(lat), "UTF-8");
            data += "&" + URLEncoder.encode("longitud", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(lan), "UTF-8");

            data += "&" + URLEncoder.encode("pass", "UTF-8") + "=" + URLEncoder.encode(contrasenaWS, "UTF-8");
            //Abrir conexion y envio de datos via POST
            conn = direccion.openConnection();
            conn.setDoOutput(true);
            wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
            //Obtener respuesta del servidor
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            //Leer respuesta del servidor
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
            //Indica url del webservice
            urlprevia=webService+"conexion.php";
            direccion = new URL(urlprevia);
            //Datos a enviar en POST
            data = URLEncoder.encode("usu", "UTF-8")+ "=" + URLEncoder.encode(email, "UTF-8");
            data += "&" + URLEncoder.encode("pin", "UTF-8") + "="+ URLEncoder.encode(pin, "UTF-8");
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
                return CrearConexion(preferences.getString("idUsuario",""),json.getJSONObject(0).getString("idUs"));
                //return 1;
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
            //Indica url del webservice
            urlprevia = webService + "conexion2.php";
            direccion = new URL(urlprevia);
            //Datos a enviar en POST
            data =        URLEncoder.encode("padre", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
            data += "&"+  URLEncoder.encode("hijo", "UTF-8") + "=" + URLEncoder.encode(idUs, "UTF-8");
            data += "&" + URLEncoder.encode("pass", "UTF-8") + "=" + URLEncoder.encode(contrasenaWS, "UTF-8");
            //Abrir conexion y envio de datos via POST
            conn = direccion.openConnection();
            conn.setDoOutput(true);
            wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
            //Obtener respuesta del servidor
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            //Leer respuesta del servidor
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

    protected int CargarUbicacion(String id, double lat, double lon, String fecha) {
        try {
            //Indica url del webservice
            urlprevia = webService + "ubicacion.php";
            direccion = new URL(urlprevia);
            //Datos a enviar en POST
            data = URLEncoder.encode("idUs", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
            data += "&" + URLEncoder.encode("latitud", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(lat), "UTF-8");
            data += "&" + URLEncoder.encode("longitud", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(lon), "UTF-8");
            data += "&" + URLEncoder.encode("fecha", "UTF-8") + "=" + URLEncoder.encode(fecha, "UTF-8");
            data += "&" + URLEncoder.encode("pass", "UTF-8") + "=" + URLEncoder.encode(contrasenaWS, "UTF-8");
            //Abrir conexion y envio de datos via POST
            conn = direccion.openConnection();
            conn.setDoOutput(true);
            wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
            //Obtener respuesta del servidor
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            //Leer respuesta del servidor
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
            //Indica url del webservice
            urlprevia=webService+"listarHijos.php";
            direccion = new URL(urlprevia);
            //Datos a enviar en POST
            data = URLEncoder.encode("idUs", "UTF-8")+ "=" + URLEncoder.encode(id, "UTF-8");
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
                    listaHijos.clear();
                JSONObject j;
                for(int i=0;i<json.length();i++)
                {
                    j = json.getJSONObject(i);
                    UltimaUbicacionHijo(j.getString("idUs"));
                    listaHijos.add(new Hijo(j.getString("idUs"),j.getString("nombres"),lat,lan,j.getString("telefono")));
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
            //Indica url del webservice
            urlprevia=webService+"ultimaUbicacion.php";
            direccion = new URL(urlprevia);
            //Datos a enviar en POST
            //data = URLEncoder.encode("id", "UTF-8")+ "=" + URLEncoder.encode(id, "UTF-8");
            String consulta = "select latitud, longitud from `ubicaciones` where idUs = "+id+" order by idUb DESC LIMIT 1";
            data = URLEncoder.encode("consulta", "UTF-8")+ "=" + URLEncoder.encode(consulta, "UTF-8");
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
            linea="";
            sb=new StringBuilder();
            while ((linea=reader.readLine())!=null)
                sb.append(linea);

            JSONArray json=new JSONArray(sb.toString());
            if (json.length()>0)
            {
                lat = json.getJSONObject(0).getDouble("latitud");
                lan = json.getJSONObject(0).getDouble("longitud");
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
}

