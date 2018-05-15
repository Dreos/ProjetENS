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
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity implements SensorEventListener {

    private SensorManager mSensorManager;
    static private Sensor GravitySensor;
    static private Sensor LinearASensor;
    static private Sensor RotationSensor;
    static private Sensor GyroSensor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager  = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

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
        // The light sensor returns a single value.
        // Many sensors return 3 values, one for each axis.
        float lux = event.values[0];
        // Do something with this sensor value.
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
