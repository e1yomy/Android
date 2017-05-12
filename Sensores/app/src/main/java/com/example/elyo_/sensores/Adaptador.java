package com.example.elyo_.sensores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Centro de Computo on 28/04/2017.
 */

public class Adaptador extends BaseAdapter {
    public String[] valores;
    public Context context;

    public Adaptador(String[] valores, Context context)
    {
        this.valores = valores;
        this.context = context;
    }

    @Override
    public int getCount() {
        return valores.length;
    }

    @Override
    public Object getItem(int position) {
        return valores[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater li = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View renglon = li.inflate(R.layout.renglon, null);
            TextView texto = (TextView)renglon.findViewById(R.id.texto);
            texto.setText(valores[position]);
            return renglon;
        }
        return convertView;
    }
}
