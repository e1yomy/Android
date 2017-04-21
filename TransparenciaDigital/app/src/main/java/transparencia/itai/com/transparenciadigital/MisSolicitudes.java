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
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import static transparencia.itai.com.transparenciadigital.MainActivity.c;


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

    ListView lv1, lv2;
    List<String> lista1 = new ArrayList<String>();
    List<String> lista2 = new ArrayList<String>();
    FloatingActionButton btnVolver;
    LinearLayout layoutMisSolicitudes;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mis_solicitudes, container, false);

        lv1 = (ListView) view.findViewById(R.id.listSolicitudes);
        lv2 = (ListView) view.findViewById(R.id.listDatos);
        btnVolver = (FloatingActionButton) view.findViewById(R.id.btnVolver);
        layoutMisSolicitudes = (LinearLayout)view.findViewById(R.id.layoutMisSolicitudes);

        lv1.setVisibility(View.VISIBLE);
        lv2.setVisibility(View.GONE);
        lv2.setItemsCanFocus(false);
        btnVolver.setVisibility(View.GONE);

        lista1.add("lista1");
        lista1.add("lista1");
        lista1.add("lista1");
        lista1.add("lista1");
        lista1.add("lista1");lista1.add("lista1");lista1.add("lista1");lista1.add("lista1");lista1.add("lista1");lista1.add("lista1");lista1.add("lista1");lista1.add("lista1");lista1.add("lista1");lista1.add("lista1");lista1.add("lista1");lista1.add("lista1");lista1.add("lista1");lista1.add("lista1");lista1.add("lista1");lista1.add("lista1");lista1.add("lista1");lista1.add("lista1");lista1.add("lista1");

        lista2.add("lista2");
        lista2.add("lista2");lista2.add("lista2");lista2.add("lista2");lista2.add("lista2");lista2.add("lista2");lista2.add("lista2");lista2.add("lista2");lista2.add("lista2");lista2.add("lista2");lista2.add("lista2");lista2.add("lista2");lista2.add("lista2");lista2.add("lista2");lista2.add("lista2");lista2.add("lista2");lista2.add("lista2");lista2.add("lista2");lista2.add("lista2");lista2.add("lista2");
        Toques();
        return view;
    }

    private void Toques() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(c,android.R.layout.simple_list_item_1,lista1);
        lv1.setAdapter(arrayAdapter);
        arrayAdapter = new ArrayAdapter<String>(c,android.R.layout.simple_list_item_1,lista2);
        lv2.setAdapter(arrayAdapter);

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lv1.setVisibility(View.GONE);
                lv2.setVisibility(View.VISIBLE);
                btnVolver.show();
            }
        });
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lv1.setVisibility(View.VISIBLE);
                lv2.setVisibility(View.GONE);
                btnVolver.hide();
            }
        });
    }
}
