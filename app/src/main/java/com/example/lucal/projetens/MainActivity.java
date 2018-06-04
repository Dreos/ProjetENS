package com.example.lucal.projetens;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.System.*;

public class MainActivity extends Activity implements  SensorEventListener {

    private static Context context ;
    static private Log log;

    private WifiManager wifiManager ;

    private SensorManager mSensorManager;

    // liste des sensors affiché
    private ArrayList<Sensor> sensorListened;


    // liste contenant l'IHM de toutles sensors disponibles ;
    private HashMap<Sensor,IHMSensor> listIHM_S;


    private Sensor accelerometre;
    private Sensor gyroscope;
    private Sensor magnetometre;

    private LinearLayout layout;

    private WifiScanReciever broadcastReceiver ;



    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        context         =   getApplicationContext();

        // demande plusieurs permissions ( voir Outils )
        Outils outils = new Outils();
        outils.permissionsRequest(outils.getPermissions1(),this,this);


        // recuperation des sensors et du sensor manager
        mSensorManager  =   (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometre   =   (Sensor)        mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyroscope       =   (Sensor)        mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        magnetometre    =   (Sensor)        mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        sensorListened  = new ArrayList<Sensor>();

        layout          = new LinearLayout(this);
        setContentView(layout);
        layout.setOrientation(LinearLayout.VERTICAL);
        Button boutonRechercher = new Button (this);
        layout.addView(boutonRechercher);
        //recuperation du sensor manager


        // recuperation de tout les sensors actif sur la machine
        listIHM_S            = new HashMap<Sensor,IHMSensor>();
        List<Sensor> liste   = mSensorManager.getSensorList(Sensor.TYPE_ALL);

        // on remplie la listIHM_S avec les IHM sensors et on les mets tous invisibles ;
        for(Sensor s : liste){
            IHMSensor UI = new IHMSensor(s,layout,this);
            listIHM_S.put(s,UI);
            deleteSensor(s);
        }

        // ajout de sensors pour le tests
        addSensor(accelerometre);
        addSensor(gyroscope);
        addSensor(magnetometre);

        // supression d'un sensor pour les test
        deleteSensor(accelerometre);

        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        broadcastReceiver = new WifiScanReciever(this,this);


        // On attache le receiver au scan result
        registerReceiver(broadcastReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));





        boutonRechercher.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if(wifiManager != null)
                    wifiManager.startScan();
            }
        });


    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    @Override

    // fonction qui est appele a chaque rafraichissement d'un sensor
    public final void onSensorChanged(SensorEvent event){

        SensorEvent e = event ;

        String type = event.sensor.getStringType();

       /* for(Sensor sensor : sensorListened){
            if(sensor.getType()==e.sensor.getType()){

                float x = e.values[0];
                float y = e.values[1];
                float z = e.values[2];

                IHMSensor UI = listIHM_S.get(sensor);
                UI.setValeurs(x,y,z);

                System.out.println(type + ":");
                System.out.println("x: " + x + "  y: " + y + "  z: " + z);



            }
        }*/
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
    public WifiManager getWifiManager(){
        return wifiManager ;
    }
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
    }
    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
        unregisterReceiver(broadcastReceiver);
    }
}
