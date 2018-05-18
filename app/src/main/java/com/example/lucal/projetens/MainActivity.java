package com.example.lucal.projetens;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;

public class MainActivity extends Activity implements  SensorEventListener {

    private SensorManager mSensorManager;


    private List<Sensor> liste ;
    static private Log log;
    private static  WifiManager mWifiManager ;
    private static Context context ;
    private Sensor accelerometer;
    /*HashMap<Integer , Sensor> listSensor ;
    HashMap<Integer , IHMSensor> listIHMSensor ;*/



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //recuperation du sensor manager
        mSensorManager  = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        accelerometer = mSensorManager .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // recuperation de tout les sensors actif sur la machine
        liste = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        // test des sensor actifs
        for(int i = 0 ; i <liste.size();i++){
            Sensor tmp = liste.get(i);
            System.out.println(tmp.toString());
        }




        // recuperation de certains sensors



    }






    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event){

        SensorEvent e = event ;
        if (e.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = e.values[0];
            float y = e.values[1];
            float z = e.values[2];
            System.out.println(e.sensor.getType() + ":");
            System.out.println("x: " + x + "  y: " + y + "  z: " + z);
        }


    }

    /*public void addSensor(Sensor s){
        Sensor tmpsensor = null;
        try{
            tmpsensor = listSensor.get(s);
        }
        catch(Exception e){

        }
        if (tmpsensor!=null) {
            listSensor.put(s.getType(), s);
            IHMSensor tmp = new IHMSensor(s);
            listIHMSensor.put(s.getType(),tmp);
        }



    }
    public void deleteSensor(Sensor s){
        listSensor.remove(s);
        listIHMSensor.remove(s);

    }



    public class IHMSensor{
        Sensor sensor ;
        public IHMSensor(Sensor s){
            sensor = s ;
        }
    }


    public void wifiScan(){
        mWifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);

        //
        if (mWifiManager.isWifiEnabled() == false) {

            Toast.makeText(getApplicationContext(), "le wifi desactivé , nous l'activons", Toast.LENGTH_LONG).show();
            mWifiManager.setWifiEnabled(true);
        }
        // enregistrement du resultat du scan wifi
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);

        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context context, Intent intent) {

                List<ScanResult> results = mWifiManager.getScanResults();
                final int N = results.size();

                Log.v(TAG, "Wi-Fi Scan Results ... Count:" + N);
                for(int i=0; i < N; ++i) {
                    Log.v(TAG, "  BSSID       =" + results.get(i).BSSID);
                    Log.v(TAG, "  SSID        =" + results.get(i).SSID);
                    Log.v(TAG, "  Capabilities=" + results.get(i).capabilities);
                    Log.v(TAG, "  Frequency   =" + results.get(i).frequency);
                    Log.v(TAG, "  Level       =" + results.get(i).level);
                    Log.v(TAG, "---------------");
                }
            }
        }, filter);
        // start WiFi Scan
        mWifiManager.startScan();
    }*/
    @Override
    protected void onResume() {
        super.onResume();

        mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

}
