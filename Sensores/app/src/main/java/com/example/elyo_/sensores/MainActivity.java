package com.example.elyo_.sensores;

import android.annotation.TargetApi;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class MainActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private ArrayList<Sensor> sensores;
    private int[] sensorType = {
            Sensor.TYPE_ACCELEROMETER,
            Sensor.TYPE_AMBIENT_TEMPERATURE,
            Sensor.TYPE_GAME_ROTATION_VECTOR,
            Sensor.TYPE_GRAVITY,
            Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR,
            Sensor.TYPE_GYROSCOPE,
            Sensor.TYPE_GYROSCOPE_UNCALIBRATED,
            Sensor.TYPE_HEART_BEAT,
            Sensor.TYPE_HEART_RATE,
            Sensor.TYPE_LIGHT,
            Sensor.TYPE_LINEAR_ACCELERATION,
            Sensor.TYPE_MAGNETIC_FIELD,
            Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED,
            Sensor.TYPE_MOTION_DETECT,
            Sensor.TYPE_POSE_6DOF,
            Sensor.TYPE_PRESSURE,
            Sensor.TYPE_PROXIMITY,
            Sensor.TYPE_RELATIVE_HUMIDITY,
            Sensor.TYPE_SIGNIFICANT_MOTION,
            Sensor.TYPE_STATIONARY_DETECT,
            Sensor.TYPE_STEP_COUNTER,
            Sensor.TYPE_STEP_DETECTOR,
            Sensor.TYPE_ORIENTATION,
            Sensor.TYPE_TEMPERATURE};
    private String[] sensorName = {
            Sensor.STRING_TYPE_ACCELEROMETER,
            Sensor.STRING_TYPE_AMBIENT_TEMPERATURE,
            Sensor.STRING_TYPE_GAME_ROTATION_VECTOR,
            Sensor.STRING_TYPE_GRAVITY,
            Sensor.STRING_TYPE_GEOMAGNETIC_ROTATION_VECTOR,
            Sensor.STRING_TYPE_GYROSCOPE,
            Sensor.STRING_TYPE_GYROSCOPE_UNCALIBRATED,
            Sensor.STRING_TYPE_HEART_BEAT,
            Sensor.STRING_TYPE_HEART_RATE,
            Sensor.STRING_TYPE_LIGHT,
            Sensor.STRING_TYPE_LINEAR_ACCELERATION,
            Sensor.STRING_TYPE_MAGNETIC_FIELD,
            Sensor.STRING_TYPE_MAGNETIC_FIELD_UNCALIBRATED,
            Sensor.STRING_TYPE_MOTION_DETECT,
            Sensor.STRING_TYPE_POSE_6DOF,
            Sensor.STRING_TYPE_PRESSURE,
            Sensor.STRING_TYPE_PROXIMITY,
            Sensor.STRING_TYPE_RELATIVE_HUMIDITY,
            Sensor.STRING_TYPE_SIGNIFICANT_MOTION,
            Sensor.STRING_TYPE_STATIONARY_DETECT,
            Sensor.STRING_TYPE_STEP_COUNTER,
            Sensor.STRING_TYPE_STEP_DETECTOR,
            Sensor.STRING_TYPE_ORIENTATION,
            Sensor.STRING_TYPE_TEMPERATURE};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager) getSystemService(this.SENSOR_SERVICE);
        sensores = new ArrayList<Sensor>();
        String[] listaValores = new String[sensorType.length];
        for (int k=0; k<sensorType.length; k++)
            listaValores[k] = "";
        final Adaptador adapter = new Adaptador(listaValores, this);
        final ListView lista = (ListView)findViewById(R.id.listaSensores);
        lista.setAdapter(adapter);
        for (int i=0; i<sensorType.length; i++)
        {
            sensores.add(sensorManager.getDefaultSensor(sensorType[i]));
            adapter.valores[i] = sensorName[i] + ": ";
            if (sensores.get(i)!=null)
            {
                final int j = i;
                sensorManager.registerListener(new SensorEventListener()
                {
                    @Override
                    public void onSensorChanged(SensorEvent event) {
                        if (event.values.length >0) {
                            adapter.valores[j] = sensorName[j] + ": (";
                            for (int v = 0; v < event.values.length; v++) {
                                if (v > 0)
                                    adapter.valores[j] += ", ";
                                adapter.valores[j] += event.values[v];
                            }
                            adapter.valores[j] += ")";
                            lista.invalidate();
                        }
                        //adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onAccuracyChanged(Sensor sensor, int accuracy) {

                    }
                }, sensores.get(i), SensorManager.SENSOR_DELAY_FASTEST);
                //sensorManager.requestTriggerSensor(new TriggerEventListener() {
                /*@Override
                    public void onTrigger(TriggerEvent event) {
                        adapter.valores[j] = sensorName[j] + ": (";
                        for (int v=0; v< event.values.length; v++) {
                            if (v>0)
                                adapter.valores[j] += ", ";
                            adapter.valores[j] += event.values[v];
                        }
                        adapter.valores[j] += ")";
                        adapter.notifyDataSetChanged();
                    }
                }, sensores.get(i));*/
            }
            else
            {
                listaValores[i] += "-";
            }
        }

    }
}
