package ejemplo.elyo.my.ejemploandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ResumenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen);
        cargar();
    }
    public void cargar()
    {
        TextView t1=(TextView)findViewById(R.id.txt2);
        t1.setText(getIntent().getExtras().getString("usuario"));
        TextView t2=(TextView)findViewById(R.id.txt4);
        t2.setText(getIntent().getExtras().getString("nombre"));
        TextView t3=(TextView)findViewById(R.id.txt6);
        t3.setText(getIntent().getExtras().getString("genero"));
        TextView t4=(TextView)findViewById(R.id.txt8);
        t4.setText(getIntent().getExtras().getString("ocupacion"));


    }
}
