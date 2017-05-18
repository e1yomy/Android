package transparencia.itai.com.transparenciadigital;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


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
    WebView webView1;
    Button btnEstructura;
    TextView textView30, textView31, textView32, textView33, textView34, textView35, textView36, textView37, textView38, textView39, textView40, textView41, textView42, textView43, textView44, textView45, textView46, textView47, textView48, textView49, textView50, textView51, textView52, textView53, textView54, textView55, textView56, textView57, textView58, textView59, textView60, textView61, textView62, textView63, textView64, textView65, textView66, textView67, textView68, textView69, textView70, textView71, textView72, textView73, textView74, textView75, textView76, textView77, textView78, textView79, textView80, textView81, textView82, textView83, textView84, textView85, textView86;
    List<TextView> c;
    List<String> clink;
    FloatingActionButton btnIrArriba;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_quienes_somos, container, false);
        //tab= new ArrayList<>();
        tabDatos= new ArrayList<>();
        btnEstructura= (Button)view.findViewById(R.id.btnEstructura);
        btnEstructura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://itaibcs.org.mx/ifile/EstructuraOrganica/EstructuraOrganica.pdf")));
            }
        });
        c=new ArrayList<>();
        clink=new ArrayList<>();
        CargarTextViewPantalla3(view);
        CargarTabs(view);


        return view;
    }

    private void CargarTextViewPantalla3(View view) {
        textView30= (TextView)view.findViewById(R.id.textView30);
        textView31= (TextView)view.findViewById(R.id.textView31);
        textView32= (TextView)view.findViewById(R.id.textView32);
        textView33= (TextView)view.findViewById(R.id.textView33);
        textView34= (TextView)view.findViewById(R.id.textView34);
        textView35= (TextView)view.findViewById(R.id.textView35);
        textView36= (TextView)view.findViewById(R.id.textView36);
        textView37= (TextView)view.findViewById(R.id.textView37);
        textView38= (TextView)view.findViewById(R.id.textView38);
        textView39= (TextView)view.findViewById(R.id.textView39);
        textView40= (TextView)view.findViewById(R.id.textView40);
        textView41= (TextView)view.findViewById(R.id.textView41);
        textView42= (TextView)view.findViewById(R.id.textView42);
        textView43= (TextView)view.findViewById(R.id.textView43);
        textView44= (TextView)view.findViewById(R.id.textView44);
        textView45= (TextView)view.findViewById(R.id.textView45);
        textView46= (TextView)view.findViewById(R.id.textView46);
        textView47= (TextView)view.findViewById(R.id.textView47);
        textView48= (TextView)view.findViewById(R.id.textView48);
        textView49= (TextView)view.findViewById(R.id.textView49);
        textView50= (TextView)view.findViewById(R.id.textView50);
        textView51= (TextView)view.findViewById(R.id.textView51);
        textView52= (TextView)view.findViewById(R.id.textView52);
        textView53= (TextView)view.findViewById(R.id.textView53);
        textView54= (TextView)view.findViewById(R.id.textView54);
        textView55= (TextView)view.findViewById(R.id.textView55);
        textView56= (TextView)view.findViewById(R.id.textView56);
        textView57= (TextView)view.findViewById(R.id.textView57);
        textView58= (TextView)view.findViewById(R.id.textView58);
        textView59= (TextView)view.findViewById(R.id.textView59);
        textView60= (TextView)view.findViewById(R.id.textView60);
        textView61= (TextView)view.findViewById(R.id.textView61);
        textView62= (TextView)view.findViewById(R.id.textView62);
        textView63= (TextView)view.findViewById(R.id.textView63);
        textView64= (TextView)view.findViewById(R.id.textView64);
        textView65= (TextView)view.findViewById(R.id.textView65);
        textView66= (TextView)view.findViewById(R.id.textView66);
        textView67= (TextView)view.findViewById(R.id.textView67);
        textView68= (TextView)view.findViewById(R.id.textView68);
        textView69= (TextView)view.findViewById(R.id.textView69);
        textView70= (TextView)view.findViewById(R.id.textView70);
        textView71= (TextView)view.findViewById(R.id.textView71);
        textView72= (TextView)view.findViewById(R.id.textView72);
        textView73= (TextView)view.findViewById(R.id.textView73);
        textView74= (TextView)view.findViewById(R.id.textView74);
        textView75= (TextView)view.findViewById(R.id.textView75);
        textView76= (TextView)view.findViewById(R.id.textView76);
        textView77= (TextView)view.findViewById(R.id.textView77);
        textView78= (TextView)view.findViewById(R.id.textView78);
        textView79= (TextView)view.findViewById(R.id.textView79);
        textView80= (TextView)view.findViewById(R.id.textView80);
        textView81= (TextView)view.findViewById(R.id.textView81);
        textView82= (TextView)view.findViewById(R.id.textView82);
        textView83= (TextView)view.findViewById(R.id.textView83);
        textView84= (TextView)view.findViewById(R.id.textView84);
        textView85= (TextView)view.findViewById(R.id.textView85);
        CargarListenersDePantalla3();

    }
    void CargarListenersDePantalla3()
    {
        c.add(textView32);c.add(textView33);c.add(textView35);c.add(textView36);
        c.add(textView38);c.add(textView39);c.add(textView41);c.add(textView43);
        c.add(textView44);c.add(textView45);c.add(textView46);c.add(textView47);
        c.add(textView48);c.add(textView49);c.add(textView50);c.add(textView51);
        c.add(textView52);c.add(textView53);c.add(textView54);c.add(textView55);
        c.add(textView56);c.add(textView57);c.add(textView58);c.add(textView59);
        c.add(textView61);c.add(textView62);c.add(textView63);c.add(textView64);
        c.add(textView65);c.add(textView66);c.add(textView67);c.add(textView69);
        c.add(textView70);c.add(textView71);c.add(textView72);c.add(textView74);
        c.add(textView76);c.add(textView77);c.add(textView78);c.add(textView79);
        c.add(textView81);c.add(textView82);c.add(textView84);c.add(textView85);



        for(byte i=0;i<c.size();i++)
        {
            if(i!=2)
            c.get(i).setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        }
        OcultarTextos();
        //constitucion politica
        textView31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OcultarTextos();
                textView32.setVisibility(View.VISIBLE);
                textView33.setVisibility(View.VISIBLE);
            }
        });
        textView32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://itaibcs.org.mx/ifile/Constitucion/CPoliticadelosEstadosUnidosMexicanosDOF29ene16.pdf")));
            }
        });
        textView33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://itaibcs.org.mx/ifile/Constitucion/CPoliticaBCS.pdf")));
            }
        });


        //acuerdos
        textView34.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OcultarTextos();
                textView35.setVisibility(View.VISIBLE);
                textView36.setVisibility(View.VISIBLE);
            }
        });
        textView36.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://itaibcs.org.mx/ifile/AcuerdoCatalogoSujetosObligados/AcuerdoCatalogodeSujetosObligados.pdf")));
            }
        });

        //codigo
        textView37.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OcultarTextos();
                textView38.setVisibility(View.VISIBLE);
                textView39.setVisibility(View.VISIBLE);
            }
        });
        textView38.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://itaibcs.org.mx/ifile/codigo/CProcedimientosCivilesBCS.pdf")));
            }
        });
        textView39.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://itaibcs.org.mx/ifile/codigo/CFF8_170616.pdf")));
            }
        });

        //criterios
        textView40.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OcultarTextos();
                textView41.setVisibility(View.VISIBLE);
            }
        });
        textView41.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://itaibcs.org.mx/ifile/criterios/Criteriosparaelcobroenlareproduccionocopiadode%20informacionpublica.pdf")));
            }
        });
        //leyes
        textView42.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OcultarTextos();
                textView43.setVisibility(View.VISIBLE);
                textView44.setVisibility(View.VISIBLE);
                textView45.setVisibility(View.VISIBLE);
                textView46.setVisibility(View.VISIBLE);
                textView47.setVisibility(View.VISIBLE);
                textView48.setVisibility(View.VISIBLE);
                textView49.setVisibility(View.VISIBLE);
                textView50.setVisibility(View.VISIBLE);
                textView51.setVisibility(View.VISIBLE);
                textView52.setVisibility(View.VISIBLE);
                textView53.setVisibility(View.VISIBLE);
                textView54.setVisibility(View.VISIBLE);
                textView55.setVisibility(View.VISIBLE);
                textView56.setVisibility(View.VISIBLE);
                textView57.setVisibility(View.VISIBLE);
                textView58.setVisibility(View.VISIBLE);
                textView59.setVisibility(View.VISIBLE);
            }
        });

        textView43.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://itaibcs.org.mx/ifile/leyes/LTransparenciaAccesoBCS26MAY2016.pdf")));
            }
        });
        textView44.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://itaibcs.org.mx/ifile/leyes/LResponsabilidadesServidoresPublicosBCS20DIC2015.pdf")));
            }
        });
        textView45.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://itaibcs.org.mx/ifile/leyes/LJusticiaAdministrativaBCS24DIC2008.pdf")));
            }
        });
        textView46.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://itaibcs.org.mx/ifile/leyes/LRemuneracionesServiciosPublicosBCS20MAR2011.pdf")));
            }
        });
        textView47.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://itaibcs.org.mx/ifile/leyes/NOR_01_17_001.pdf")));
            }
        });
        textView48.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://itaibcs.org.mx/ifile/leyes/LAdquisionesArrendamientosBCS20NOV2014.pdf")));
            }
        });
        textView49.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://itaibcs.org.mx/ifile/leyes/LPresupuestoControlGasto20NOV2014.pdf")));
            }
        });
        textView50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://itaibcs.org.mx/ifile/leyes/LIngresosBCS2016.pdf")));
            }
        });
        textView51.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://itaibcs.org.mx/ifile/leyes/LDesarrolloInstitucionalBCS.pdf")));
            }
        });
        textView52.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://itaibcs.org.mx/ifile/leyes/LOrganoFiscalizacionBCS.pdf")));
            }
        });
        textView53.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://itaibcs.org.mx/ifile/leyes/LDerechosProductosBCS10ABR2016.pdf")));
            }
        });
        textView54.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://itaibcs.org.mx/ifile/leyes/LSSDOF12nov2015.pdf")));
            }
        });
        textView55.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://itaibcs.org.mx/ifile/leyes/LFTDOF12JUN2015.pdf")));
            }
        });
        textView56.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://itaibcs.org.mx/ifile/leyes/01_LGTAIP.pdf")));
            }
        });
        textView57.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://secfin.bcs.gob.mx/fnz/wp-content/themes/fnz_bcs/assets/images/boletines/2005/18_parte_1.pdf")));
            }
        });
        textView58.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://secfin.bcs.gob.mx/fnz/wp-content/themes/fnz_bcs/assets/images/boletines/2007/58.pdf")));
            }
        });
        textView59.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://secfin.bcs.gob.mx/fnz/wp-content/themes/fnz_bcs/assets/images/boletines/2010/10.pdf")));
            }
        });


        //lineamientos
        textView60.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OcultarTextos();
                textView61.setVisibility(View.VISIBLE);
                textView62.setVisibility(View.VISIBLE);
                textView63.setVisibility(View.VISIBLE);
                textView64.setVisibility(View.VISIBLE);
                textView65.setVisibility(View.VISIBLE);
                textView66.setVisibility(View.VISIBLE);
                textView67.setVisibility(View.VISIBLE);
            }
        });
        textView61.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://itaibcs.org.mx/ifile/Normatividad/Lineamientosgrales.pdf")));
            }
        });
        textView62.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://itaibcs.org.mx/ifile/Normatividad/lineamientosprotecciondedatospersonales.pdf")));
            }
        });
        textView63.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://itaibcs.org.mx/ifile/Normatividad/Lineamientosgralessustanciaciondelprocedimientoderevision.pdf")));
            }
        });
        textView64.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://itaibcs.org.mx/ifile/Normatividad/Lineamientosdeusodelastecnologiasdelainformacion.pdf")));
            }
        });
        textView65.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://itaibcs.org.mx/ifile/Normatividad/Lineamientos_de_Obligaciones_y_Anexos.pdf")));
            }
        });
        textView66.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://itaibcs.org.mx/ifile/Normatividad/Lineamientos_de_Clasificacion_y_Desclasificacion_de_la_informacion.pdf")));
            }
        });
        textView67.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://itaibcs.org.mx/ifile/Normatividad/Lineamientos_para_la_Conservacion_de_Archivos.pdf")));
            }
        });


        //reglamentos
        textView68.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OcultarTextos();
                textView69.setVisibility(View.VISIBLE);
                textView70.setVisibility(View.VISIBLE);
                textView71.setVisibility(View.VISIBLE);
                textView72.setVisibility(View.VISIBLE);
            }
        });
        textView69.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://itaibcs.org.mx/ifile/Normatividad/RInteriorITAIP2016.pdf")));
            }
        });
        textView70.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://itaibcs.org.mx/ifile/Normatividad/REGLAMENTOINTERIORDETRABAJOITAIBCS.pdf")));
            }
        });
        textView71.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://itaibcs.org.mx/ifile/Normatividad/REGLAMENTODESESIONES.pdf")));
            }
        });
        textView72.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://itaibcs.org.mx/ifile/Normatividad/RInterior_abrogado.pdf")));
            }
        });

        //recomendaciones
        textView73.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OcultarTextos();
                textView74.setVisibility(View.VISIBLE);
            }
        });
        textView74.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://itaibcs.org.mx/ifile/Normatividad/RECOMENDACIONESGRALESPARALACREACIONDELASUAIDELASENTIDADESGUBERNAMENTALESYDEINTERESPUBLICODELESTADODEBCS.pdf")));
            }
        });

        //normas
        textView75.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OcultarTextos();
                textView76.setVisibility(View.VISIBLE);
                textView77.setVisibility(View.VISIBLE);
                textView78.setVisibility(View.VISIBLE);
                textView79.setVisibility(View.VISIBLE);
            }
        });

        textView76.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://itaibcs.org.mx/ifile/Normatividad/Normasinternasparalaevaluaciondeldesempenio.pdf")));
            }
        });
        textView77.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://itaibcs.org.mx/ifile/Normatividad/Normasinternasparalaorganizaciondelosarchivos.pdf")));
            }
        });
        textView78.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://itaibcs.org.mx/ifile/Normatividad/Normasparaelreclutamientodelosservidorespublicos.pdf")));
            }
        });
        textView79.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://itaibcs.org.mx/ifile/Normatividad/NormatividaddelContenidoyControldelaCuentaPublicadelEstadodeBajaCaliforniaSur.pdf")));
            }
        });

        //manuales
        textView80.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OcultarTextos();
                textView81.setVisibility(View.VISIBLE);
                textView82.setVisibility(View.VISIBLE);
            }
        });
        textView81.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://itaibcs.org.mx/ifile/manual/Manualdeorganizacion.pdf")));
            }
        });
        textView82.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://itaibcs.org.mx/ifile/manual/ManualdeAdministraciondeRemuneraciones.pdf")));
            }
        });

        //tratados internacionales
        textView83.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OcultarTextos();
                textView84.setVisibility(View.VISIBLE);
                textView85.setVisibility(View.VISIBLE);
            }
        });
        textView84.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://itaibcs.org.mx/ifile/tratadosinternacionales/D1BIS.pdf")));
            }
        });
        textView85.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://itaibcs.org.mx/ifile/tratadosinternacionales/D47.pdf")));
            }
        });
    }

    void OcultarTextos()
    {
        for (byte i=0;i<c.size();i++)
        {
            c.get(i).setVisibility(View.GONE);
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void CargarTabs(View view)
    {
        try {
            tabTitulos = (TabLayout) view.findViewById(R.id.tabTitulos);
            btnIrArriba = (FloatingActionButton) view.findViewById(R.id.btnIrArriba);
            tabTitulos.setTabMode(TabLayout.MODE_SCROLLABLE);
            tabTitulos.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            tabDatos.add((ScrollView) view.findViewById(R.id.scroll1));
            tabDatos.add((ScrollView) view.findViewById(R.id.scroll2));
            tabDatos.add((ScrollView) view.findViewById(R.id.scroll3));
            tabDatos.add((ScrollView) view.findViewById(R.id.scroll4));
            tabDatos.add((ScrollView) view.findViewById(R.id.scroll5));
            tabDatos.add((ScrollView) view.findViewById(R.id.scroll6));
            btnIrArriba.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tabDatos.get(pestanaActiva).fullScroll(View.FOCUS_UP);
                    btnIrArriba.hide();
                }
            });
            for (byte i = 0; i < tabDatos.size(); i++) {
                try {
                    final ScrollView s=tabDatos.get(i);
                    s.setVisibility(View.GONE);
                    s.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                        @Override
                        public void onScrollChanged() {
                            /*
                            if (s.getChildAt(0).getBottom() <= (s.getHeight() + s.getScrollY())) {
                                btnIrArriba.show();
                            }
                            else
                            {
                                btnIrArriba.hide();
                            }
                            */
                        }
                    });

                } catch (Exception e) {
                    String as = e.getMessage();
                }
            }
            tabDatos.get(0).setVisibility(View.VISIBLE);
            pestanaActiva = 0;

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
                    //MostrarPestana(tab);
                }
            });
        } catch (Exception e) {
            String as = e.getMessage();
        }
    }
    int pestanaActiva=-1;
    public void MostrarPestana(TabLayout.Tab tab)
    {
        for(int i=0;i<tabTitulos.getTabCount();i++)
        {
            if(tab.getText()==tabTitulos.getTabAt(i).getText()) {
                tabDatos.get(i).setVisibility(View.VISIBLE);
                pestanaActiva=i;
            }
            else
                tabDatos.get(i).setVisibility(View.GONE);
        }
    }
}