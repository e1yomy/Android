package transparencia.itai.com.transparenciadigital;

import android.content.Context;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.content.Context.MEDIA_PROJECTION_SERVICE;
import static transparencia.itai.com.transparenciadigital.MainActivity.HabilitarMenu;
import static transparencia.itai.com.transparenciadigital.MainActivity.drawer;
import static transparencia.itai.com.transparenciadigital.MainActivity.fragmentManager;
import static transparencia.itai.com.transparenciadigital.MainActivity.navigationView;
import static transparencia.itai.com.transparenciadigital.MainActivity.preferences;
import static transparencia.itai.com.transparenciadigital.MainActivity.sesion;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Sesion.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Sesion#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Sesion extends Fragment implements Registro.OnFragmentInteractionListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

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

    @Override
    public void onFragmentInteraction(Uri uri) {

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
    ScrollView layoutRegistro1;
    LinearLayout layoutInicioSesion;
    Button btnRegistro1;
    ArrayList<EditText> textos= new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sesion, container, false);
        entrar=(Button) view.findViewById(R.id.btnEntrar);
        registro=(TextView)view.findViewById(R.id.txtRegistro);
        fragmentManager= getFragmentManager();
        registro.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        layoutRegistro1 = (ScrollView)view.findViewById(R.id.layoutRegistro1);
        layoutInicioSesion=(LinearLayout)view.findViewById(R.id.layoutInicioSesion);
        layoutInicioSesion.setVisibility(View.VISIBLE);
        layoutRegistro1.setVisibility(View.GONE);
        btnRegistro1=(Button)view.findViewById(R.id.btnRegistro1);
        Entrar();
        Registrar();
        Llenar(view);
        return view;
    }

    private void Registrar() {
        //Label para registrarse
            registro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        //fragmentManager.beginTransaction().replace(R.id.content_principal,new Registro()).commit();
                        layoutInicioSesion.setVisibility(View.GONE);
                        layoutRegistro1.setVisibility(View.VISIBLE);
                        layoutRegistro1.fullScroll(View.FOCUS_UP);
                    } catch (Exception ex) {
                        String s = ex.getMessage();
                    }

                }
            });
        //Boton de finalizar el registro
            btnRegistro1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        layoutInicioSesion.setVisibility(View.VISIBLE);
                        layoutRegistro1.setVisibility(View.GONE);
                        preferences.edit().putBoolean("sesion",false);

                        ///
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
        textos.add((EditText) view.findViewById(R.id.editDomicilioMunicipio1));
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

    private void Entrar() {
        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences.edit().putBoolean("sesion",true).commit();
                HabilitarMenu(preferences.getBoolean("sesion",false));
                navigationView.getMenu().getItem(0).setChecked(true);
                fragmentManager.beginTransaction().replace(R.id.content_principal, new MisSolicitudes()).commit();
            }
        });
    }
}
