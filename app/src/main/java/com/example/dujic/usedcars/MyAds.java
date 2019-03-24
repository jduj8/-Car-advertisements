package com.example.dujic.usedcars;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

/**
 * Created by Dujic on 12/26/2017.
 */

public class MyAds extends AppCompatActivity {

    FirebaseAuth mAuth;
    ListView lsvMyAds;
    String url = "http://cdn.mysitemyway.com/etc-mysitemyway/icons/legacy-previews/icons/blue-jelly-icons-signs/090638-blue-jelly-icon-signs-first-aid.png";
    ImageView imgPlus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_car_list);

        try {
            mAuth = FirebaseAuth.getInstance();

            DataStorage.userAds(mAuth.getCurrentUser().getEmail());
            Log.i("hahaha", mAuth.getCurrentUser().getEmail());
            Helper.trenutni = mAuth.getCurrentUser().getEmail();

            lsvMyAds = (ListView) findViewById(R.id.lsvMyCarsList);
            lsvMyAds.setAdapter(new MyAdsAdapter(getApplicationContext()));

            imgPlus = (ImageView) findViewById(R.id.imgPlus);
            imgPlus.setImageResource(R.drawable.plus);

            imgPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent newAd = new Intent(MyAds.this, NewCarAd.class);
                    startActivity(newAd);
                }
            });
        }

        catch(Exception e){}
        //Picasso.with(this).load(url).into(imgPlus);


        //Helper.toastaj(getApplicationContext(), mAuth.getCurrentUser().getDisplayName());
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Helper.accessData();
        //DataStorage.ads.clear();
        DataStorage.usercarAdsList.clear();
        mAuth = FirebaseAuth.getInstance();

        DataStorage.userAds(mAuth.getCurrentUser().getEmail());

        if (DataStorage.usercarAdsList.size() == 0){

        }
        //lsvMyAds = (ListView) findViewById(R.id.lsvMyCarsList);
        //lsvMyAds.setAdapter(new MyAdsAdapter(getApplicationContext()));


    }

    public void callMain(){
        Intent main = new Intent(MyAds.this, MainActivity.class);
        startActivity(main);
    }
}
