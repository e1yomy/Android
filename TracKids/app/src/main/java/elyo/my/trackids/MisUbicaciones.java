package elyo.my.trackids;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import static elyo.my.trackids.Principal.c;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MisUbicaciones.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MisUbicaciones#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MisUbicaciones extends Fragment implements OnMapReadyCallback, LocationListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MisUbicaciones() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MisUbicaciones.
     */
    // TODO: Rename and change types and number of parameters
    public static MisUbicaciones newInstance(String param1, String param2) {
        MisUbicaciones fragment = new MisUbicaciones();
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

    ListView listMisUbicaciones;
    List<String> lista1;
    List<LatLng> lista2;
    BaseDatosHelper b;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mis_ubicaciones, container, false);
        LinearLayout misubicacionescontenedor = (LinearLayout) view.findViewById(R.id.misubicacionescontenedor);
        int altura = misubicacionescontenedor.getHeight() / 2;
        listMisUbicaciones = (ListView) view.findViewById(R.id.listMisUbicaciones);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.mapa2);
        mapFragment.getMapAsync(this);
        b=new BaseDatosHelper(c);
        EventosLista();

        lista1 = new ArrayList<>();
        lista2 = new ArrayList<>();


        ActualizarLista();

        return view;
    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    GoogleMap m;
    static LatLng locacion;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        m = googleMap;
        m.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        m.getUiSettings().setZoomControlsEnabled(true);
        m.getUiSettings().setZoomGesturesEnabled(true);
        m.getUiSettings().setCompassEnabled(true);
        m.getUiSettings().setScrollGesturesEnabled(true);
        m.getUiSettings().setMapToolbarEnabled(false);
        m.getUiSettings().setMyLocationButtonEnabled(true);
        if (ActivityCompat.checkSelfPermission(c, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(c, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        m.setMyLocationEnabled(true);
        m.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                locacion=latLng;
                AlertDialog.Builder alert= new AlertDialog.Builder(c);
                alert.setTitle("Agregar lugar");
                alert.setMessage("Ingrese el nombre que se le asignará a la ubicación.");

                final EditText input = new EditText(c);
                input.setHeight(100);
                input.setWidth(340);
                input.setGravity(Gravity.LEFT);

                input.setImeOptions(EditorInfo.IME_ACTION_DONE);
                alert.setView(input);

                alert.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(input.getText().toString()!="") {

                            List<String> datos=new ArrayList<String>();
                            datos.add("alguien");
                            datos.add(input.getText().toString());
                            datos.add(locacion.latitude+"");
                            datos.add(locacion.longitude+"");
                            b.insertRow("mislugares",datos);
                            ActualizarLista();
                        }
                        else
                        {
                            Toast.makeText(c, "La ubicación no se ha guardado.\nHay que asignar un nombre.", Toast.LENGTH_SHORT).show();
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
        });
    }
    public void ActualizarLista(){
        try{

            Cursor c1 = b.selectLugares("alguien");
            if(c1.moveToFirst())
            {
                lista1.clear();
                lista2.clear();
                do{
                    lista1.add(c1.getString(0));
                    lista2.add(new LatLng(c1.getDouble(1),c1.getDouble(2)));
                }while (c1.moveToNext());
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                        c,
                        android.R.layout.simple_list_item_1,
                        lista1 );

                listMisUbicaciones.setAdapter(arrayAdapter);
            }


        }
        catch (Exception ec){

        }
    }
    public void ActualizarMarcador(int posicion){
            m.clear();
            m.addMarker(new MarkerOptions().position(lista2.get(posicion)).title(lista1.get(posicion)).icon(BitmapDescriptorFactory.defaultMarker()));
            m.animateCamera(CameraUpdateFactory.newLatLngZoom(lista2.get(posicion),18));
        //m.getCameraPosition().zoom
    }
    public void EventosLista(){
        listMisUbicaciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Recuperar la posicion y usar la lista1 para el nombre y la lista 2 para la ubicacion
                ActualizarMarcador(position);
            }
        });
        listMisUbicaciones.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alert= new AlertDialog.Builder(c);
                alert.setTitle("Eliminar ubicación");
                alert.setMessage("¿Está seguro que desea eliminar \'"+lista1.get(position)+"\'?");
                alert.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        b.eliminarLugar("alguien",lista1.get(position));
                        m.clear();
                        ActualizarLista();
                    }
                });
                alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();


                return true;
            }
        });
    }

}
