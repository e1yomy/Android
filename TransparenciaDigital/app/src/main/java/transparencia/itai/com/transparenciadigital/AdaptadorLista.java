package transparencia.itai.com.transparenciadigital;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static transparencia.itai.com.transparenciadigital.MisSolicitudes.opcion;

/**
 * Created by elyo_ on 05/06/2017.
 */

public class AdaptadorLista extends BaseAdapter {
    //ArrayList<SolicitudItem> solicitudItems;
    List<SolicitudItem> solicitudItems;
    Context context;
    byte tabla;
    @Override
    public int getCount() {
        return solicitudItems.size();
    }

    @Override
    public Object getItem(int position) {
        return solicitudItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return solicitudItems.get(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null) {
            try {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View renglon = inflater.inflate(R.layout.solicituditem, null);
                SolicitudItem s = solicitudItems.get(position);
                if (s.tipo == opcion) {
                    TextView txtSujetoObligado = (TextView) renglon.findViewById(R.id.txtSujetoObligado);
                    TextView txtFecha = (TextView) renglon.findViewById(R.id.txtFecha);
                    ImageView imgTipoSolicitud = (ImageView) renglon.findViewById(R.id.imgTipoSolicitud);
                    ImageView imgSemaforo = (ImageView) renglon.findViewById(R.id.imgSemaforo);
                    switch (s.tipo) {
                        case 0:
                            imgTipoSolicitud.setImageResource(R.drawable.ic_sol_informacion);
                            break;
                        case 1:
                            imgTipoSolicitud.setImageResource(R.drawable.ic_recurso);
                            break;
                        case 2:
                            imgTipoSolicitud.setImageResource(R.drawable.ic_incumplimiento);
                            break;
                    }
                    txtSujetoObligado.setText(s.sujetoObligado);
                    txtSujetoObligado.setSingleLine();
                    txtFecha.setText(s.fecha);

                    switch (s.estado) {
                        case 0:
                            imgSemaforo.setImageResource(R.mipmap.ic_green);
                            break;
                        case 1:
                            imgSemaforo.setImageResource(R.mipmap.ic_yellow);
                            break;
                        case 2:
                            imgSemaforo.setImageResource(R.mipmap.ic_brown);
                            break;
                        case 3:
                            imgSemaforo.setImageResource(R.mipmap.ic_red);
                            break;
                        case 4:
                            imgSemaforo.setImageResource(R.mipmap.ic_blue);
                            break;
                    }
                }
                else {

                }
                return renglon;
            }
            catch (Exception ex){
                String s= ex.getMessage();
                Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
            }
        }
        return convertView;
    }

    public AdaptadorLista(Context context, List<SolicitudItem> solicitudItems){
        this.solicitudItems=solicitudItems;
        this.context=context;
    }
}
