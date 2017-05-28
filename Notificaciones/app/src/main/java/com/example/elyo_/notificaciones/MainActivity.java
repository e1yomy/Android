package com.example.elyo_.notificaciones;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            TextView text= (TextView)findViewById(R.id.text);
        String texto="";
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            Calendar cal = Calendar.getInstance();
            cal.setTime(java.util.Calendar.getInstance());
            int month = cal.get(Calendar.MONTH);
            int dd = cal.get(Calendar.DATE);
            int yer = cal.get(Calendar.YEAR);
            text.setText();
        }
        catch (Exception ex)
        {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
