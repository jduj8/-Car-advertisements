package com.example.dujic.usedcars;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Dujic on 12/23/2017.
 */

public class Helper {

    public static String trenutni = "";

    public static void toastaj(Context m, String text) {
        Toast.makeText(m, text, Toast.LENGTH_LONG).show();
        //example of use: Helper.toastaj(getApplicationContext(), "OK");
    }

    public static void accessData(){
        DataStorage.getCarAdsFirebase();
        DataStorage.getCarPitcuresFirebase();
    }


}
