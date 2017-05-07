package elyo.my.trackids;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Map;

import static elyo.my.trackids.Principal.preferences;

/**
 * Created by elyo_ on 06/05/2017.
 */

public class AdaptadorLista extends BaseAdapter{
    SharedPreferences p ;
    ArrayList<Hijo> hijos;
    Context context;


    public AdaptadorLista(ArrayList<Hijo> hijos, Context context){
        this.hijos=hijos;
        this.context=context;
    }

    @Override
    public int getCount() {
        return hijos.size();
    }

    @Override
    public Object getItem(int position) {
        return hijos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            try {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View renglon = inflater.inflate(R.layout.lista_personalizada_hijos, null);
                TextView txtNombreHijo = (TextView) renglon.findViewById(R.id.txtNombreHijo);
                TextView txtUbicacionHijo = (TextView) renglon.findViewById(R.id.txtUbicacionHijo);
                FloatingActionButton btnLlamar = (FloatingActionButton) renglon.findViewById(R.id.btnLlamar);
                LinearLayout layoutDatos = (LinearLayout) renglon.findViewById(R.id.layoutDatos);
                txtNombreHijo.setText(hijos.get(position).nombre);
                //Verificar distancia con lugares guardados y en caso de no estar dentro de la distancia, mostrar lo siguiente
                txtUbicacionHijo.setText(hijos.get(position).latitud+", "+hijos.get(position).longitud);

                Clic(layoutDatos,position);
                Llamar(btnLlamar, position);
                return renglon;
            }
            catch (Exception ex){
                Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        return convertView;
    }

    private void Clic(LinearLayout layoutDatos, final int position) {

        layoutDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences.edit().putString("nombre",hijos.get(position).nombre).commit();
                preferences.edit().putFloat("lat", (float) hijos.get(position).latitud).commit();
                preferences.edit().putFloat("lon", (float) hijos.get(position).longitud).commit();

            }
        });
    }

    private void Llamar(FloatingActionButton btnLlamar, final int position) {
        try {

            btnLlamar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + "6121972191"));//hijos.get(position).telefono));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    context.startActivity(intent);

                }
            });

        }
        catch (Exception e)
        {
            String s= e.getMessage();
        }
    }

}
