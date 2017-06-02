 package elyo.my.trackids;

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

import static elyo.my.trackids.Principal.AgregarHijo;


 /**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link IngresarClaves.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link IngresarClaves#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IngresarClaves extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public IngresarClaves() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IngresarClaves.
     */
    // TODO: Rename and change types and number of parameters
    public static IngresarClaves newInstance(String param1, String param2) {
        IngresarClaves fragment = new IngresarClaves();
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


     View view;
     EditText editUsuario,editPin;
     FloatingActionButton btnAgregarHijo;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        try {
            view = inflater.inflate(R.layout.fragment_ingresar_claves, container, false);
            editPin = (EditText) view.findViewById(R.id.editPin);
            editUsuario = (EditText) view.findViewById(R.id.editUsuario);
            btnAgregarHijo = (FloatingActionButton) view.findViewById(R.id.btnAgregarHijo);
            Boton();

        return view;
        }
        catch (Exception ex)
        {
            Snackbar.make(view,"Algo ha salido mal, intente nuevamente, de no funcionar, reinicie la aplicación.",Snackbar.LENGTH_SHORT).show();
        }
        return null;
    }


     private void Boton() {
         btnAgregarHijo.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 try {
                     AgregarHijo(editUsuario.getText().toString(), editPin.getText().toString());
                     /*
                     Thread.sleep(1000);
                     if (preferences.getBoolean("existe", false)) {
                         PantallaHijos();
                     }
                     else
                     {
                         Snackbar.make(view,"Usuario o Pin incorrectos.",Snackbar.LENGTH_SHORT).show();
                     }
                     */
                 } catch (Exception ex) {
                     Snackbar.make(view,"Algo ha salido mal, intente nuevamente, de no funcionar, reinicie la aplicación.",Snackbar.LENGTH_SHORT).show();
                 }
             }
         });
     }
 }