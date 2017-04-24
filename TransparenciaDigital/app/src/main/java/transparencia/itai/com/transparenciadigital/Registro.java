package transparencia.itai.com.transparenciadigital;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import static transparencia.itai.com.transparenciadigital.MainActivity.c;
import static transparencia.itai.com.transparenciadigital.MainActivity.usr;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Registro.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Registro#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Registro extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Registro() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Registro.
     */
    // TODO: Rename and change types and number of parameters
    public static Registro newInstance(String param1, String param2) {
        Registro fragment = new Registro();
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

    ArrayList<EditText> texto;
    FloatingActionButton btnEditar, btnActualizar;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_registro, container, false);
        btnEditar= (FloatingActionButton)view.findViewById(R.id.btnEditar);
        btnActualizar=(FloatingActionButton)view.findViewById(R.id.btnActualizar);

        Llenar(view);
        HabilitarCampos(false);
        Boton();
        return view;
    }

    private void Boton() {
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnEditar.hide();
                btnActualizar.show();
                HabilitarCampos(true);
            }
        });
        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert= new AlertDialog.Builder(c);
                alert.setTitle("Edición de datos personales");
                alert.setMessage("¿Seguro que desea actualizar sus datos personales? ");
                alert.setPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //ProgresDialog para actualizar datos en la base de datos
                        //
                        //Si todo sale bien, continua, caso contrario, muestra mensaje de error y vuelve a la pantalla
                        btnActualizar.hide();
                        btnEditar.show();

                        HabilitarCampos(false);
                    }
                });
                alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Lo mismo pero sin actualizar campos.
                        Llenar(view);
                        btnActualizar.hide();
                        btnEditar.show();

                        HabilitarCampos(false);
                    }
                });
                alert.setNeutralButton("Volver", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                alert.show();
                //Mensaje de confirmacion, si dice que si, se hace lo siguiente

                //Actualizar datos en la base de datos
            }
        });
    }

    public void Llenar(View view){
        texto= new ArrayList<>();
        texto.add((EditText) view.findViewById(R.id.editEmail));
        texto.add((EditText) view.findViewById(R.id.editContra1));
        texto.add((EditText) view.findViewById(R.id.editContra2));
        texto.add((EditText) view.findViewById(R.id.editNombres));
        texto.add((EditText) view.findViewById(R.id.editPaterno));
        texto.add((EditText) view.findViewById(R.id.editMaterno));
        texto.add((EditText) view.findViewById(R.id.editDomicilioCalle));
        texto.add((EditText) view.findViewById(R.id.editDomicilioExterior));
        texto.add((EditText) view.findViewById(R.id.editDomicilioInterior));
        texto.add((EditText) view.findViewById(R.id.editDomicilioEntreCalles));
        texto.add((EditText) view.findViewById(R.id.editDomicilioColonia));
        texto.add((EditText) view.findViewById(R.id.editDomicilioCP));
        texto.add((EditText) view.findViewById(R.id.editDomicilioEntidadFederativa));
        texto.add((EditText) view.findViewById(R.id.editDomicilioMunicipio));
        texto.add((EditText) view.findViewById(R.id.editTelefono));

        for(byte i=0;i<texto.size();i++){
            texto.get(i).setEnabled(false);
            final byte finalI = i;
            if(i<2)
                texto.get(i).setText(usr.datos.get(i+2));
            else
                texto.get(i).setText(usr.datos.get(i+1));


            texto.get(i).setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                            texto.get(finalI+1).setFocusable(true);
                            texto.get(finalI+1).requestFocus();
                        return true;
                    }
                    return false;
                }
            });
        }
    }

    public void HabilitarCampos(boolean boo){
        for(byte i=0;i<texto.size();i++) {
            texto.get(i).setEnabled(boo);
        }
    }

}
