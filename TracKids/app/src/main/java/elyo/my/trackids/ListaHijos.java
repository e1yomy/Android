package elyo.my.trackids;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import static elyo.my.trackids.Principal.c;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListaHijos.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListaHijos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaHijos extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ListaHijos() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListaHijos.
     */
    // TODO: Rename and change types and number of parameters
    public static ListaHijos newInstance(String param1, String param2) {
        ListaHijos fragment = new ListaHijos();
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

    static ListView lv;
    List<String> lista = new ArrayList<String>();
    static ArrayList<Hijo> listaHijos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_lista_hijos, container, false);

            lv = (ListView) view.findViewById(R.id.listHijos);
            lista.add("foo");
            lista.add("bar");
            listaHijos = new ArrayList<Hijo>();
            listaHijos.add(new Hijo("Eloy", 24.1332822, -110.310106, "Wea", "6121972191"));
            listaHijos.add(new Hijo("Erik", 24.1388434, -110.314306, "Wea", "6121972191"));
            listaHijos.add(new Hijo("Ruiz", 24.1392832, -110.314106, "Wea", "6121972191"));
            listaHijos.add(new Hijo("Ulises", 24.142822, -110.310506, "Wea", "6121972191"));
            listaHijos.add(new Hijo("Denisse", 24.133892, -110.316106, "Wea", "6121972191"));
            ActualizarLista();
            return view;

    }
    static void ActualizarLista(){
        AdaptadorLista adaptadorLista = new AdaptadorLista(listaHijos,c);
        lv.setAdapter(adaptadorLista);
    }

}
