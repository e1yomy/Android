package transparencia.itai.com.transparenciadigital;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;

/**
 * Created by elyo_ on 27/04/2017.
 */

public class BDHelper extends SQLiteOpenHelper {
    public BDHelper(Context context) {
        super(context, "itai", null, 1);
    }
    String lastQuery="";
    int id;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table sujetos(\n" +
                "\tid text,\n" +
                "\tnombre text\n" +
                ");");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public boolean InsertarFila(String tabla, ArrayList<String> datos)
    {
        try {

            getWritableDatabase().execSQL(createInsertQuery(tabla,datos));
            return true;
        }
        catch (Exception ex)
        {
            String s= ex.getMessage();
            return false;
        }
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
