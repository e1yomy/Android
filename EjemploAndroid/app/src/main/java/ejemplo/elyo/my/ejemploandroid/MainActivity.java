package ejemplo.elyo.my.ejemploandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
    public void Entrar(View v)
    {
        EditText editUsuario= (EditText)findViewById(R.id.editUsuario);
        EditText editcontrasenia= (EditText)findViewById(R.id.editcontrasenia);
        String usuario=editUsuario.getText().toString(), contra=editcontrasenia.getText().toString();
        if(contra.equals("123")) {
            Intent i = new Intent(this, DatosPersonalesActivity.class);
            i.putExtra("usuario",usuario);

            startActivity(i);
        }
    }
}
