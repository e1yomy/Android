package transparencia.itai.com.transparenciadigital;

import android.content.Context;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static transparencia.itai.com.transparenciadigital.MainActivity.Snack;
import static transparencia.itai.com.transparenciadigital.MainActivity.c;
import static transparencia.itai.com.transparenciadigital.MainActivity.postDataParams;
import static transparencia.itai.com.transparenciadigital.MainActivity.preferences;
import static transparencia.itai.com.transparenciadigital.MainActivity.toolbar;
import static transparencia.itai.com.transparenciadigital.MainActivity.txtTituloPantalla;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Sesion.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Sesion#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Sesion extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    //variables para el servicio
    URL direccion=null;
    String urlprevia="";
    final String webService= "http://pruebastec.890m.com/webservices/";
    String linea="";
    int respuesta=0;
    StringBuilder resul=new StringBuilder();
    HttpURLConnection conection;



    public Sesion() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Sesion.
     */
    // TODO: Rename and change types and number of parameters
    public static Sesion newInstance(String param1, String param2) {
        Sesion fragment = new Sesion();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }



    public int ConexionCorrecta(String url){
        try {
            direccion= new URL(url);
            conection = (HttpURLConnection) direccion.openConnection();
            respuesta = conection.getResponseCode();
            resul = new StringBuilder();
            if(respuesta==HttpURLConnection.HTTP_OK)
                return 1;
            else
                return 0;
        }
        catch (Exception ex){
            return 0;
        }

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    Button entrar;
    TextView registro;
    FragmentManager fragmentManager;
    static ScrollView layoutRegistro1,layoutInicioSesion;
    //LinearLayout layoutInicioSesion;
    static FloatingActionButton btnRegistro1;
    static ArrayList<EditText> textos= new ArrayList<>();
    EditText editCuenta, editContrasena;
    static FloatingActionButton btnVolverRegistro;
    Spinner spinnMunicipio;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sesion, container, false);
        entrar=(Button) view.findViewById(R.id.btnEntrar);
        registro=(TextView)view.findViewById(R.id.txtRegistro);
        fragmentManager= getFragmentManager();
        registro.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        editCuenta = (EditText)view.findViewById(R.id.editCuenta);
        editContrasena = (EditText)view.findViewById(R.id.editContrasena);

        layoutRegistro1 = (ScrollView)view.findViewById(R.id.layoutRegistro1);
        //layoutInicioSesion=(LinearLayout)view.findViewById(R.id.layoutInicioSesion);
        layoutInicioSesion=(ScrollView)view.findViewById(R.id.layoutInicioSesion);
        btnVolverRegistro=(FloatingActionButton)view.findViewById(R.id.btnVolverRegistro);


        layoutInicioSesion.setVisibility(View.VISIBLE);
        spinnMunicipio=(Spinner)view.findViewById(R.id.spinnMunicipio);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(c, R.array.municipios,R.layout.textospinner);
        spinnMunicipio.setAdapter(adapter);

        layoutRegistro1.setVisibility(View.GONE);
        btnRegistro1=(FloatingActionButton)view.findViewById(R.id.btnRegistro1);
        btnRegistro1.setVisibility(View.GONE);
        btnVolverRegistro.setVisibility(View.GONE);
        toolbar.setVisibility(View.GONE);
        Botones();
        Llenar(view);
        return view;
    }

    private void Botones() {
        //Label para registrarse
        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //fragmentManager.beginTransaction().replace(R.id.content_principal,new Registro()).commit();
                    layoutInicioSesion.setVisibility(View.GONE);
                    layoutRegistro1.setVisibility(View.VISIBLE);
                    layoutRegistro1.fullScroll(View.FOCUS_UP);
                    btnVolverRegistro.show();
                    btnRegistro1.show();
                    txtTituloPantalla.setText("Registro");
                } catch (Exception ex) {
                    String s = ex.getMessage();
                }

            }
        });
        //Boton de finalizar el registro
        btnRegistro1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //editCuenta.setText("");
                //editContrasena.setText("");
                //layoutInicioSesion.setVisibility(View.VISIBLE);
                //layoutRegistro1.setVisibility(View.GONE);

                for(byte i=0;i<textos.size();i++)
                {
                    String texto=textos.get(i).getText().toString();
                    switch (i)
                    {
                        case 0:
                            if(texto.length()<3)
                            {
                                Snack("Verificar los campos");
                                return;
                            }
                            break;
                        case 1:
                            if(texto.length()<3)
                            {
                                Snack("Verificar los campos");
                                return;
                            }
                            break;
                        case 2:
                            if(texto.length()<3)
                            {
                                Snack("Verificar los campos");
                                return;
                            }
                            break;
                        case 3:
                            if(!texto.contains("@"))
                            {
                                Snack("Ingresar un correo electrónico válido");
                                return;
                            }
                            break;
                        case 4:
                            if(texto.length()<3)
                            {
                                Snack("Favor de utilizar una contraseña más larga");
                                return;
                            }
                            break;
                        case 5:
                            if(!texto.equals(textos.get(i-1).getText().toString()))
                            {
                                Snack("Las contraseñas no coinciden");
                                return;
                            }
                            if(texto.length()<3)
                            {
                                Snack("Favor de utilizar una contraseña más larga");
                                return;
                            }
                            break;
                        case 6:
                            if(texto.length()<2)
                            {
                                Snack("Verificar el nombre de la calle");
                                return;
                            }
                            break;
                        case 7:
                            if(texto.length()==0)
                            {
                                Snack("Verificar el Número Exterior");
                                return;
                            }
                            break;
                        case 8:
                            break;
                        case 9:
                            if(texto.length()<2)
                            {
                                Snack("Verificar el nombre de las calle");
                                return;
                            }
                            break;
                        case 10:
                            if(texto.length()<2)
                            {
                                Snack("Verificar el nombre de la colonia");
                                return;
                            }
                        break;
                        case 11:
                            if(texto.length()!=5)
                            {
                                Snack("Verificar el Código Postal");
                                return;
                            }
                            break;
                        case 12:
                            break;
                        case 13:
                            if(texto.length()<10)
                            {
                                Snack("Verificar el Número Telefónico");
                                return;
                            }
                            break;
                    }
                }
                if(spinnMunicipio.getSelectedItemPosition()==0)
                {
                    Snack("Seleccionar un municipio");
                    return;
                }
                preferences.edit().putBoolean("sesion",false);
                //Registro(textos.get(3).getText().toString(),textos.get(4).getText().toString(),textos.get(0).getText().toString(),textos.get(1).getText().toString(),textos.get(2).getText().toString(),textos.get(6).getText().toString(),textos.get(7).getText().toString(),textos.get(8).getText().toString(),textos.get(9).getText().toString(),textos.get(10).getText().toString(),textos.get(11).getText().toString(),textos.get(12).getText().toString(),textos.get(13).getText().toString(),textos.get(14).getText().toString());
                try {
                    postDataParams = new JSONObject();
                    postDataParams.put("token", "12345678");
                    postDataParams.put("funcion", "registro");
                    postDataParams.put("tabla", "ciudadano");
                    postDataParams.put("usu", textos.get(3).getText().toString());
                    postDataParams.put("pas", textos.get(4).getText().toString());
                    postDataParams.put("nom", textos.get(0).getText().toString());
                    postDataParams.put("paterno", textos.get(1).getText().toString());
                    postDataParams.put("materno", textos.get(2).getText().toString());
                    postDataParams.put("calles", textos.get(6).getText().toString());
                    postDataParams.put("numeroExterior", textos.get(7).getText().toString());
                    postDataParams.put("numeroInterior", textos.get(8).getText().toString());
                    postDataParams.put("entreCalles", textos.get(9).getText().toString());
                    postDataParams.put("colonia", textos.get(10).getText().toString());
                    postDataParams.put("CP", textos.get(11).getText().toString());
                    postDataParams.put("entidad", textos.get(12).getText().toString());
                    postDataParams.put("municipio", spinnMunicipio.getSelectedItem().toString());
                    postDataParams.put("telefono", textos.get(13).getText().toString());
                    new AsyncConsulta().execute();
                }
                catch (Exception ex)
                {

                }
                ///
            }
        });

        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    //MainActivity.fragmentManager.beginTransaction().replace(R.id.content_principal, new MisSolicitudes()).commit();

                    //Funcion del MainActivity que hace la llamada al inicio de sesion desde un hilo
                    //IniciarSesion(editCuenta.getText().toString(),editContrasena.getText().toString());
                    try {
                        postDataParams= new JSONObject();
                        postDataParams.put("token", "12345678");
                        postDataParams.put("funcion", "acceso");
                        postDataParams.put("tabla", "ciudadano");
                        postDataParams.put("usuario", editCuenta.getText().toString());
                        postDataParams.put("contrasena", editContrasena.getText().toString());
                        Log.e("params",postDataParams.toString());
                    }
                    catch (Exception ex)
                    {
                        Toast.makeText(c, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    new AsyncConsulta().execute();

                } catch (Exception ex) {
                    String exx = ex.getMessage();
                    Log.e("Error",ex.getMessage(),ex.getCause());

                }

            }
        });

        btnVolverRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutInicioSesion.setVisibility(View.VISIBLE);
                layoutRegistro1.setVisibility(View.GONE);
                btnVolverRegistro.hide();
                btnRegistro1.hide();
                txtTituloPantalla.setText("Inicio de Sesión");
                LimpiarCampos();
            }
        });
    }

    public void Llenar(View view){
        textos.add((EditText) view.findViewById(R.id.editNombres1));
        textos.add((EditText) view.findViewById(R.id.editPaterno1));
        textos.add((EditText) view.findViewById(R.id.editMaterno1));
        textos.add((EditText) view.findViewById(R.id.editEmail1));
        textos.add((EditText) view.findViewById(R.id.editContra11));
        textos.add((EditText) view.findViewById(R.id.editContra21));
        textos.add((EditText) view.findViewById(R.id.editDomicilioCalle1));
        textos.add((EditText) view.findViewById(R.id.editDomicilioExterior1));
        textos.add((EditText) view.findViewById(R.id.editDomicilioInterior1));
        textos.add((EditText) view.findViewById(R.id.editDomicilioEntreCalles1));
        textos.add((EditText) view.findViewById(R.id.editDomicilioColonia1));
        textos.add((EditText) view.findViewById(R.id.editDomicilioCP1));
        textos.add((EditText) view.findViewById(R.id.editDomicilioEntidadFederativa1));
        textos.get(textos.size()-1).setFocusable(false);textos.get(textos.size()-1).setClickable(false);

        textos.add((EditText) view.findViewById(R.id.editTelefono1));

        for(byte i=0;i<textos.size();i++){
            final byte finalI = i;
            textos.get(i).setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        textos.get(finalI+1).setFocusable(true);
                        textos.get(finalI+1).requestFocus();
                        return true;
                    }
                    return false;
                }
            });
        }
    }
    public static void LimpiarCampos(){
        for(byte i=0;i<textos.size();i++){
            textos.get(i).setText("");
        }
    }

}