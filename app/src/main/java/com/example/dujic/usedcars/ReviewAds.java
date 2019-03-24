package com.example.dujic.usedcars;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dujic on 12/22/2017.
 */

public class ReviewAds extends AppCompatActivity {

    ListView lsvReviewAds;
    Button btnFilter;
    Spinner spMark;
    double minP = 0, maxP = 10000000;
    int minY = 0, maxY = 2100;
    EditText edtModel, edtMinPrice, edtMaxPrice, edtMinYear, edtMaxYear, edtLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cars_list_layout);

        lsvReviewAds = (ListView) findViewById(R.id.lsvCarsList);
        btnFilter = (Button) findViewById(R.id.btnFilter);
        spMark = (Spinner) findViewById(R.id.spMarks);
        spMark.setSelection(0);
        edtModel = (EditText) findViewById(R.id.edtModel);
        edtMinPrice = (EditText) findViewById(R.id.edtMinPrice);
        edtMaxPrice = (EditText) findViewById(R.id.edtMaxPrice);
        edtMinYear = (EditText) findViewById(R.id.edtMinYear);
        edtMaxYear = (EditText) findViewById(R.id.edtMaxYear);
        edtLocation = (EditText) findViewById(R.id.edtLocation);

        //fillSpinner();

        //DataStorage.getCarAdsFirebase();
        //Helper.toastaj(getApplicationContext(), String.valueOf(DataStorage.ads.size()));

        //DataStorageTest.fillCarList();
        //lsvReviewAds.setAdapter(new ListAdsAdapterTest(getApplicationContext()));

        //DataStorage.fillAds();
        Handler handler = new Handler();
        //Helper.toastaj(getApplicationContext(), String.valueOf(DataStorage.ads.size()));
        handler.postDelayed(new Runnable(){
            @Override
            public void run(){
                //Helper.toastaj(getApplicationContext(), String.valueOf(DataStorage.ads.size()));
                lsvReviewAds.setAdapter(new ListAdsAdapter(getApplicationContext()));
            }
        }, 1000);
        //lsvReviewAds.setAdapter(new ListAdsAdapter(getApplicationContext()));
        //Helper.toastaj(getApplicationContext(), String.valueOf(DataStorage.idUrlOfPitcures.size()));


        lsvReviewAds.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CarAd obj = (CarAd) lsvReviewAds.getItemAtPosition(position);
                String p = String.valueOf(position);
                Intent intent = new Intent(ReviewAds.this, AdDetails.class);
                intent.putExtra("position", p);
                startActivity(intent);
            }
        });

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!edtMinPrice.getText().toString().isEmpty()){
                    minP = Double.valueOf(edtMinPrice.getText().toString());
                    //Helper.toastaj(getApplicationContext(), String.valueOf(minP));
                }

                if (!edtMaxPrice.getText().toString().isEmpty()){
                    maxP = Integer.valueOf(edtMaxPrice.getText().toString());
                }
                if (!edtMinYear.getText().toString().isEmpty()){
                    minY = Integer.valueOf(edtMinYear.getText().toString());
                }
                if (!edtMaxYear.getText().toString().isEmpty()){
                    maxY = Integer.valueOf(edtMaxYear.getText().toString());
                }
                String model = edtModel.getText().toString();
                String location = edtLocation.getText().toString();
                lsvReviewAds.setAdapter(null);
                Log.i("Filteri", String.valueOf(minP) + "-" + String.valueOf(maxP));
                DataStorage.filterAds(spMark.getSelectedItem().toString(), model, minP, maxP, minY, maxY, location);
                lsvReviewAds.setAdapter(new ListAdsAdapter(getApplicationContext()));
                minP = 0; maxP = 10000000; minY = 0; maxY = 2100;


            }
        });

    }

    public void fillSpinner(){
        ArrayList<String> marks = new ArrayList<String>();

        marks.add("Sve marke");
        for (int i = 0; i < DataStorage.ads.size(); i++){
            marks.add(DataStorage.ads.get(i).mark);
        }
        ArrayAdapter<String> adp2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, marks);

        adp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMark.setAdapter(adp2);

    }

    @Override
    public void onResume(){
        super.onResume();
        ////////////////////////////
        //Helper.accessData();
        Handler handler = new Handler();
        //Helper.toastaj(getApplicationContext(), String.valueOf(DataStorage.ads.size()));
        handler.postDelayed(new Runnable(){
            @Override
            public void run(){
                //Helper.toastaj(getApplicationContext(), String.valueOf(DataStorage.ads.size()));
                lsvReviewAds.setAdapter(new ListAdsAdapter(getApplicationContext()));
            }
        }, 1000);
    }


}


