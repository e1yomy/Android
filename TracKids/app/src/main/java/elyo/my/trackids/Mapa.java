package elyo.my.trackids;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.LOCATION_SERVICE;
import static elyo.my.trackids.ListaHijos.index;
import static elyo.my.trackids.ListaHijos.listaHijos;
import static elyo.my.trackids.Principal.PantallaHijos;
import static elyo.my.trackids.Principal.UltimaRutaConocida;
import static elyo.my.trackids.Principal.c;
import static elyo.my.trackids.Principal.pantalla;
import static elyo.my.trackids.Principal.preferences;
import static elyo.my.trackids.Principal.usuario;
import static elyo.my.trackids.ServiciosWeb.puntos;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Mapa.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Mapa#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Mapa extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Mapa() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Mapa.
     */
    // TODO: Rename and change types and number of parameters
    public static Mapa newInstance(String param1, String param2) {
        Mapa fragment = new Mapa();
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

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            try {

                m.clear();
                if (location != null) {
                    if (pantalla == 1) {
                        ActualizarMarcador(location);
                    } else {
                        ActualizarMarcador(location);
                        MostrarPosicion();
                    }
                }
            } catch (Exception e) {
                Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {
            try {
                m.clear();
                if (la != null) {
                    if (locationManager != null) {
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
                        l = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
                    }
                    Location a=new Location("a");
                    a.setLatitude(preferences.getFloat("actualLat",0.0f));
                    a.setLongitude(preferences.getFloat("actualLon",0.0f));
                    if (pantalla == 1) {
                        ActualizarMarcador(l);
                    } else {
                        ActualizarMarcador(l);
                        MostrarPosicion();
                    }
                }
            }
            catch (Exception e)
            {
                Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onProviderDisabled(String provider) {
            try {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
            catch (Exception e)
            {
                Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    };

    GoogleMap m;
    LocationManager locationManager;
    Location l;
    boolean gps;
    LatLng la;
    String nombre;
    FloatingActionButton opciones,miUbicacion, volver;
    SeekBar zoom;
    int zoomlevel;
    GoogleApiClient mGoogleApiClient;
    ArrayList<LatLng> MarkerPoints;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view=inflater.inflate(R.layout.fragment_mapa, container, false);
        try {
            SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                    .findFragmentById(R.id.mapa1);
            mapFragment.getMapAsync(this);

            locationManager = (LocationManager) c.getSystemService(LOCATION_SERVICE);
            gps = locationManager.isProviderEnabled(locationManager.GPS_PROVIDER);
            opciones = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);
            miUbicacion = (FloatingActionButton) view.findViewById(R.id.floatingActionButton2);
            volver = (FloatingActionButton) view.findViewById(R.id.floatingActionButton3);
            zoom=(SeekBar)view.findViewById(R.id.seekZoom);
            MarkerPoints= new ArrayList<>();
            MarkerPoints.add(null);
            MarkerPoints.add(null);

            Zoom();
            BotonOpciones(view);
            BotonMiUbicacion();
            BotonVolver();
            if(pantalla==1){
                opciones.hide();
                miUbicacion.show();
                volver.hide();
                //
            }
            else {
                opciones.show();
                miUbicacion.show();
                volver.show();
            }
        }
        catch (Exception ex)
        {
            Toast.makeText( c, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    private void BotonVolver() {
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PantallaHijos();
            }
        });
    }

    private void BotonMiUbicacion() {
        miUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                m.clear();
                if(pantalla==1)
                {
                    ActualizarMarcador(l);
                }
                else
                {
                    ActualizarMarcador(l);
                    MostrarPosicion();
                }
                }
                catch (Exception e)
                {
                    Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void Zoom() {
        zoom.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                try {
                    zoomlevel = progress;
                    if (pantalla == 1) {
                        m.animateCamera(CameraUpdateFactory.newLatLngZoom(la, zoomlevel));
                    } else {
                        //MostrarPosicion();
                        m.animateCamera(CameraUpdateFactory.newLatLngZoom(m.getCameraPosition().target, progress));
                        //m.animateCamera(CameraUpdateFactory.newLatLngZoom(la, zoomlevel));
                    }
                }
                catch (Exception e)
                {
                    Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                //
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void BotonOpciones(final View view) {
        opciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert= new AlertDialog.Builder(c);
                alert.setTitle("Opciones");
                alert.setItems(R.array.opciones, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //which es el valor del index de la lista de elementos que se muestran en el Dialog
                        switch (which)
                        {
                            case 0:
                                MostrarUltimaUbicacionConocidaDeHijo();
                                break;
                            case 1:
                                MostrarUltimaRutaConocida();
                                break;
                            case 2:
                                break;
                            case 3:
                                DatePickerDialog pick= new DatePickerDialog(c, new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker v, int year, int month, int dayOfMonth) {
                                        month++; //meses está de 0 a 11

                                        //Cuando no haya ningun registro del hijo en la fecha seleccionada, deberá mostrar un Snackz
                                        Snackbar.make(view,dayOfMonth+" "+(month+1)+" "+ year,Snackbar.LENGTH_LONG).show();

                                    }
                                }, Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                                pick.setTitle("Seleccionar fecha");
                                pick.show();
                                break;


                        }

                    }
                });
                alert.show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            ConfigurarMapa(googleMap);

            if (ActivityCompat.checkSelfPermission(c, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(c, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(c, "No se tienen los permisos necesarios", Toast.LENGTH_SHORT).show();
                return;
            }
            locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 10000, 10f, locationListener);
            locationManager = (LocationManager) c.getSystemService(LOCATION_SERVICE);

            //if (gps)
            {
                if (locationManager != null) {
                    l = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
                }
                if (pantalla == 1) {
                    ActualizarMarcador(l);
                } else {
                    ////////////Mostrar la ubicacion que se mando antes
                    ActualizarMarcador(l);
                    MostrarPosicion();
                }
            }
            //else
                {
            //  startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            // Toast.makeText(c, "GPS no activo", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception ex) {
            Toast.makeText( c, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void ConfigurarMapa(GoogleMap googleMap){
        try {
            m = googleMap;
            m.clear();
            m.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            //m.getUiSettings().setZoomControlsEnabled(true);
            m.getUiSettings().setZoomGesturesEnabled(true);
            m.getUiSettings().setCompassEnabled(true);
            m.getUiSettings().setScrollGesturesEnabled(true);
            m.getUiSettings().setMapToolbarEnabled(false);
            m.setPadding(0, 0, 15, 130);
            //zoom.setProgress(zoomlevel,true);
            m.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                @Override
                public boolean onMyLocationButtonClick() {
                    ActualizarMarcador(l);
                    return false;
                }
            });
            m.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
                @Override
                public void onCameraChange(CameraPosition cameraPosition) {
                    zoom.setProgress((int) cameraPosition.zoom,true);
                }
            });
        }
        catch (Exception ex)
        {
            Toast.makeText( c, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void ActualizarMarcador(Location lo){
        l=lo;
        if (l != null) {
            //la=new LatLng(l.getLatitude(), l.getLongitude());
            la= new LatLng(Double.valueOf(preferences.getFloat("actualLat",0.0f)),Double.valueOf(preferences.getFloat("actualLon",0.0f)));
            preferences.edit().putFloat("actualLat", (float) l.getLatitude()).putFloat("actualLon", (float) l.getLongitude()).commit();
            MarkerOptions mar=new MarkerOptions().position(la).title(usuario.nombres).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_markerpadre));
            m.addMarker(mar);
            m.animateCamera(CameraUpdateFactory.newLatLngZoom(la,zoomlevel));
            MostrarMisLugares();
            if(pantalla!=1)
            {
                MarkerPoints.set(0,la);
            }
        }
    }
    public void MostrarPosicion(){
        try {
            la=new LatLng(listaHijos.get(index).latitud,listaHijos.get(index).longitud);
            MarkerPoints.set(1, la);
            if (MarkerPoints.get(0) != null && MarkerPoints.get(1) != null) {
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                MarkerOptions mar = new MarkerOptions()
                        .position(la)
                        .title(listaHijos.get(index).nombre)
                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_markerhijo))
                        ;
                m.addMarker(mar);
                builder.include(MarkerPoints.get(0));
                builder.include(MarkerPoints.get(1));
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(builder.build(), 100);
                m.animateCamera(cu);
                //m.animateCamera(CameraUpdateFactory.newLatLngZoom(la,zoomlevel));
                String url = getUrl(MarkerPoints.get(0), MarkerPoints.get(1));
                FetchUrl fetchUrl = new FetchUrl();
                fetchUrl.execute(url);

            }
        }
        catch (Exception e)
        {
            //Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public void MostrarUltimaUbicacionConocidaDeHijo()
    {
        m.clear();
        la=new LatLng(listaHijos.get(index).latitud,listaHijos.get(index).longitud);
        MarkerOptions mar=new MarkerOptions().position(la).title(listaHijos.get(index).nombre).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_markerhijo));
        m.addMarker(mar);
        m.animateCamera(CameraUpdateFactory.newLatLngZoom(la,zoomlevel));
    }

    public void MostrarUltimaRutaConocida(){
        try {
            m.clear();
            UltimaRutaConocida(listaHijos.get(index).id);
            Thread.sleep(500);
            if(preferences.getBoolean("existe",false))
            {
                 PolylineOptions p=new PolylineOptions()
                    .color(Color.BLACK)
                    .width(3);
                for(int i=1;i<puntos.size();i++)
                {
                    p.add(puntos.get(i));
                }
                //Polyline mMutablePolyline = m.addPolyline(p);
                m.addPolyline(p);
            }

        }
        catch (Exception ex)
        {
            Toast.makeText(c, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    /// Recibir la fecha como parametro, para ruta de hoy, fecha actual, para otra fecha, resultado del dialogo
    public void MostrarRutaDeFecha(){
        m.clear();

    }

    private String getUrl(LatLng origin, LatLng dest) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Sensor enabled
        String sensor = "sensor=false";
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;
        return url;
    }
    /**
     * A method to download json data from url
     */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();
            Log.d("downloadUrl", data.toString());
            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    // Fetches data from url passed
    private class FetchUrl extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
                Log.d("Background Task data", data.toString());
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);

        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(c)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                //.addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                Log.d("ParserTask",jsonData[0].toString());
                DataParser parser = new DataParser();
                Log.d("ParserTask", parser.toString());

                // Starts parsing data
                routes = parser.parse(jObject);
                Log.d("ParserTask","Executing routes");
                Log.d("ParserTask",routes.toString());

            } catch (Exception e) {
                Log.d("ParserTask",e.toString());
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points;
            PolylineOptions lineOptions = null;

            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(10);
                lineOptions.color(Color.RED);

                Log.d("onPostExecute","onPostExecute lineoptions decoded");

            }

            // Drawing polyline in the Google Map for the i-th route
            if(lineOptions != null) {
                m.addPolyline(lineOptions);
            }
            else {
                Log.d("onPostExecute","without Polylines drawn");
            }
        }
    }

    BaseDatosHelper b= new BaseDatosHelper(c);
    public void MostrarMisLugares()
    {
        Cursor c= b.selectLugares(preferences.getString("correoUsuario",""));
        if(c.moveToFirst())
        {
            do {
                m.addMarker(new MarkerOptions()
                        .position(new LatLng(c.getDouble(1),c.getDouble(2))).title(c.getString(0))
                        .icon(bitmapSizeByScall()));
                m.addCircle(new CircleOptions()
                        .center(new LatLng(c.getDouble(1),c.getDouble(2)))
                        .radius(30)
                        .strokeWidth(3.0f)
                        .strokeColor(R.color.colorPrimaryDark)
                        .fillColor(R.color.colorAccent)
                        .clickable(false));

            }while (c.moveToNext());
        }
    }
    public BitmapDescriptor bitmapSizeByScall() {
        BitmapDescriptor bit;
        Bitmap bi= BitmapFactory.decodeResource(c.getResources(),R.mipmap.ic_markerlugar);
        Bitmap bi2 = Bitmap.createScaledBitmap(bi,
                Math.round(bi.getWidth() * 0.5f),
                Math.round(bi.getHeight() * 0.5f), false);
        bit=BitmapDescriptorFactory.fromBitmap(bi2);
        return bit;
    }
}
