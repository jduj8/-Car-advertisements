package com.example.dujic.usedcars;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Dujic on 12/23/2017.
 */

public class AdDetails extends AppCompatActivity {

    Integer index = 0;
    ImageView imgLeft, imgRight, imgCarGallery;
    TextView txtModelMark, txtYear, txtPrice, txtConsummation, txtKm, txtPower, txtFuel, txtDecribe, txtTelefone, txtLocation;
    String[] images;
    String[] pitcures;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_list_details);

        Bundle extras = getIntent().getExtras(); //komunikacija među aktivnostima
        String pos = extras.getString("position");
        int  position = Integer.valueOf(pos);


        final CarAd car = DataStorage.filteredAds.get(position);

        //images = DataStorage.returnPitcuresOfCar(car.carAdID);
        images = DataStorage.pitcuresOfCar(car.carAdID);
        for (int i = 0; i < images.length; i++){
            Log.i("Detaljno", images[i].toString());
        }

        imgLeft = (ImageView) findViewById(R.id.imgLeft);
        imgRight = (ImageView) findViewById(R.id.imgRight);
        imgCarGallery = (ImageView) findViewById(R.id.imgCarGallery);

        txtModelMark = (TextView) findViewById(R.id.txtModelMark);
        txtYear = (TextView) findViewById(R.id.txtYearDet);
        txtPrice = (TextView) findViewById(R.id.txtPriceDet);
        txtConsummation = (TextView) findViewById(R.id.txtConsummationDet);
        txtKm = (TextView) findViewById(R.id.txtKmDet);
        txtPower = (TextView) findViewById(R.id.txtPowerDet);
        txtFuel = (TextView) findViewById(R.id.txtFuelDet);
        txtDecribe = (TextView) findViewById(R.id.txtDescribeDet);
        txtTelefone = (TextView) findViewById(R.id.txtTelefonDet);
        txtLocation = (TextView) findViewById(R.id.txtLocationDet);

        txtModelMark.setText(String.valueOf(car.mark) + " " + String.valueOf(car.model));
        txtYear.setText("Godina: " + String.valueOf(car.year));
        txtPrice.setText("Cijena(kn): " + String.valueOf(car.price));
        txtConsummation.setText("Potrošnja(l): " + String.valueOf(car.consummation));
        txtKm.setText("Prijeđeni km: " + String.valueOf(car.kilometersOfTravel));
        txtPower.setText("Snaga(kw): " + String.valueOf(car.enginePower));
        txtFuel.setText("Gorivo: " + String.valueOf(car.fuel));
        txtDecribe.setText("Detaljniji opis:\n" + String.valueOf(car.description)+"\n\nEmail: "+car.userID);
        txtTelefone.setText("Telefon:\n" + String.valueOf(car.telefon));
        txtLocation.setText("Lokacija:\n" + String.valueOf(car.location));


        Picasso.with(this).load("https://png.icons8.com/ios-glyphs/540//back.png").into(imgLeft);
        Picasso.with(this).load("https://png.icons8.com/windows/540/forward.png").into(imgRight);
        Picasso.with(this).load(images[index].toString()).into(imgCarGallery);
        //Picasso.with(this).load(images[index].toString()).resize(204,140).into(imgCarGallery);

        //shared prefernces broj otvaranja određenog oglasa na uređaju
        SharedPreferences pref = this.getSharedPreferences(car.carAdID, 0);
        Log.i("Postavke", String.valueOf(pref.getInt(car.carAdID, 0)));
        int openingNumber = pref.getInt(car.carAdID, 0);
        Helper.toastaj(getApplicationContext(), "Oglas za " + car.mark + " " + car.model + " je otvoren " + String.valueOf(openingNumber) + " puta");
        openingNumber++;
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(car.carAdID, openingNumber);
        editor.commit();
        ///////////////////////////////////////////////////////////////

        imgRight.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                index++;
                if (index==images.length) index = 0;
                Picasso.with(AdDetails.this).load(images[index].toString()).into(imgCarGallery);
            }
        });


        /*index++;
        if (index==images.length) index = 0;
        Picasso.with(AdDetails.this).load(images[index].toString()).into(imgCarGallery);*/

        imgLeft.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                index--;
                if (index==-1) index = images.length-1;
                Picasso.with(AdDetails.this).load(images[index].toString()).into(imgCarGallery);
            }
        });



    }

    private Ad returnAdOfCar(CarTest car){
        for (int i=0;i<DataStorageTest.adArrayList.size();i++){
            Ad ad = DataStorageTest.adArrayList.get(i);
            if (ad.carId == car.id){
                return ad;
            }
        }
        return null;
    }
}
