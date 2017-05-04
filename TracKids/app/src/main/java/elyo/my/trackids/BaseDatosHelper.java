package elyo.my.trackids;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

/**
 * Created by elyo_ on 03/05/2017.
 */

public class BaseDatosHelper extends SQLiteOpenHelper {

    String lastQuery = "";
    String table = "";
    public static Integer id = 1;
    public static String er = "";

    public BaseDatosHelper(Context context) {
        super(context, "kids", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Ejecutar la creacion de las tablas de la base de datos
        db.execSQL("create table mislugares (" +
                "usuario text not null," +
                "nombre text not null," +
                "lat text not null," +
                "lng text not null);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertRow(String t, List<String> v) {
        try {

            getWritableDatabase()
                    .execSQL(createInsertQuery(t, v));
            return true;
        } catch (Exception ex) {
            er = ex.getMessage();
            return false;
        }
    }

    String createInsertQuery(String t, List<String> v) {
        lastQuery = "INSERT INTO " + t + " VALUES (";
        for (byte i = 0; i < v.size(); i++) {
            if (i != v.size() - 1)
                lastQuery += "'" + v.get(i) + "', ";
            else
                lastQuery += "'" + v.get(i) + "')";
        }
        return lastQuery;
    }

    public Cursor selectLugares(String usuario) {
        try{
            lastQuery="select nombre,lat,lng from mislugares where usuario = '"+usuario+"'";
            return getReadableDatabase().rawQuery(lastQuery,null);
        }
        catch (Exception ex)
        {
            er = ex.getMessage();
            return null;
        }
    }

    public boolean eliminarLugar(String usuario, String nombre) {
        try {
            lastQuery = "delete from mislugares where usuario='"+usuario+"' and nombre = '"+nombre+"'";
            getWritableDatabase().execSQL(lastQuery);
            return true;
        } catch (Exception ex) {
            er = ex.getMessage();
            return false;
        }
    }

    public Cursor selectAll(String t) {
        try {
            lastQuery = "select * from " + t;
            Cursor cu = getReadableDatabase().rawQuery(lastQuery, null);
            return cu;
        } catch (Exception ex) {
            er = ex.getMessage();
            return null;
        }
    }

    public Cursor select(String s) {
        try {
            Cursor cu = getReadableDatabase().rawQuery(s, null);
            return cu;
        } catch (Exception ex) {
            er = ex.getMessage();
            return null;
        }
    }

    public Cursor selectRow(String t, String w, String v) {
        lastQuery = "select * from " + t + " where " + w + "='" + v + "'";
        Cursor c = getReadableDatabase().rawQuery(lastQuery, null);
        return c;
    }

    public Cursor selectRow(String s) {

        return null;
    }

    public Usuario buscarUsuario(String ur) {
        try {
            Usuario u = new Usuario();
            lastQuery = "select * from usuarios where usuario='" + ur + "'";
            Cursor c = getReadableDatabase().rawQuery(lastQuery, null);
            /*
            u.setUsuario(c.getString(1));
            u.setTelefono(c.getString(2));
            u.setContrasena(c.getString(3));
            u.setNombre(c.getString(4));
            u.setApellido(c.getString(5));
            u.setSexo(c.getString(6));
            u.setEdad(c.getString(7));
            u.setCiudad(c.getString(8));
            u.setDescripcion(c.getString(9));
            */
            return u;
        } catch (Exception ex) {
            return null;
        }
    }

    public boolean deleteQuery(String t, String w, String v) {
        try {
            lastQuery = "delete from " + t + " where " + w + " = '" + v + "'";
            getWritableDatabase().execSQL(lastQuery);
            return true;
        } catch (Exception ex) {
            er = ex.getMessage();
            return false;
        }
    }

    public boolean updateQuery(String t, String w, String[] v) {
        try {

            return true;
        } catch (Exception ex) {
            er = ex.getMessage();
            return false;
        }
    }

    public Cursor selectAll(String t, String c) {
        try {
            lastQuery = "select * from " + t + " orderby " + c + " asc";
            Cursor cu = getReadableDatabase().rawQuery(lastQuery, null);
            return cu;
        } catch (Exception ex) {
            er = ex.getMessage();
            return null;
        }
    }
}
