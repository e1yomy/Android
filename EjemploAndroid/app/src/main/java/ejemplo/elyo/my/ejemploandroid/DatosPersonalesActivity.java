package ejemplo.elyo.my.ejemploandroid;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class DatosPersonalesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_personales);


        String usuario=getIntent().getExtras().getString("usuario");
        TextView texto= (TextView)findViewById(R.id.textView3);
        texto.setText(usuario);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.ocup, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }
    public void Siguiente(View v)
    {
        EditText editNombre= (EditText)findViewById(R.id.editNombre);
        RadioButton rmasculino= (RadioButton)findViewById(R.id.radioButton);
        RadioButton rfemenino= (RadioButton)findViewById(R.id.radioButton2);
        Spinner sOcupacion= (Spinner)findViewById(R.id.spinner);
        String nombre=editNombre.getText().toString();
        String genero;

        if(rmasculino.isChecked() && !rfemenino.isChecked())
            genero="Masculino";
        else
            genero="Femenino";
        String ocupacion = sOcupacion.getSelectedItem().toString();



        //Intent i= new Intent(this,InteresesActivity.class);
        Intent i= new Intent(this,ResumenActivity.class);
        i.putExtra("usuario",getIntent().getExtras().getString("usuario"));
        i.putExtra("nombre",editNombre.getText().toString());
        i.putExtra("genero",genero);
        i.putExtra("ocupacion",ocupacion);
        startActivity(i);
    }
}
