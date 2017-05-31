package elyo.my.trackids;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import java.util.Calendar;

import static elyo.my.trackids.Principal.CargarUbicacion;
import static elyo.my.trackids.Principal.CargarUltimaUbicacion;
import static elyo.my.trackids.Principal.preferences;

/**
 * Created by elyo_ on 18/05/2017.
 */

public class Servicio extends Service {
    int mStartMode;       // indicates how to behave if the service is killed
    IBinder mBinder;      // interface for clients that bind
    boolean mAllowRebind; // indicates whether onRebind should be used
    private Context c;
    double lat, lan;
    LocationManager locationManager;
    Location l;
    boolean gps;
    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            //Guardar ubicacion en base de datos
            //a.setLatitude(preferences.getFloat("actualLat",0.0f));
            if(preferences.getInt("sesion",0)==1||preferences.getInt("sesion",0)==2) {
                try {
                    //android.os.Debug.waitForDebugger();
                    gps = locationManager.isProviderEnabled(locationManager.GPS_PROVIDER);
                    while (!gps) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                    if (ActivityCompat.checkSelfPermission(Principal.c, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Principal.c, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                    lat = l.getLatitude();
                    lan = l.getLongitude();
                    Toast.makeText(Principal.c, "" + lat, Toast.LENGTH_SHORT).show();
                    Toast.makeText(Principal.c, "" + lan, Toast.LENGTH_SHORT).show();
                    preferences.edit()
                            .putFloat("actualLat", (float) lat)
                            .putFloat("actualLon", (float) lan)
                            .commit();
                    CargarUbicacionDe(preferences.getString("idUsuario", ""));
                    CargarUltimaUbicacion(preferences.getString("idUsuario", ""),lat,lan);
                } catch (Exception ex) {
                    Toast.makeText(c, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {
            try {
                //onLocationChanged();
                Location a=new Location("a");
                a.setLatitude(lat);
                a.setLongitude(lan);
                onLocationChanged(a);
            }
            catch (Exception e)
            {
                Toast.makeText(Principal.c, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onProviderDisabled(String provider) {
            try {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
            catch (Exception e)
            {
                Toast.makeText(Principal.c, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
    };
    public Servicio(){}
    public Servicio(Context context) {
        super();
        //android.os.Debug.waitForDebugger();
        c = context;
        try {
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
            locationManager = (LocationManager) c.getSystemService(LOCATION_SERVICE);
            gps = locationManager.isProviderEnabled(locationManager.GPS_PROVIDER);
            while (!gps) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
            locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 30000, 10f, locationListener);
            l = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            lat = (preferences.getFloat("actualLat", 0.0f));
            lan = (preferences.getFloat("actualLon", 0.0f));
            Toast.makeText(Principal.c, "" + lat, Toast.LENGTH_SHORT).show();
            Toast.makeText(Principal.c, "" + lan, Toast.LENGTH_SHORT).show();

        } catch (Exception ex) {
            Toast.makeText(c, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCreate() {
        // The service is being created
        //android.os.Debug.waitForDebugger();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // The service is starting, due to a call to startService()
        return START_STICKY;

    }

    @Override
    public IBinder onBind(Intent intent) {
        // A client is binding to the service with bindService()
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        // All clients have unbound with unbindService()
        return mAllowRebind;
    }

    @Override
    public void onRebind(Intent intent) {
        // A client is binding to the service with bindService(),
        // after onUnbind() has already been called
        lat = (preferences.getFloat("actualLat", 0.0f));
        lan = (preferences.getFloat("actualLon", 0.0f));
        Toast.makeText(Principal.c, "" + lat, Toast.LENGTH_SHORT).show();
        Toast.makeText(Principal.c, "" + lan, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        // The service is no longer used and is being destroyed
        try{
            android.os.Debug.waitForDebugger();
            Intent intent = new Intent("elyo.my.trackids.Servicio");
            //intent.putExtra("yourvalue", "torestore");
            sendBroadcast(intent);

        gps = locationManager.isProviderEnabled(locationManager.GPS_PROVIDER);
        while(!gps)
        {
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }
    }
        catch (Exception ex)
    {
        Toast.makeText(c, ex.getMessage(), Toast.LENGTH_SHORT).show();
    }
    }
    String fecha="";
    private void CargarUbicacionDe(String id) {
        //Mandar ubicacion a la base de datos
        try {
            //android.os.Debug.waitForDebugger();
            ServiciosWeb s = new ServiciosWeb();
            Calendar ca= Calendar.getInstance();
            fecha= ca.get(Calendar.YEAR)+"-"+
                    (ca.get(Calendar.MONTH)+1)+"-"+
                    ca.get(Calendar.DAY_OF_MONTH)+" "+
                    ca.get(Calendar.HOUR_OF_DAY)+":"+
                    ca.get(Calendar.MINUTE)+":"+
                    ca.get(Calendar.SECOND);
            CargarUbicacion(id, lat, lan, fecha);
            CargarUltimaUbicacion(id, lat, lan);

        }
        catch (Exception ex)
        {
            //Toast.makeText(c, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
