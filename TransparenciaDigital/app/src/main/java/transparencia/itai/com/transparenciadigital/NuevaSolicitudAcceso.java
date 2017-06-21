package transparencia.itai.com.transparenciadigital;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import static transparencia.itai.com.transparenciadigital.Conexion.idSO;
import static transparencia.itai.com.transparenciadigital.Conexion.nombresSO;
import static transparencia.itai.com.transparenciadigital.MainActivity.CambiarPantalla;
import static transparencia.itai.com.transparenciadigital.MainActivity.c;
import static transparencia.itai.com.transparenciadigital.MainActivity.postDataParams;
import static transparencia.itai.com.transparenciadigital.MainActivity.usr;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NuevaSolicitudAcceso.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NuevaSolicitudAcceso#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NuevaSolicitudAcceso extends Fragment implements MisSolicitudes.OnFragmentInteractionListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public NuevaSolicitudAcceso() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NuevaSolicitudAcceso.
     */
    // TODO: Rename and change types and number of parameters
    public static NuevaSolicitudAcceso newInstance(String param1, String param2) {
        NuevaSolicitudAcceso fragment = new NuevaSolicitudAcceso();
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

    ArrayList<RadioButton> opciones=new ArrayList<>();
    ScrollView layoutSolicitudAcceso;
    FloatingActionButton btnTerminarSolicitudAcceso;
    FragmentManager fragmentManager;
    RadioGroup radioGroup;
    EditText editDescripcion;
    AutoCompleteTextView editSujeto;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nueva_solicitud_acceso, container, false);
        layoutSolicitudAcceso=(ScrollView)view.findViewById(R.id.layoutSolicitudAcceso);
        btnTerminarSolicitudAcceso=(FloatingActionButton)view.findViewById(R.id.btnTerminarSolicitudAcceso);
        editSujeto = (AutoCompleteTextView)view.findViewById(R.id.editSujeto);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(c,
                android.R.layout.simple_dropdown_item_1line, nombresSO);
        editSujeto.setAdapter(adapter);
        fragmentManager= getFragmentManager();
        MetodosClic(view);


        return view;
    }
    public void MetodosClic(final View view){
        opciones.add((RadioButton) view.findViewById(R.id.radioOpcion1));
        opciones.add((RadioButton) view.findViewById(R.id.radioOpcion2));
        opciones.add((RadioButton) view.findViewById(R.id.radioOpcion3));
        opciones.add((RadioButton) view.findViewById(R.id.radioOpcion4));
        opciones.add((RadioButton) view.findViewById(R.id.radioOpcion5));
        opciones.add((RadioButton) view.findViewById(R.id.radioOpcion6));
        opciones.get(5).setSelected(true);
        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        editDescripcion=(EditText)view.findViewById(R.id.editDescripcion);

        for(byte i=0;i<opciones.size();i++)
        {
            {
                opciones.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        layoutSolicitudAcceso.fullScroll(View.FOCUS_DOWN);
                    }
                });
            }
        }
        btnTerminarSolicitudAcceso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert= new AlertDialog.Builder(c);
                alert.setTitle("Solicitud de acceso a la información pública");
                alert.setMessage("¿Desea enviar la solicitud? ");
                alert.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Hilo para subir a la base de datos
                        //ProgressDialog progress= new ProgressDialog(c);
                        //progress.setTitle("Enviando");

                        //Snackbar con mensaje de que la solicitud se envio correctamente y después mandar a la lista de solicitudes
                        //Si no se envio, mantener en esa pantalla y mostrar mensaje de error de conexion.
                        Calendar ca = Calendar.getInstance();
                        String fecha = ca.get(Calendar.YEAR) + "-" +
                                (ca.get(Calendar.MONTH) + 1) + "-" +
                                ca.get(Calendar.DAY_OF_MONTH) + " " +
                                ca.get(Calendar.HOUR_OF_DAY) + ":" +
                                ca.get(Calendar.MINUTE) + ":" +
                                ca.get(Calendar.SECOND);

                        int index = radioGroup.indexOfChild(view.findViewById(radioGroup.getCheckedRadioButtonId()));
                        if (nombresSO.indexOf(editSujeto.getText().toString()) != -1)
                            if (editDescripcion.getText().toString().length()>20)
                                if (index != -1) {
                                    try {
                                        postDataParams = new JSONObject();
                                        postDataParams.put("token", "12345678");
                                        postDataParams.put("funcion", "nuevaSolicitud");
                                        postDataParams.put("tabla", "solAcceso");
                                        postDataParams.put("folio","0");
                                        postDataParams.put("fecha",fecha);
                                        postDataParams.put("idUsuario",usr.getId());
                                        postDataParams.put("idNotificaciones","0");
                                        postDataParams.put("idSujeto",idSO.get(nombresSO.indexOf(editSujeto.getText().toString())));
                                        postDataParams.put("nombreSujeto",editSujeto.getText().toString());
                                        postDataParams.put("descripcion",editDescripcion.getText().toString());
                                        postDataParams.put("IdtipoDeEntrega","0");
                                        new AsyncConsulta().execute();
                                    } catch (Exception ex) {

                                    }
                                    //CargarSolicitud(view, fecha, usr.getId(), String.valueOf(index), idSO.get(nombresSO.indexOf(editSujeto.getText().toString())), editSujeto.getText().toString(), editDescripcion.getText().toString(), "0");
                                }
                                else
                                    Snackbar.make(view, "Favor de seleccionar una modalidad de entrega.", Snackbar.LENGTH_SHORT).show();
                            else
                                Snackbar.make(view, "Favor de ingresar la informaión de manera más extensa.", Snackbar.LENGTH_SHORT).show();
                        else
                            Snackbar.make(view, "Favor de ingresar un Sujeto Obligado valido.", Snackbar.LENGTH_SHORT).show();
                    }
                });
                alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CambiarPantalla(new MisSolicitudes());
                    }
                });
                alert.setNeutralButton("Volver", new DialogInterface.OnClickListener() {
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
