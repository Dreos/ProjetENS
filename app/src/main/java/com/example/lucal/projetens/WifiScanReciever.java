package com.example.lucal.projetens;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

public class WifiScanReciever extends BroadcastReceiver {
    private final WifiManager wifiManager;
    private List<ScanResult> listeScan;
    private final Context cont ;

    public Activity activity ;
    public WifiScanReciever(MainActivity a,Context c){
        activity = a ;
        wifiManager = a.getWifiManager();
        cont = c ;
    }



    @Override
    // appelé a chaque fois qu'est apelle la method startScan() du wifiManager
    public void onReceive(Context context, Intent intent) {
        // On vérifie que notre objet est bien instancié
        new Thread(new Runnable() {
             @Override
             public void run() {
                 if (wifiManager != null)

                 {

                     // On vérifie que le WiFi est allumé
                     if (wifiManager.isWifiEnabled()) {
                         // On récupère les scans
                         listeScan = wifiManager.getScanResults();
                         if (listeScan.size() == 0) {
                             Log.d("WIFI SCANNER : ", " ON LA PAS EU ,COURAGE");
                         } else {
                             Log.d("WIFI SCANNER : ", " ON LA EU ,CHAMPAGNE");
                         }


                     } else {
                         Toast.makeText(cont, "Vous devez activer votre WiFi",
                                 Toast.LENGTH_SHORT);
                     }
                 } else

                 {
                     Toast.makeText(cont, "wifimanager null",
                             Toast.LENGTH_SHORT);
                 }

             }
         }).start();
    }
}
