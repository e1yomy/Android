package com.example.elyo_.ejemplolistapersonalizada;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<Platillo> platillos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            platillos = new ArrayList<Platillo>();
            platillos.add(new Platillo(0, "carne", "asada", 120));
            platillos.add(new Platillo(0, "papa", "rellena", 100));
            platillos.add(new Platillo(0, "tosti", "locos", 80));

            ListView lv = (ListView) findViewById(R.id.listaMenu);
            AdaptadorPlatillo adaptador = new AdaptadorPlatillo(platillos, this);
            lv.setAdapter(adaptador);


        }
        catch (Exception ex)
        {
            String exs=ex.getMessage();
        }

    }
}
