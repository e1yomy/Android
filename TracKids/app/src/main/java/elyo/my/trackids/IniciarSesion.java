package elyo.my.trackids;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import static elyo.my.trackids.Principal.ExisteCuenta;
import static elyo.my.trackids.Principal.PantallaRegistro;
import static elyo.my.trackids.Principal.c;
import static elyo.my.trackids.Principal.pantalla;
import static elyo.my.trackids.Principal.preferences;
import static elyo.my.trackids.Principal.toolbar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link IniciarSesion.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link IniciarSesion#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IniciarSesion extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public IniciarSesion() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IniciarSesion.
     */
    // TODO: Rename and change types and number of parameters
    public static IniciarSesion newInstance(String param1, String param2) {
        IniciarSesion fragment = new IniciarSesion();
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

    Button btnEntrar, btnFacebook;
    TextView txtRegistro;
    EditText editUsuario, editContrasena;
    View view;
    LoginButton loginButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_iniciar_sesion, container, false);
        Botones(view);


        return view;
    }
    CallbackManager callBackManager;
    public void Botones(final View view)
    {

        try {
        btnEntrar=(Button)view.findViewById(R.id.btnEntrar);
        loginButton = (LoginButton) view.findViewById(R.id.btnFacebook);

        //btnFacebook=(Button)view.findViewById(R.id.btnFacebook);
        txtRegistro=(TextView)view.findViewById(R.id.txtRegistro);
        editUsuario=(EditText)view.findViewById(R.id.editCuenta);
        editContrasena=(EditText)view.findViewById(R.id.editContrasena);
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Consulta a servicio web de inicio de sesion
                ExisteCuenta(editUsuario.getText().toString(),editContrasena.getText().toString());
            }
        });
        BotonFacebook();

            txtRegistro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PantallaRegistro();
                }
            });
        }
        catch (Exception ex)
        {
            String e=ex.getMessage();
        }


    }


    private void BotonFacebook() {

        callBackManager= CallbackManager.Factory.create();
        loginButton.setReadPermissions("email");

        loginButton.setFragment(this);

        loginButton.registerCallback(callBackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                ////////
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                try {
                                    //Servicio weab para verificar que el correo esté guardado en la base de datos,
                                    // si está guardado manda a la pantalla del mapa
                                    // si no está guardado se mandan los datos a la pantalla de registro para completar el registro
                                    // ///

                                    ///////
                                    preferences.edit()
                                            .putString("nombreUsuario",object.getString("first_name"))
                                            .putString("apellidoUsuario",object.getString("last_name"))
                                            .putString("correoUsuario",object.getString("email"))

                                            .commit();

                                    toolbar.setVisibility(View.VISIBLE);

                                    //si ya esta guardado manda al mapa
                                    pantalla=1;

                                    //si no esta guardado manda al registro
                                    preferences.edit().putInt("sesion",2).commit();
                                    PantallaRegistro();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,link,gender,birthday,email,first_name,last_name,location,locale,timezone");
                request.setParameters(parameters);
                request.executeAsync();
                //////////

                //Toast.makeText(c,loginResult.toString() ,Toast.LENGTH_SHORT).show();
                //PantallaMapa();
            }

            @Override
            public void onCancel() {
                PantallaRegistro();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(c,error.getMessage() ,Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callBackManager.onActivityResult(requestCode, resultCode, data);
    }
}
