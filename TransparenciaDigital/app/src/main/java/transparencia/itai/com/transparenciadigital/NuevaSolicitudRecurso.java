package transparencia.itai.com.transparenciadigital;

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
import android.widget.Spinner;

import java.util.Calendar;

import static transparencia.itai.com.transparenciadigital.Conexion.idSO;
import static transparencia.itai.com.transparenciadigital.Conexion.nombresSO;
import static transparencia.itai.com.transparenciadigital.MainActivity.CambiarPantalla;
import static transparencia.itai.com.transparenciadigital.MainActivity.CargarRecurso;
import static transparencia.itai.com.transparenciadigital.MainActivity.usr;
import static transparencia.itai.com.transparenciadigital.MisSolicitudes.indice;
import static transparencia.itai.com.transparenciadigital.MisSolicitudes.solicitudes;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NuevaSolicitudRecurso.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NuevaSolicitudRecurso#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NuevaSolicitudRecurso extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public NuevaSolicitudRecurso() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NuevaSolicitudRecurso.
     */
    // TODO: Rename and change types and number of parameters
    public static NuevaSolicitudRecurso newInstance(String param1, String param2) {
        NuevaSolicitudRecurso fragment = new NuevaSolicitudRecurso();
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
    FloatingActionButton btnVolverALista,btnEnviarRecurso;
    EditText txtNombreSujeto,txtCausa;
    Spinner spinActoRecurrido;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_nueva_solicitud_recurso, container, false);
        btnVolverALista=(FloatingActionButton)view.findViewById(R.id.btnVolverALista);
        btnEnviarRecurso=(FloatingActionButton)view.findViewById(R.id.btnEnviarRecurso);
        txtNombreSujeto=(EditText)view.findViewById(R.id.txtNombreSujeto);
        txtNombreSujeto.setText(solicitudes.get(indice).sujetoObligado);
        txtNombreSujeto.setEnabled(false);
        txtCausa=(EditText)view.findViewById(R.id.txtCausa);
        spinActoRecurrido= (Spinner)view.findViewById(R.id.spinActoRecurrido);
        Botones();
        return view;
    }

    private void Botones() {
        btnVolverALista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CambiarPantalla(new MisSolicitudes());
            }
        });
        btnEnviarRecurso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Calendar ca = Calendar.getInstance();

                    String fecha = ca.get(Calendar.YEAR) + "-" +
                            (ca.get(Calendar.MONTH) + 1) + "-" +
                            ca.get(Calendar.DAY_OF_MONTH) + " " +
                            ca.get(Calendar.HOUR_OF_DAY) + ":" +
                            ca.get(Calendar.MINUTE) + ":" +
                            ca.get(Calendar.SECOND);
                    int idS = Integer.parseInt(idSO.get(nombresSO.indexOf(txtNombreSujeto.getText().toString())));
                    CargarRecurso(view,usr.getId(), "0", "0", String.valueOf(idS), txtNombreSujeto.getText().toString(), spinActoRecurrido.getSelectedItem().toString(), txtCausa.getText().toString(), solicitudes.get(indice).id, fecha);
                }
                catch (Exception e)
                {
                    Snackbar.make(view,e.getMessage(),Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
}

