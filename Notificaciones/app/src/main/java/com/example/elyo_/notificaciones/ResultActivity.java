package com.example.elyo_.notificaciones;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    private TextView textView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        textView = (TextView)findViewById(R.id.textView1);
        Bundle bundle = getIntent().getExtras();
        String mensaje = bundle.getString("Mensaje");
        textView.setText("Mensaje Recibido: \""+mensaje+"\"");
    }
}
