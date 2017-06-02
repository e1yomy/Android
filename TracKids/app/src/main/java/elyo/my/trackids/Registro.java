package elyo.my.trackids;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.facebook.login.LoginManager;

import java.util.ArrayList;
import java.util.List;

import static elyo.my.trackids.Principal.GuardarCuenta;
import static elyo.my.trackids.Principal.PantallaInicioDeSesion;
import static elyo.my.trackids.Principal.preferences;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Registro.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Registro#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Registro extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Registro() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Registro.
     */
    // TODO: Rename and change types and number of parameters
    public static Registro newInstance(String param1, String param2) {
        Registro fragment = new Registro();
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

    EditText editNombres,editApellidos,editEmail,editContra1,editContra2, editTelefono,editPin;
    static List<EditText> campos;
    FloatingActionButton btnVolver,btnRegistrar;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_registro, container, false);
        campos= new ArrayList<>();
        editNombres=(EditText)view.findViewById(R.id.editNombres);
        editApellidos=(EditText)view.findViewById(R.id.editApellidos);
        editEmail=(EditText)view.findViewById(R.id.editEmail);
        editContra1=(EditText)view.findViewById(R.id.editContra1);
        editContra2=(EditText)view.findViewById(R.id.editContra2);
        editTelefono=(EditText)view.findViewById(R.id.editTelefono);
        editPin=(EditText)view.findViewById(R.id.editPin);
        campos.add(editNombres);
        campos.add(editApellidos);
        campos.add(editEmail);
        campos.add(editContra1);
        campos.add(editContra2);
        campos.add( editTelefono);
        campos.add( editPin);
        Regresar(view);
        TerminarRegistro(view);
        if(preferences.getInt("sesion",-1)==3)
        {
            //Cargar los datos en los campos, dejando los campos de contraseña de solo lectura
            String nombre = preferences.getString("nombresUsuario","");
            String apellido =preferences.getString("apellidosUsuario","");
            String email=preferences.getString("correoUsuario","");
            String contra= preferences.getString("contrasenaUsuario","1324567890");
            editNombres.setText(nombre);
            editApellidos.setText(apellido);
            editEmail.setText(email);
            editNombres.setEnabled(nombre.isEmpty());
            editApellidos.setEnabled(apellido.isEmpty());
            editEmail.setEnabled(email.isEmpty());
            editContra1.setEnabled(false);
            editContra2.setEnabled(false);
            editContra1.setText(contra);
            editContra2.setText(contra);
            editTelefono.setEnabled(true);
            editPin.setEnabled(true);

        }
        else
        {
            for (byte i=0;i<campos.size();i++)
            {
                campos.get(i).setEnabled(true);
            }
        }

        return view;
    }

    private void Regresar(View view) {
        try {
            btnVolver = (FloatingActionButton) view.findViewById(R.id.btnVolverASesion);
            btnVolver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LoginManager.getInstance().logOut();
                    preferences.edit().putInt("sesion", 0).commit();
                    PantallaInicioDeSesion();
                }
            });
        }
        catch (Exception ex)
        {
            String e=ex.getMessage();
        }
    }


    private void TerminarRegistro(final View view) {
        try{
        btnRegistrar=(FloatingActionButton)view.findViewById(R.id.btnFinalizarRegistro);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    for (byte i = 0; i < campos.size(); i++) {
                        if (campos.get(i).getText().toString().isEmpty()) {
                            Snackbar.make(view,"Verificar que los campos estén completos.",Snackbar.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    if (editContra1.getText().toString().equals(editContra2.getText().toString())) {
                        //if (preferences.getInt("sesion", -1) != 2)
                        {
                            GuardarCuenta(campos.get(0).getText().toString(),campos.get(1).getText().toString(),campos.get(2).getText().toString(),campos.get(3).getText().toString(),campos.get(5).getText().toString(),campos.get(6).getText().toString());
                            /*
                            Thread.sleep(1000);
                            if (preferences.getInt("sesion", -1) == 3) {
                                pantalla = 1;
                                //cargarDatos De usuario en preferences
                                ExisteCuenta(campos.get(2).getText().toString(),preferences.getString("contrasenaUsuario",""));
                                preferences.edit().putInt("sesion", 2).commit();
                                CrearRegistroUltimaUbicacion();
                                PantallaMapa();

                            }
                            else {
                                if (preferences.getBoolean("exito", false))
                                {
                                    preferences.edit().putInt("sesion", 0).commit();
                                    Snackbar.make(view,"Cuenta creada, ahora puedes iniciar sesion.",Snackbar.LENGTH_SHORT).show();
                                    ExisteCuenta(campos.get(2).getText().toString(),campos.get(3).getText().toString());
                                    CrearRegistroUltimaUbicacion();
                                    PantallaInicioDeSesion();
                                }
                                else
                                {
                                    /////
                                    Snackbar.make(view,"Algo ha salido mal, intente nuevamente, de no funcionar, reinicie la aplicación.",Snackbar.LENGTH_SHORT).show();
                                }
                            }
                            */

                        }

                    }

                    ////
                }
                catch (Exception ex)
                {
                    //String s = ex.getMessage();
                }

            }
        });
        }
        catch (Exception ex)
        {
            //String e=ex.getMessage();
        }
    }
}

