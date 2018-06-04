package com.example.lucal.projetens;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;




// Class qui regroupe des methodes utilitaires
public class Outils {

    public static ArrayList<String> permissions1 = new ArrayList<>();
    public Outils(){
        permissions1.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        permissions1.add(Manifest.permission.ACCESS_WIFI_STATE);
        permissions1.add(Manifest.permission.CHANGE_WIFI_STATE);
        permissions1.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions1.add(Manifest.permission.ACCESS_NETWORK_STATE);
    } ;



    // demande la permission d'utilisé des informations selon une liste
    public static void permissionsRequest(ArrayList<String> listePermission, Context c,Activity a){
        for(String str : listePermission) {
            if (ContextCompat.checkSelfPermission(c,
                    str)
                    != PackageManager.PERMISSION_GRANTED) {

                // permission non accordé
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(a, str)) {
                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                }
                else {
                    // on demande la permission
                    System.out.println("Demande permission : "+str);
                    ActivityCompat.requestPermissions(a, new String[]{str}, 20);


                }
            }
            else {
                // Instructions quand un droit a deja été donné
                System.out.println("L'autorisation "+ str + " accordée.");
            }
        }
    }

    public static ArrayList<String> getPermissions1() {
        return permissions1;
    }
}
