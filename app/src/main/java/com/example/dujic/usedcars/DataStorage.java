package com.example.dujic.usedcars;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.util.ArrayMap;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Dujic on 12/27/2017.
 */

public class DataStorage {

    public static ArrayList<CarAd> carAdsList = new ArrayList<CarAd>();
    public static ArrayList<CarPitcure> carPitcures = new ArrayList<CarPitcure>();

    public static ArrayList<CarAd> usercarAdsList = new ArrayList<CarAd>();
    public static ArrayList<CarAd> ads = new ArrayList<CarAd>();
    public static ArrayList<CarPitcure> imagesOfCars = new ArrayList<CarPitcure>();
    public static ArrayList<String> idUrlOfPitcures = new ArrayList<String>();
    public static ArrayList<CarAd> filteredAds = new ArrayList<CarAd>();

    public static StorageReference mStorage = FirebaseStorage.getInstance().getReference();
    public static DatabaseReference databasePitcures = FirebaseDatabase.getInstance().getReference("carpitcure");
    public static DatabaseReference databaseCarAds = FirebaseDatabase.getInstance().getReference("carad");
    public static String zadnji = "";

    private static StorageReference mStorageRef;
    public static ArrayList<Bitmap> bmp = new ArrayList<Bitmap>();

    public static void fillAds() {

        StorageReference mStorage;
        DatabaseReference databaseCarAds = FirebaseDatabase.getInstance().getReference("carad");
        DatabaseReference databasePitcures = FirebaseDatabase.getInstance().getReference("carpitcure");

        if (carAdsList.size() == 0) {
            for (int i = 0; i < marks.length; i++) {
                CarAd carAd = new CarAd(String.valueOf(i), marks[i], models[i], prices[i], years[i], powers[i], potrosnja[i], kilometers[i],
                        fuels[i], despritions[i], users[i], telefons[i], locations[i]);
                carAdsList.add(carAd);
            }

            for (int i = 0; i < images.length; i++) {
                for (int j = 0; j < images[i].length; j++) {
                    CarPitcure carPitcure = new CarPitcure(String.valueOf(j), String.valueOf(i), images[i][j]);
                    carPitcures.add(carPitcure);
                }
            }
        }

        //Log.i("aaaaaaaaaaaa", String.valueOf(carAdsList.size()) + String.valueOf(carPitcures.size()));
    }

    //provjera postojanja oglasa
    private static Boolean checkExistenceCar(String carID) {
        for (int i = 0; i < ads.size(); i++) {
            if (ads.get(i).carAdID.equals(carID)) {
                return true;
            }
        }

        return false;
    }

    private static Boolean checkExistencePitcure(String pitcureID){
        for (int i = 0; i < imagesOfCars.size(); i++){
            if (imagesOfCars.get(i).pitcureID.equals(pitcureID)){
                return true;
            }
        }

        return false;
    }

    public static CarPitcure returnPitcureOfCar(String carAdID) {
        for (int i = 0; i < carPitcures.size(); i++) {
            if (carPitcures.get(i).carAdID == carAdID) {
                return carPitcures.get(i);
            }
        }
        return null;
    }

    //oglasi određenog korisnika za myAds
    public static void userAds(String mail) {
        usercarAdsList.clear();

        for (int i = 0; i < ads.size(); i++) {
            Log.i("Moji oglasi", "2");
            if (ads.get(i).userID.equals(mail)) {
                usercarAdsList.add(ads.get(i));
            }
        }

        Log.i("Moji oglasi", String.valueOf(usercarAdsList.size()));
    }

    public static void removeAds(int carAdID) {
        int ind = 0;
        for (int i = 0; i < carAdsList.size(); i++) {
            if (carAdsList.get(i).carAdID == String.valueOf(carAdID)) {
                ind = i;
            }
        }
        carAdsList.remove(ind);
    }

