package elyo.my.trackids;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.widget.Toast;

/**
 * Created by elyo_ on 18/05/2017.
 */

public class Servicio extends Service implements LocationListener {
    int mStartMode;       // indicates how to behave if the service is killed
    IBinder mBinder;      // interface for clients that bind
    boolean mAllowRebind; // indicates whether onRebind should be used
    private final Context c;
    double lat, lng;
    Location l;

    public Servicio(Context context) {
        super();
        c=context;
        try {

        }
        catch (Exception ex)
        {

        }

    }

    @Override
    public void onCreate() {
        // The service is being created
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
    }
    @Override
    public void onDestroy() {
        // The service is no longer used and is being destroyed
    }

    @Override
    public void onLocationChanged(Location location) {
        //Guardar ubicacion en base de datos
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        try {

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
}
