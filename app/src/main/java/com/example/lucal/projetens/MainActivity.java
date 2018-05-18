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
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;

public class MainActivity extends Activity implements  SensorEventListener {

    private static Context context ;
    static private Log log;

    private static  WifiManager mWifiManager ;

    private SensorManager mSensorManager;
    private List<Sensor> liste ;
    private ArrayList<Sensor> sensorListened;
    private HashMap<Sensor,IHMSensor> listIHM_S;

    private Sensor accelerometre;
    private Sensor gyroscope;
    private Sensor magnetometre;

    private LinearLayout layout;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        context         =   getApplicationContext();


        mSensorManager  =   (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometre   =   (Sensor)        mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyroscope       =   (Sensor)        mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        magnetometre    =   (Sensor)        mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        sensorListened  = new ArrayList<Sensor>();

        layout          = new LinearLayout(this);
        setContentView(layout);
        layout.setOrientation(LinearLayout.VERTICAL);

        //recuperation du sensor manager

        wifiScan();

        // recuperation de tout les sensors actif sur la machine
        listIHM_S       = new HashMap<Sensor,IHMSensor>();
        liste = mSensorManager.getSensorList(Sensor.TYPE_ALL);

        for(Sensor s : liste){
            IHMSensor UI = new IHMSensor(s);
            listIHM_S.put(s,UI);
            deleteSensor(s);
        }


        addSensor(accelerometre);
        addSensor(gyroscope);
        addSensor(magnetometre);
        deleteSensor(accelerometre);



    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    @Override
    public final void onSensorChanged(SensorEvent event){

        SensorEvent e = event ;
        Sensor s = event.sensor;
        String type = s.getStringType();

        for(Sensor sensor : sensorListened){
            if(sensor.getType()==e.sensor.getType()){

                float x = e.values[0];
                float y = e.values[1];
                float z = e.values[2];

                IHMSensor UI = listIHM_S.get(sensor);
                UI.setValeurs(x,y,z);

                System.out.println(type + ":");
                System.out.println("x: " + x + "  y: " + y + "  z: " + z);
            }
        }


    }
    // ajoute un sensor a la liste des sensors selectionné et l'affiche
    public void addSensor(Sensor s){
        IHMSensor UI = listIHM_S.get(s);
        UI.visible();
        sensorListened.add(s);
        mSensorManager.registerListener(this, s , SensorManager.SENSOR_DELAY_NORMAL);

    }
    // retire un sensor de la liste des sensors selectionnés et le rend invisible
    public void deleteSensor(Sensor s){
        IHMSensor UI = listIHM_S.get(s);
        UI.invisible();
        if (sensorListened.contains(s)){
            sensorListened.remove(s);
        }
        mSensorManager.unregisterListener(this,s);
    }


    // Class qui gere l'affichage des sensors
    public class IHMSensor{

        Sensor sensor ;

        TextView type   = new TextView(context) ;
        TextView x      = new TextView(context) ;
        TextView y      = new TextView(context) ;
        TextView z      = new TextView(context) ;

        @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
        public IHMSensor(Sensor s){

            sensor = s ;
            type.setText(s.getStringType());
            x.setText("0.0");
            y.setText("0.0");
            z.setText("0.0");
            layout.addView(type);
            layout.addView(x);
            layout.addView(y);
            layout.addView(z);


        }

        public void setValeurs(float X,float Y,float Z){
            x.setText(String.valueOf(X));
            y.setText(String.valueOf(Y));
            z.setText(String.valueOf(Z));
        }

        public void invisible(){
            type.setVisibility(View.INVISIBLE);
            x.setVisibility(View.INVISIBLE);
            y.setVisibility(View.INVISIBLE);
            z.setVisibility(View.INVISIBLE);
        }

        public void visible(){
            type.setVisibility(View.VISIBLE);
            x.setVisibility(View.VISIBLE);
            y.setVisibility(View.VISIBLE);
            z.setVisibility(View.VISIBLE);
        }
    }

    // classe qui gere l'affichage des informations WIFI
    public void wifiScan(){
        mWifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);
        mWifiManager.setWifiEnabled(true);

        // enregistrement du resultat du scan wifi
        List<ScanResult> results = mWifiManager.getScanResults();
        final int N = results.size();

        System.out.println( "Wi-Fi Scan Results ... Count:" + N);
        for(int i=0; i < N; ++i) {
            System.out.println( "  BSSID       =" + results.get(i).BSSID);
            System.out.println( "  SSID        =" + results.get(i).SSID);
            System.out.println( "  Capabilities=" + results.get(i).capabilities);
            System.out.println( "  Frequency   =" + results.get(i).frequency);
            System.out.println( "  Level       =" + results.get(i).level);
            System.out.println( "---------------");
        }
        // start WiFi Scan
        mWifiManager.startScan();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

}