    public static String[] returnPitcuresOfCar(String carAdID) {
        ArrayList<String> im = new ArrayList<String>();

        for (int i = 0; i < carPitcures.size(); i++) {
            if (carPitcures.get(i).carAdID == carAdID) {
                im.add(carPitcures.get(i).image);
            }
        }

        String[] images = new String[im.size()];

        for (int i = 0; i < im.size(); i++) {
            images[i] = im.get(i);
        }

        return images;
    }

    public static void fillFilterAds(){
        DataStorage.filteredAds.clear();
        for (int i = 0; i < DataStorage.ads.size(); i++){
            filteredAds.add(DataStorage.ads.get(i));
        }
    }

    //dodavanje oglasa i slika oglasa u bazu
    public static void addCarAdFirebase(final CarAd ad, final ArrayList<Uri> uriImages) {

        final String id = DataStorage.databaseCarAds.push().getKey();

        Log.i("Kljuc", id.toString());
        ad.carAdID = id;
        DataStorage.ads.add(ad);
        fillFilterAds();
        zadnji = id;
        Log.i("Dodaj", "OK");

        for (int i = 0; i < uriImages.size(); i++){
            Uri uri = uriImages.get(i);
            StorageReference filepath = mStorage.child("Photos").child(uri.getLastPathSegment());

            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Log.i("okej", "Upload image");

                }

            });

        }

        for (int i = 0; i < uriImages.size(); i++) {
            String uri = uriImages.get(i).toString();
            CarPitcure carPitcure = new CarPitcure("1", "aa", uri);
            addPitcureToDatabase(carPitcure);

        }

        Handler handler = new Handler();
        //Helper.toastaj(getApplicationContext(), String.valueOf(DataStorage.ads.size()));
        handler.postDelayed(new Runnable(){
            @Override
            public void run(){

                /*DatabaseReference databaseCarAds2 = FirebaseDatabase.getInstance().getReference("carad");

                databaseCarAds2.child(id).setValue(ad);*/
                /*DatabaseReference drCarAd = databaseCarAds.child(id);
                drCarAd.setValue(ad);*/
                DataStorage.databaseCarAds.child(id).setValue(ad);


                /*for (int i = 0; i < uriImages.size(); i++) {
                    Log.i("Uri", uriImages.get(i).toString());
                    String uri = uriImages.get(i).toString();
                    CarPitcure carPitcure = new CarPitcure("1", "aa", uri);
                    addPitcureToDatabase(carPitcure);

                }*/
            }
        }, 1000);


        //Helper.accessData();
        Log.i("Dodaj", "dohvaćeno");



    }

    //dodavanje podataka o slici u bazu
    public static  void addPitcureToDatabase(CarPitcure pitcure){
        Log.i("Dodaj", zadnji);
        String id = DataStorage.databasePitcures.push().getKey();
        pitcure.pitcureID = id;
        pitcure.carAdID = zadnji;
        DataStorage.databasePitcures.child(id).setValue(pitcure);
        Log.i("DodajSlike", pitcure.pitcureID);
    }


    //vraćanje svih oglasa sa servera
    public static void getCarAdsFirebase(){
        //DataStorage.ads.clear();
        ArrayList<String> im = new ArrayList<String>();

        databaseCarAds.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot carAd : dataSnapshot.getChildren()){
                    CarAd ad = new CarAd();
                    ad = carAd.getValue(CarAd.class);
                    Log.i("Dohvat", ad.mark);
                    if (!checkExistenceCar(ad.carAdID)){
                        ads.add(ad);
                        filteredAds.add(ad);
                    }
                }

                Log.i("GetCar", String.valueOf(ads.size()));
                DataStorage.getCarPitcuresFirebase();
                DataStorage.returnPitcure();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("Greska", "ne ok");
            }
        });

    }

    //vraćanje svih slika auta sa servera
    public static void getCarPitcuresFirebase(){

        databasePitcures.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot carPitcure : dataSnapshot.getChildren()){
                    CarPitcure cp = new CarPitcure();
                    Log.i("GetPitcure", String.valueOf(carPitcure.getValue(CarPitcure.class).carAdID));
                    cp = carPitcure.getValue(CarPitcure.class);
                    if (!checkExistencePitcure(cp.pitcureID)){
                        imagesOfCars.add(cp);
                    }
                }

                Log.i("GetPitcure", String.valueOf(imagesOfCars.size()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("CarPitcure", "Greška");
            }
        });
    }

    public static void deleteAdLocal(String carID){
        for (int i = 0; i < ads.size(); i++){
            if (ads.get(i).carAdID.equals(carID)){
                ads.remove(i);
                fillFilterAds();
                //deleteForFilter(carID);
            }
        }
    }



    //--------------STARA VERZIJA--------------------------------
    public static String[] returnPitcuresOfCarFirebase(String carAdID){
        ArrayList<String> im = new ArrayList<String>();
        for (int i = 0; i < imagesOfCars.size(); i++){
            if (imagesOfCars.get(i).carAdID.equals(carAdID)){
                Log.i("CarPitcure", "Ima slika");
                im.add(imagesOfCars.get(i).toString());
            }
        }

        String[] images = new String[im.size()];

        for (int i = 0; i < im.size(); i++){
            images[i] = im.get(i);
        }

        return images;
    }

    //---------STARA VERZIJA---------------------------
    public static CarPitcure returnPitcureOfCarFirebase(String carAdID){
        ArrayList<String> im = new ArrayList<String>();
        for (int i = 0; i < imagesOfCars.size(); i++){
            Log.i("CarPitcure", imagesOfCars.get(i).carAdID + " " + carAdID);
            if (imagesOfCars.get(i).carAdID.equals(carAdID)){
                return imagesOfCars.get(i);

            }
        }
        Log.i("CarPitcure", "null");

        return null;
    }

    //vrati sve slike određenog auto za detalje o nekom autu
    public static String[] pitcuresOfCar(String carAdID){
        ArrayList<String> im = new ArrayList<String>();
        for (int i = 0; i < idUrlOfPitcures.size(); i++){
            String[] pom = idUrlOfPitcures.get(i).toString().split("§");
            Log.i("Detalji", idUrlOfPitcures.get(i) + " " + pom[0]);
            if (pom[0].equals(carAdID)){
                Log.i("Detalji", "jednako");
                im.add(pom[1]);
            }
        }

        String[] images = new String[im.size()];
        for (int i = 0; i < im.size(); i++){
            images[i] = im.get(i);
        }
        Log.i("Detalji", String.valueOf(images.length));

        return images;
    }

    //vrati prvu sliku određenog auta za listu oglasa
    public static String pitcureOfCar(String carAdID){
        for (int i = 0; i < idUrlOfPitcures.size(); i++){
            String[] pom = idUrlOfPitcures.get(i).toString().split("§");
            Log.i("Detalji", idUrlOfPitcures.get(i) + " " + pom[0]);
            if (pom[0].equals(carAdID)){
                Log.i("Detalji", "vraćeno");
               return pom[1];
            }
        }

        return null;
    }

    //vrati url od slika
    public static void returnPitcure(){
        final ArrayList<String> pitcures = new ArrayList<String>();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        for (int i = 0; i < imagesOfCars.size(); i++){
            final CarPitcure carPitcure = imagesOfCars.get(i);
            String[] slike = carPitcure.image.split("/");
            Log.i("okej", slike[slike.length-1]);
            String path = "Photos/" + slike[slike.length-1];

            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();


                storageRef.child(path).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Got the download URL for 'users/me/profile.png'
                        Log.i("Uspjeh", carPitcure.carAdID + "|" + uri.toString());

                        pitcures.add(carPitcure.carAdID + "|" + uri.toString());
                        idUrlOfPitcures.add(carPitcure.carAdID.toString() + "§" + uri.toString());

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                        Log.i("Uspjeh", "neOk");
                    }
                });
                Log.i("Velicina", "tu");
            }

        Log.i("Velicina", String.valueOf(pitcures.size()));
    }

    //filtriranje oglasa
    public static void filterAds(String mark, String model, double minP, double maxP, int minY, int maxY, String location){
        Log.i("Filteri", "1");
        filteredAds.clear();
        Log.i("Filteri", "2");
        for (int i = 0; i < ads.size(); i++){
            CarAd car = ads.get(i);
            Log.i("Filteri", "Cijena: " + String.valueOf(car.price));
            Log.i("Filteri", "min: " + String.valueOf(minP));
            Log.i("Filteri", car.mark + " " + mark);
            String minmax = String.valueOf(minP + "-" + maxP + ", " + minY + "-" + maxY);
            Log.i("Filteri", minmax);
            if ((car.mark.toLowerCase().equals(mark.toLowerCase()) || mark.toLowerCase().equals("Sve marke".toLowerCase())) && car.price >= minP && car.price <= maxP &&
                    car.year >= minY && car.year <= maxY && (car.location.toLowerCase().equals(location.toLowerCase()) || location.equals(""))
                    && (car.model.toLowerCase().equals(model.toLowerCase()) || model.equals(""))){
                filteredAds.add(car);
                Log.i("Filteri", car.mark + " dodan");
            }
        }

        Log.i("Filteri", String.valueOf(filteredAds.size()));
    }

    //brisanje oglasa
    public static void deleteAd(final String carId) {
        Log.i("BrisanjeOglas", carId);

        //Handler handler = new Handler();
        //DatabaseReference drCarAd = databaseCarAds.child(carId);


        DatabaseReference drCarAd = databaseCarAds.child(carId);
        drCarAd.removeValue();
        for (int i = 0; i < imagesOfCars.size(); i++) {
            if (carId.equals(imagesOfCars.get(i).carAdID)) {
                DatabaseReference drCarPitcure = databasePitcures.child(imagesOfCars.get(i).pitcureID);
                drCarPitcure.removeValue();
            }
        }


        deleteAdLocal(carId);
        Log.i("Brisanje", String.valueOf(ads.size()));
        //getCarAdsFirebase();
        //Helper.accessData();
    }

    public static void deleteForFilter(String carID){
        for (int i = 0; i < filteredAds.size(); i++){
            if (filteredAds.get(i).carAdID.equals(carID)){
                filteredAds.remove(i);
            }
        }
    }






    private static String[] users = {"jdujic87@gmail.com", "sbukva878@gmail.com", "jdujic87@gmail.com"};

    private static String[] locations = {"Split", "Zagreb", "Split"};

    private static String[] marks = {"Audi", "Bmw", "Mercedes"};

    private static String[] models = {"s5", "m3", "c250"};

    private static double[] prices = {200000, 250000, 150000};

    private static int[] years = {2012, 2010, 2012};

    private static int[] powers = {130, 200, 120};

    private static int[] potrosnja = {8, 12, 7};

    private static String[] fuels = {"dizel", "benzin", "dizel"};

    private static int[] kilometers = {50000, 60000, 80000};

    private static String[] despritions = {"Ovo je Audi s5, Klimatizacija je dvozonska automatska", "Ovo je Bmw m3", "Ovo je Mercedes c250"};

    private static String[][] images = {{"http://dagelic.com/vjezbe/android/carssmall/audis5.jpg", "http://st.motortrend.com/uploads/sites/10/2015/09/2012-Audi-S5-Cabriolet-engine.jpg",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR3pQURK50tTSZMUUwb8h6QcUQTCJ0TPVzXOtZEk2V5Kcl68mkyiA"},
            {"https://upload.wikimedia.org/wikipedia/commons/thumb/9/92/BMW_M3_Lime_Rock_Park_Edition_%2814497472213%29.jpg/220px-BMW_M3_Lime_Rock_Park_Edition_%2814497472213%29.jpg"}
            , {"http://www.awzqt.com/wp-content/uploads/2017/04/Stunning-Mercedes-Benz-C250-on-Small-Car-Decoration-Ideas-with-Mercedes-Benz-C250.jpg"}};


    private static int[] carIds = {1, 2, 3};

    private static String[] telefons = {"0955819879", "0981234567", "0955819879"};

}
