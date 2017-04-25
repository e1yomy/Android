package transparencia.itai.com.transparenciadigital;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QuienesSomos.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QuienesSomos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuienesSomos extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public QuienesSomos() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuienesSomos.
     */
    // TODO: Rename and change types and number of parameters
    public static QuienesSomos newInstance(String param1, String param2) {
        QuienesSomos fragment = new QuienesSomos();
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

    TabLayout tabTitulos;
    ArrayList<ScrollView> tabDatos;
    //ArrayList<TabLayout.Tab> tab;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_quienes_somos, container, false);
        //tab= new ArrayList<>();
        tabDatos= new ArrayList<>();



        return view;
    }

    public void CargarTabs(View view)
    {
        tabTitulos=(TabLayout)view.findViewById(R.id.tabTitulos);
        tabTitulos.setTabMode(TabLayout.MODE_SCROLLABLE);
        LinearLayout layoutPrincipal= (LinearLayout)view.findViewById(R.id.layoutPrincipal);
        for(int i=0;i<layoutPrincipal.getChildCount();i++)
            if(layoutPrincipal.getChildAt(i) instanceof ScrollView) {
                tabDatos.add((ScrollView) layoutPrincipal.getChildAt(i));
                tabDatos.get(i).setVisibility(View.GONE);
            }
        //for(byte i=0;i<tabTitulos.getTabCount();i++){tab.add(tabTitulos.getTabAt(i));}

        tabTitulos.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //mostrar
                MostrarPestana(tab);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //Ocultar
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //mostrar
                MostrarPestana(tab);
            }
        });
    }
    public int MostrarPestana(TabLayout.Tab tab)
    {

        for(int i=0;i<tabTitulos.getTabCount();i++)
        {
            if(tab.getText()==tabTitulos.getTabAt(i).getText())
            {
                tabDatos.get(i).setVisibility(View.VISIBLE);
            }
            else
            {
                tabDatos.get(i).setVisibility(View.GONE);
            }
        }
        return 0;
    }
}