package transparencia.itai.com.transparenciadigital;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static transparencia.itai.com.transparenciadigital.MainActivity.CambiarPantalla;
import static transparencia.itai.com.transparenciadigital.MainActivity.c;
import static transparencia.itai.com.transparenciadigital.MainActivity.postDataParams;
import static transparencia.itai.com.transparenciadigital.MainActivity.usr;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MisSolicitudes.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MisSolicitudes#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MisSolicitudes extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MisSolicitudes() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MisSolicitudes.
     */
    // TODO: Rename and change types and number of parameters
    public static MisSolicitudes newInstance(String param1, String param2) {
        MisSolicitudes fragment = new MisSolicitudes();
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

    static ListView lv1;
    static ListView lv2;
    static ListView lv3;
    static List<String> lista1 = new ArrayList<>();
    static List<String> lista2 = new ArrayList<>();
    static List<SolicitudItem> solicitudes= new ArrayList<>();
    FloatingActionButton btnVolver;
    LinearLayout layoutMisSolicitudes;
    Spinner spinOpciones;
    View view;
    static byte opcion=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_mis_solicitudes, container, false);

        lv1 = (ListView) view.findViewById(R.id.listSolicitudes);
        lv2 = (ListView) view.findViewById(R.id.listDatos);
        lv3 = (ListView) view.findViewById(R.id.listRespuesta);
        btnVolver = (FloatingActionButton) view.findViewById(R.id.btnVolver);
        layoutMisSolicitudes = (LinearLayout)view.findViewById(R.id.layoutMisSolicitudes);

        spinOpciones = (Spinner)view.findViewById(R.id.spinOpciones);
        //spinOpciones.setSelection(opcion);
        lv1.setVisibility(View.VISIBLE);
        lv2.setVisibility(View.GONE);
        lv2.setItemsCanFocus(false);
        lv3.setVisibility(View.GONE);
        lv3.setItemsCanFocus(false);
        btnVolver.setVisibility(View.GONE);
        Toques();
        return view;
    }
    static int indice=0;
    private void Toques() {
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    lv1.setVisibility(View.GONE);
                    lv2.setVisibility(View.VISIBLE);
                    lv3.setVisibility(View.VISIBLE);
                    btnVolver.show();
                    spinOpciones.setVisibility(View.GONE);
                    ////////////////////////////////////////////
                    //Hacer solicitud de detalles de solicitud//
                    ////////////////////////////////////////////
                    postDataParams = new JSONObject();
                    postDataParams.put("token", "12345678");
                    postDataParams.put("funcion", "datosSolicitud");
                    switch (opcion) {
                        case 0:
                            postDataParams.put("tabla", "solAcceso");
                            postDataParams.put("idTabla", "idAcceso");
                            break;
                        case 1:
                            postDataParams.put("tabla", "recRevision");
                            postDataParams.put("idTabla", "idRecurso");
                            break;
                        case 2:
                            postDataParams.put("tabla", "demIncumplimiento");
                            postDataParams.put("idTabla", "idDemanda");
                            break;
                    }
                    postDataParams.put("id", solicitudes.get(position).id);
                    new AsyncConsulta().execute();
                }
                catch (Exception e)
                {

                }
            }
        });
        lv1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                indice=position;
                if (opcion == 0) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(c);
                    alert.setTitle("Interponer recurso de revisión");
                    alert.setMessage("Ésto lo llevará a la pantalla de solicitud de recurso de revisión.\nUse esto si no está satisfecho con el resultado de su solicitud.");
                    alert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                CambiarPantalla(new NuevaSolicitudRecurso(),3);
                                //ActualizarLista();
                            } catch (Exception e) {
                                //Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    alert.show();
                }


                //Toast.makeText(c,position+"",Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lv1.setVisibility(View.VISIBLE);
                lv2.setVisibility(View.GONE);
                lv3.setVisibility(View.GONE);
                btnVolver.hide();
                spinOpciones.setVisibility(View.VISIBLE);

            }
        });
        spinOpciones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try{
                    opcion= (byte) position;
                    solicitudes.clear();
                    lv1.setAdapter(null);
                    //ListarSolicitudes(view);
                    postDataParams = new JSONObject();
                    postDataParams.put("token", "12345678");
                    postDataParams.put("funcion", "misSolicitudes");
                    switch (opcion)
                    {
                        case 0:
                            postDataParams.put("tabla", "solAcceso");
                            postDataParams.put("idTabla", "idAcceso");
                            break;
                        case 1:
                            postDataParams.put("tabla", "recRevision");
                            postDataParams.put("idTabla", "idRecurso");
                            break;
                        case 2:
                            postDataParams.put("tabla", "demIncumplimiento");
                            postDataParams.put("idTabla", "idDemanda");
                            break;
                    }
                    postDataParams.put("idUsuario", usr.getId());
                    new AsyncConsulta().execute();

                }
                catch (Exception e){Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();}
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public static void ActualizarListaPrimaria()
    {
        AdaptadorLista adaptadorLista= new AdaptadorLista(c,solicitudes);
        lv1.setAdapter(adaptadorLista);
    }
}
