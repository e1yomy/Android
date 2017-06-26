package transparencia.itai.com.transparenciadigital;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static transparencia.itai.com.transparenciadigital.MainActivity.c;
import static transparencia.itai.com.transparenciadigital.MainActivity.idSO;
import static transparencia.itai.com.transparenciadigital.MainActivity.nombresSO;
import static transparencia.itai.com.transparenciadigital.MainActivity.postDataParams;
import static transparencia.itai.com.transparenciadigital.MisSolicitudes.solicitudes;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SujetosObligados.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SujetosObligados#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SujetosObligados extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SujetosObligados() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SujetosObligados.
     */
    // TODO: Rename and change types and number of parameters
    public static SujetosObligados newInstance(String param1, String param2) {
        SujetosObligados fragment = new SujetosObligados();
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

    byte nivel=0;
    static ArrayList<ListView> listas= new ArrayList<>();
    static List<String> lista = new ArrayList<>();
    FloatingActionButton btnVolverSO;
    static TextView txtTituloSO;
    int indSolicitud=-1;
    int indSO=-1;
    View view;
    static ListView listaSO;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sujetos_obligados, container, false);
        listaSO=(ListView) view.findViewById(R.id.listSujetosObligados);
        listas.add((ListView) view.findViewById(R.id.listSujetosObligados));
        listas.add((ListView) view.findViewById(R.id.listSolicitudes));
        listas.add((ListView) view.findViewById(R.id.listDetalles));
        btnVolverSO=(FloatingActionButton)view.findViewById(R.id.btnVolverSO);
        txtTituloSO= (TextView)view.findViewById(R.id.txtTituloSO);
        Clics();
        CargarSujetos();
        ActualizarPantalla(nivel);

        return view;
    }

    private void CargarSujetos() {
        try {
            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(c, android.R.layout.simple_list_item_1, nombresSO);
            listaSO.setAdapter(arrayAdapter);
            //ListaSujetosObligados(view);
            try {
                //postDataParams = new JSONObject();
                //postDataParams.put("token", "12345678");
                //postDataParams.put("funcion", "listarSujetos");
                //new AsyncConsulta().execute();
            }
            catch (Exception e)
            {
                Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e)
        {
            Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void Clics() {
        lista.add("item1");
        lista.add("item2");
        lista.add("item3");
        lista.add("item4");
        lista.add("item5");

        btnVolverSO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    ActualizarPantalla(--nivel);
                }
                catch (Exception e)
                {
                    Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        listas.get(0).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    indSO = position;

                    ActualizarPantalla(++nivel);
                    //Llamar a hacer lista de solicitude
                    listas.get(1).setAdapter(null);
                    CargarSolicitudes();
                }
                catch (Exception e)
                {
                    Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        listas.get(1).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    indSolicitud = position;
                    ActualizarPantalla(++nivel);
                    //List<String> l = new ArrayList<String>();
                    //l.add(String.valueOf(solicitudes.get(position).tipo));
                    //l.add(String.valueOf(solicitudes.get(position).id));
                    //l.add(solicitudes.get(position).sujetoObligado);
                    //l.add(solicitudes.get(position).fecha);
                    //l.add(String.valueOf(solicitudes.get(position).estado));

                    postDataParams = new JSONObject();
                    postDataParams.put("token", "12345678");
                    postDataParams.put("funcion", "datosSolicitud");
                    postDataParams.put("tabla", "solAcceso");
                    postDataParams.put("idTabla", "idAcceso");
                    postDataParams.put("id", solicitudes.get(position).id);
                    new AsyncConsulta().execute();


                    //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(c, android.R.layout.simple_list_item_1, l);
                    //listas.get(2).setAdapter(arrayAdapter);
                }
                catch (Exception e)
                {
                    Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void CargarSolicitudes() {
        try {
            //ListarSolicitudesDeSujetoObligado(idSO.get(indSO));
            postDataParams = new JSONObject();
            postDataParams.put("token", "12345678");
            postDataParams.put("funcion", "listarSolicitudSujetos");
            postDataParams.put("tabla", "solAcceso");
            postDataParams.put("idSuj", idSO.get(indSO));
            new AsyncConsulta().execute();

        } catch (Exception e) {
            Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void ActualizarPantalla(int n){
        if(nivel<0)
            nivel=0;
        switch (n)
        {
            case 0:
                btnVolverSO.hide();
                txtTituloSO.setText("Listado de Sujetos Obligados");
                break;
            case 1:
                btnVolverSO.show();
                txtTituloSO.setText("Listado solicitudes realizadas a "+nombresSO.get(indSO));
                ///Guardar el texto del item seleccionado y mostrarlo aquí
                break;
            case 2:
                btnVolverSO.show();
                txtTituloSO.setText("Detalles de la solicitud");
                break;
            default:
                n=0;
                btnVolverSO.hide();
                txtTituloSO.setText("Listado de Sujetos Obligados");
                break;

        }

        for(byte i=0;i<listas.size();i++)
        {
            listas.get(i).setVisibility(View.GONE);
            if(i==n)
                listas.get(i).setVisibility(View.VISIBLE);
        }

    }
}
