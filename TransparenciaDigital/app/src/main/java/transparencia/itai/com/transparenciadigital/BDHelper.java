package transparencia.itai.com.transparenciadigital;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by elyo_ on 27/04/2017.
 */

public class BDHelper extends SQLiteOpenHelper {
    public BDHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    String lastQuery="";
    int id;
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table ciudadano(\n" +
                "idUsuario int(5) not null, \n" +
                "correo varchar(20) not null,\n" +
                "contrasena  varchar(15) not null,\n" +
                "nombre varchar(20) not null,\n" +
                "apellidoPaterno varchar(20) not null,\n" +
                "apellidoMaterno varchar(20) not null,\n" +
                "calle varchar(20) not null,\n" +
                "numeroExterior varchar(12) not null,\n" +
                "numeroInterior varchar(12),\n" +
                "entreCalles varchar(30) not null,\n" +
                "colonia varchar(15) not null,\n" +
                "CP varchar(4) not null,\n" +
                "entidad varchar(12) not null,\n" +
                "municipio varchar(12) not null,\n" +
                "telefono varchar(12) not null);");
        db.execSQL("create table usuarios(\n" +
                "idUs int(5) not null,\n" +
                "idRol int(5) not null,\n" +
                "cuenta varchar(12) not null, \n" +
                "contrasena  varchar(15) not null,\n" +
                "correo varchar(20) not null,\n" +
                "nombre varchar(20) not null,\n" +
                "apellidoPaterno varchar(20) not null,\n" +
                "apellidoMaterno varchar(20) not null,\n" +
                "institucion varchar(20) not null);");
        db.execSQL("create table rol(\n" +
                "idRol int(5) not null,\n" +
                "tipoRol varchar(12) not null);\n");
        db.execSQL("create table solAcceso(\n" +
                "idAcceso int(5) not null,\n" +
                "folio int(10) not null,\n" +
                "fecha datetime not null,\n" +
                "idUsuario int(5) not null,\n" +
                "nombre varchar(12), \n" +
                "apellidoPaterno varchar(20),\n" +
                "apellidoMaterno varchar(20),\n" +
                "idNotificaciones int(5),\n" +
                "correo varchar(20),\n" +
                "calles varchar(12),\n" +
                "numeroExterior varchar(12),\n" +
                "colonia varchar(12),\n" +
                "CP varchar(4),\n" +
                "entidad varchar(12),\n" +
                "municipio varchar(12),\n" +
                "idSujeto int(5) not null,\n" +
                "nombreSujeto varchar(20) not null,\n" +
                "descripcion varchar(200) not null,\n" +
                "IdtipoDeEntrega int(5) not null);");
        db.execSQL("create table recRevision(\n" +
                "idRecurso int(5) not null,\n" +
                "idUsuario int(5) not null,\n" +
                "folio int(10) not null,\n" +
                "nombre varchar(12), \n" +
                "apellidoPaterno varchar(20),\n" +
                "apellidoMaterno varchar(20),\n" +
                "correo varchar(20),\n" +
                "calles varchar(12),\n" +
                "numeroExterior varchar(12),\n" +
                "colonia varchar(12),\n" +
                "CP varchar(4),\n" +
                "entidad varchar(12),\n" +
                "municipio varchar(12),\n" +
                "nombreRepresentante varchar(12), \n" +
                "apellidoPaternoRepresentante varchar(20),\n" +
                "apellidoMaternoRepresentante varchar(20),\n" +
                "correoRepresentante varchar(20),\n" +
                "idTipoDeEntrega int(5) not null,\n" +
                "nombreTercero varchar(12), \n" +
                "apellidoPaternoTercero varchar(20),\n" +
                "apellidoMaternoTercero varchar(20),\n" +
                "correotercero varchar(20),\n" +
                "callesTercero varchar(12),\n" +
                "numeroExteriorTercero varchar(12),\n" +
                "coloniaTercero varchar(12),\n" +
                "CPTercero varchar(4),\n" +
                "entidadTercero varchar(12),\n" +
                "municipioTercero varchar(12),\n" +
                "idSujeto int(5) not null,\n" +
                "nombreSujeto varchar(20) not null,\n" +
                "causa varchar(200) not null,\n" +
                "motivo varchar(200) not null,\n" +
                "pruebas varchar(200) not null,\n" +
                "fecha datetime not null);");
        db.execSQL("create table demIncumplimiento(\n" +
                "idDemanda int(5) not null,\n" +
                "idUsuario int(5) not null,\n" +
                "folio int(10) not null,\n" +
                "nombre varchar(12), \n" +
                "apellidoPaterno varchar(20),\n" +
                "apellidoMaterno varchar(20),\n" +
                "correo varchar(20),\n" +
                "calles varchar(12),\n" +
                "numeroExterior varchar(12),\n" +
                "colonia varchar(12),\n" +
                "CP varchar(4),\n" +
                "entidad varchar(12),\n" +
                "municipio varchar(12),\n" +
                "idTipoDeEntrega varchar(20) not null,\n" +
                "idSujeto int(5) not null,\n" +
                "nombreSujeto varchar(20) not null,\n" +
                "descripcion varchar(200) not null,\n" +
                "fecha datetime not null);");
        db.execSQL("create table tipoDeEntrega(\n" +
                "IdTipoDeEntrega int(5) not null,\n" +
                "tipo varchar(12) not null);");
        db.execSQL("create table notificaciones(\n" +
                "idNotificaciones int(5) not null,\n" +
                "tipoNotificacion varchar(12) not null);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public boolean insertRow(String tabla, ArrayList<String> datos)
    {
        try {
            updateId(tabla);
            getWritableDatabase().execSQL(createInsertQuery(tabla,datos));
            return true;
        }
        catch (Exception ex)
        {
            String s= ex.getMessage();
            return false;
        }
    }
    private String createInsertQuery(String tabla, ArrayList<String> datos) {
        lastQuery="INSERT INTO "+tabla+" VALUES ('"+id+"',";
        for(byte i=0;i<datos.size();i++)
        {
            if(i!=datos.size()-1)
                lastQuery+="'"+datos.get(i)+"', ";
            else
                lastQuery+="'"+datos.get(i)+"')";
        }
        return lastQuery;
    }
    public boolean deleteQuery(String t, String w, String v)
    {
        try{
            lastQuery="delete from "+t+" where "+w+" = '"+v+"'";
            getWritableDatabase().execSQL(lastQuery);
            return true;
        }
        catch (Exception ex)
        {
            String er=ex.getMessage();
            return false;
        }
    }
    public boolean updateQuery(String t, String w, String [] v)
    {
        try{

            return true;
        }
        catch (Exception ex)
        {
            String er=ex.getMessage();
            return false;
        }
    }
    void updateId(String t) {
        lastQuery="select * from "+t;
        Cursor cr= getReadableDatabase().rawQuery(lastQuery,null);
        try {
            if (cr.moveToFirst()) {
                do {
                    id= Integer.valueOf(cr.getString(0));
                } while (cr.moveToNext());
                id++;
            }
        } catch (Exception ex) {
            String er=ex.getMessage();
        }
    }

    public Cursor selectAll(String t, String c)
    {
        try{
            lastQuery="select * from "+t+" orderby "+c+" asc";
            Cursor cu=getReadableDatabase().rawQuery(lastQuery,null);
            return cu;
        }
        catch(Exception ex)
        {
            String er=ex.getMessage();
            return null;
        }
    }
}
