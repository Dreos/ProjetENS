package com.example.lucal.projetens;

import android.content.Context;
import android.text.Layout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class IHMWifi{
    TextView Nom    ;
    TextView x      ;
    TextView y      ;
    TextView z      ;

    Context context ;
    Layout layout ;

    public IHMWifi(Context c ,Layout l ){
        layout =  l ;

        Nom   = new TextView(context) ;
        x      = new TextView(context) ;
        y      = new TextView(context) ;
        z      = new TextView(context) ;
    }


}