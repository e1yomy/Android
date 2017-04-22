package transparencia.itai.com.transparenciadigital;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import static transparencia.itai.com.transparenciadigital.MainActivity.c;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NuevaSolicitudDenuncia.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NuevaSolicitudDenuncia#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NuevaSolicitudDenuncia extends Fragment implements MisSolicitudes.OnFragmentInteractionListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public NuevaSolicitudDenuncia() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NuevaSolicitudDenuncia.
     */
    // TODO: Rename and change types and number of parameters
    public static NuevaSolicitudDenuncia newInstance(String param1, String param2) {
        NuevaSolicitudDenuncia fragment = new NuevaSolicitudDenuncia();
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


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    Button btnTerminarSolicitudDenuncia;
    FragmentManager fragmentManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment\
        View view =inflater.inflate(R.layout.fragment_nueva_solicitud_denuncia, container, false);
        btnTerminarSolicitudDenuncia= (Button)view.findViewById(R.id.btnTerminarSolicitudDenuncia);
        fragmentManager= getFragmentManager();

        Boton(view);

        return view;
    }

    private void Boton(View view) {
        btnTerminarSolicitudDenuncia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert= new AlertDialog.Builder(c);
                alert.setTitle("Denuncia por incumplimiento");
                alert.setMessage("¿Desea enviar la solicitud? ");
                alert.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Hilo para subir a la base de datos
                        //ProgressDialog progress= new ProgressDialog(c);
                        //progress.setTitle("Enviando");

                        //Snackbar con mensaje de que la solicitud se envio correctamente y después mandar a la lista de solicitudes
                        //Si no se envio, mantener en esa pantalla y mostrar mensaje de error de conexion.
                        fragmentManager.beginTransaction().replace(R.id.content_principal, new MisSolicitudes()).commit();
                    }
                });
                alert.setNegativeButton("Volver", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Volver a la edición de la solicitúd
                    }
                });
                alert.show();
            }
        });
    }
}
