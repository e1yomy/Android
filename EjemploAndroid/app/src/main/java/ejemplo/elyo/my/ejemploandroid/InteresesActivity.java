package ejemplo.elyo.my.ejemploandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class InteresesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intereses);
    }
    public void Siguiente(View v)
    {
        Intent i= new Intent(this,ResumenActivity.class);
        startActivity(i);
    }
}
