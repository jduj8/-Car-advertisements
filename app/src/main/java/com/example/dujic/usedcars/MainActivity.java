package com.example.dujic.usedcars;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ImageView imgStart;
    Button btnReviewAds;
    Button btnMyAds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgStart = (ImageView) findViewById(R.id.imgStart);
        btnReviewAds = (Button) findViewById(R.id.btnReviewAds);
        btnMyAds = (Button) findViewById(R.id.btnMyAds);
        Log.i("Glavna", "1");
        /////////////////////////////////*
        if (DataStorage.ads.size()==0){
            Helper.accessData();}
        Log.i("Glavna", String.valueOf(DataStorage.imagesOfCars.size()));

        btnReviewAds.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent reviewAds = new Intent(MainActivity.this, ReviewAds.class);
                startActivity(reviewAds);
            }
        });

        btnMyAds.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent login = new Intent(MainActivity.this, Login.class);
                startActivity(login);
            }
        });

        Picasso.with(this).load("https://openclipart.org/image/2400px/svg_to_png/185082/1381989347.png").into(imgStart);
        Log.i("Glavna", "3");

    }

   @Override
    protected void onResume() {
        super.onResume();

        //Helper.accessData();
       /*Handler handler = new Handler();
       handler.postDelayed(new Runnable(){
           @Override
           public void run(){
               Helper.toastaj(getApplicationContext(), String.valueOf(DataStorage.ads.size()));
           }
       }, 2000);*/

        //DataStorage.ads.clear();
        //DataStorage.filteredAds.clear();
    }
}
