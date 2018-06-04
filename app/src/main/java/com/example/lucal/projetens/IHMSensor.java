package com.example.lucal.projetens;

import android.content.Context;
import android.hardware.Sensor;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.Layout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;



public class IHMSensor{

    Sensor sensor ;

    TextView type   ;
    TextView x      ;
    TextView y      ;
    TextView z      ;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    public IHMSensor(Sensor s,LinearLayout l,Context c){


        sensor = s ;

        type   = new TextView(c) ;
        x      = new TextView(c) ;
        y      = new TextView(c) ;
        z      = new TextView(c) ;

        type.setText(s.getStringType());
        x.setText("0.0");
        y.setText("0.0");
        z.setText("0.0");

        l.addView(type);
        l.addView(x);
        l.addView(y);
        l.addView(z);


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



