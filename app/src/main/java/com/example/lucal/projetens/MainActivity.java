package com.example.lucal.projetens;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

public class MainActivity extends Activity implements SensorEventListener {

    private SensorManager mSensorManager;

    static private Sensor GravitySensor;
    static private Sensor LinearASensor;
    static private Sensor RotationSensor;
    static private Sensor GyroSensor;
    private List<Sensor> liste ;
    static private Log log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //recuperation du sensor manager
        mSensorManager  = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // recuperation de tout les sensors actif sur la machine
        liste = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        // test des sensor actifs
        for(int i = 0 ; i <liste.size();i++){
            Sensor tmp = liste.get(i);
            System.out.println(tmp.toString());
        }

        // recuperation de certains sensors
        GravitySensor   = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        LinearASensor   = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        RotationSensor  = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        GyroSensor      = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

    }
    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        //float lux = event.values[0];

    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, GravitySensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

}
