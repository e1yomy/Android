package com.example.elyo_.ejemplolistapersonalizada;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.concurrent.TimeoutException;
import java.util.zip.Inflater;

/**
 * Created by elyo_ on 27/04/2017.
 */

public class AdaptadorPlatillo extends BaseAdapter{
    ArrayList<Platillo> platillos;
    Context context;
    public AdaptadorPlatillo(ArrayList<Platillo> platillos, Context context){
        this.platillos=platillos;
        this.context=context;
    }

    @Override
    public int getCount() {
        return platillos.size();
    }

    @Override
    public Object getItem(int position) {
        return platillos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            try {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View renglon = inflater.inflate(R.layout.renglon, null);
                final TextView cantidad = (TextView) renglon.findViewById(R.id.txtCantidad);
                TextView nombre = (TextView) renglon.findViewById(R.id.txtNombre);
                TextView descripcion = (TextView) renglon.findViewById(R.id.txtDescripcion);
                TextView precio = (TextView) renglon.findViewById(R.id.txtPrecio);
                final TextView costo = (TextView) renglon.findViewById(R.id.txtCosto);
                FloatingActionButton mas=(FloatingActionButton)renglon.findViewById(R.id.btnmas);
                FloatingActionButton menos=(FloatingActionButton)renglon.findViewById(R.id.btnMenos);

                cantidad.setText(""+platillos.get(position).cantidad);
                nombre.setText(platillos.get(position).nombre);
                descripcion.setText(platillos.get(position).descripcion);
                precio.setText("$ " + platillos.get(position).precio);
                costo.setText("$ " + platillos.get(position).precio*platillos.get(position).cantidad);


                mas.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(platillos.get(position).cantidad>=0) {
                            platillos.get(position).cantidad = platillos.get(position).cantidad + 1;
                            cantidad.setText("" + platillos.get(position).cantidad);
                            costo.setText("$ " + platillos.get(position).precio*platillos.get(position).cantidad);
                        }
                    }
                });
                menos.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(platillos.get(position).cantidad>0) {
                            platillos.get(position).cantidad = platillos.get(position).cantidad - 1;
                            cantidad.setText("" + platillos.get(position).cantidad);
                            costo.setText("$ " + platillos.get(position).precio*platillos.get(position).cantidad);
                        }
                    }
                });


                return renglon;
            }
            catch (Exception ex)
            {
                String exx=ex.getMessage();
            }

        }
        return convertView;
    }
}
