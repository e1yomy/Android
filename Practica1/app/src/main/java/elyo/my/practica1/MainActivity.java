package elyo.my.practica1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText editFuerza, editMasa, editAngulo;
    TextView posX, posY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editFuerza=(EditText) findViewById(R.id.editFuerza);
        editMasa=(EditText) findViewById(R.id.editMasa);
        editAngulo=(EditText) findViewById(R.id.editAngulo);
        posX=(TextView)findViewById(R.id.textPosX);
        posY=(TextView)findViewById(R.id.textPosY);
    }
    public void onClic(View v) {
        try {
        double fuerza=Double.parseDouble(editFuerza.getText().toString());
        double masa=Double.parseDouble(editMasa.getText().toString());
        double angulo=Double.parseDouble(editAngulo.getText().toString());

            Objeto piedra=new Objeto(masa, new Vector2(), new Vector2(Fisica.g,270),new Vector2(fuerza/masa, angulo));

            piedra=Fisica.mover(piedra, piedra.velocidad.x/Fisica.g);
            piedra=Fisica.mover(piedra, piedra.velocidad.y/Fisica.g);
            posX.setText(""+piedra.posicion.x);
            posY.setText(""+piedra.posicion.y);


        } catch (Exception e) {
            return ;
        }

    }
}
